package com.b1nd.dodam.student

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import com.b1nd.dodam.designsystem.DodamTheme
import com.b1nd.dodam.designsystem.component.ButtonRole
import com.b1nd.dodam.designsystem.component.ButtonSize
import com.b1nd.dodam.designsystem.component.DodamButton
import com.b1nd.dodam.designsystem.component.DodamModalBottomSheet
import com.b1nd.dodam.ui.icons.B1NDLogo
import com.b1nd.dodam.ui.icons.DodamLogo
import com.b1nd.dodam.ui.util.AndroidFileDownloader
import com.b1nd.dodam.ui.util.LocalFileDownloader
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URLDecoder

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {

    private val datastoreRepository: DataStoreRepository by inject()
    private val loginDataSource: LoginDataSource by inject()

    private val appUpdateManager by lazy { AppUpdateManagerFactory.create(this) }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { _ ->
    }

    private var showLoginResultSheet by mutableStateOf(false)
    private var loginResultSuccess by mutableStateOf(false)
    private var loginResultMessage by mutableStateOf("")

    private var toastState by mutableStateOf<String?>(null)
    private var toastMessage by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

        intent?.data?.let { uri ->
            if (uri.toString().startsWith("https://deeplink.b1nd.com/")) {
                val clientId = uri.getQueryParameter("clientId")
                val code = uri.getQueryParameter("code")

                if (clientId != null && code != null) {
                    lifecycleScope.launch {
                        try {
                            val user = datastoreRepository.user.first()
                            if (user.token.isNotEmpty()) {
                                performQrLogin(clientId, code, user.token)
                            } else {
                                toastState = "ERROR"
                                toastMessage = "로그인이 필요합니다"
                            }
                        } catch (e: Exception) {
                            toastState = "ERROR"
                            toastMessage = "로그인이 필요합니다"
                        }
                    }
                }
            }
        }

        checkAppUpdate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        setContent {
            var role: String? by remember { mutableStateOf(null) }
            val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }
            val scope = rememberCoroutineScope()

            if (showLoginResultSheet) {
                DodamModalBottomSheet(
                    onDismissRequest = {
                        showLoginResultSheet = false
                    },
                    title = {
                        Text(
                            text = if (loginResultSuccess) "로그인에 성공했습니다" else "로그인에 실패했습니다",
                            style = DodamTheme.typography.heading2Bold(),
                            color = DodamTheme.colors.labelNormal,
                        )
                        Spacer(Modifier.height(8.dp))
                    },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(color = DodamTheme.colors.backgroundNormal),
                        ) {
                            if (loginResultMessage.isNotEmpty()) {
                                Text(
                                    text = loginResultMessage,
                                    style = DodamTheme.typography.body1Medium(),
                                    color = DodamTheme.colors.labelAssistive,
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                                Spacer(Modifier.height(24.dp))
                            }
                            DodamButton(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                onClick = {
                                    showLoginResultSheet = false
                                },
                                text = "확인",
                                buttonSize = ButtonSize.Large,
                                buttonRole = ButtonRole.Primary,
                            )
                            Spacer(Modifier.height(16.dp))
                        }
                    },
                )
            }

            LaunchedEffect(Unit) {
                launch {
                    enableEdgeToEdge(
                        statusBarStyle = SystemBarStyle.auto(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT,
                        ),
                        navigationBarStyle = SystemBarStyle.auto(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT,
                        ),
                    )
                }
                launch {
                    role = datastoreRepository.user.first().role
                }
            }

            CompositionLocalProvider(LocalFileDownloader provides AndroidFileDownloader(this)) {
                DodamTheme {
                    DodamTheme {
                        if (toastState != null && toastMessage.isNotEmpty()) {
                            when (toastState) {
                                "SUCCESS", "ERROR" -> {
                                    LaunchedEffect(toastState, toastMessage) {
                                        scope.launch { snackbarHostState.showSnackbar(toastMessage) }
                                        kotlinx.coroutines.delay(3000)
                                        toastState = null
                                        toastMessage = ""
                                    }
                                }
                            }
                        }

                        role?.let {
                            DodamApp(
                                logout = {
                                    lifecycleScope.launch {
                                        datastoreRepository.deleteUser()
                                        finish()
                                    }
                                },
                                firebaseAnalytics = firebaseAnalytics,
                                firebaseCrashlytics = firebaseCrashlytics,
                                role = it,
                            )
                        } ?: run {
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.background)
                                    .fillMaxSize(),
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .size(180.dp)
                                        .align(Alignment.Center),
                                    imageVector = DodamLogo,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )

                                Icon(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .align(Alignment.BottomCenter),
                                    imageVector = B1NDLogo,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlow(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                    )
                }
            }
    }

    private fun checkAppUpdate() {
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
                ) {
                    appUpdateManager.startUpdateFlow(
                        appUpdateInfo,
                        this,
                        AppUpdateOptions.newBuilder(AppUpdateType.IMMEDIATE).build(),
                    )
                }
            }
    }

    private fun performQrLogin(clientId: String, code: String, token: String) {
        lifecycleScope.launch {
            try {
                val response = loginDataSource.qrLogin(
                    code = code,
                    access = token,
                    refresh = token,
                    clientId = clientId
                )

                withContext(Dispatchers.Main) {
                    loginResultSuccess = response.status == 200
                    loginResultMessage = response.message
                    showLoginResultSheet = true
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    loginResultSuccess = false
                    loginResultMessage = e.message ?: "알 수 없는 오류가 발생했습니다"
                    showLoginResultSheet = true
                }
            }
        }
    }
}
