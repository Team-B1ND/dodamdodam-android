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
import android.widget.Toast
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

    private var showWordSelectionSheet by mutableStateOf(false)
    private var showLoginConfirmationSheet by mutableStateOf(false)
    private var qrClientId by mutableStateOf<String?>(null)
    private var qrWords by mutableStateOf<List<String>>(emptyList())
    private var qrCode by mutableStateOf<String?>(null)
    private var selectedWord by mutableStateOf<String?>(null)
    private var qrClientName by mutableStateOf<String?>(null)

    private var toastState by mutableStateOf<String?>(null)
    private var toastMessage by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

        intent?.data?.let { uri ->

            if (uri.toString().startsWith("https://deeplink.b1nd.com/student") || uri.toString().startsWith("https://deeplink.blind.com/student")) {
                val clientId = uri.getQueryParameter("clientId")
                val wordsParam = uri.getQueryParameter("words")
                val code = uri.getQueryParameter("code")
                val encodedClientName = uri.getQueryParameter("clientName")
                val clientName = encodedClientName?.let { URLDecoder.decode(it, "UTF-8") }

                if (clientId != null && wordsParam != null && code != null) {
                    val decodedWords = URLDecoder.decode(wordsParam, "UTF-8")
                    val characters = decodedWords.map { it.toString() }

                    qrClientId = clientId
                    qrWords = characters
                    qrCode = code
                    qrClientName = clientName

                    lifecycleScope.launch {
                        try {
                            val user = datastoreRepository.user.first()
                            if (user.token.isNotEmpty()) {
                                showWordSelectionSheet = true
                            } else {
                                toastState = "ERROR"
                                toastMessage = "로그인이 필요합니다"
                            }
                        } catch (e: Exception) {
                            toastState = "ERROR"
                            toastMessage = "로그인이 필요합니다"
                        }
                    }
                } else {
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

            if (showWordSelectionSheet) {
                DodamModalBottomSheet(
                    onDismissRequest = {
                        showWordSelectionSheet = false
                        qrClientId = null
                        qrWords = emptyList()
                        qrCode = null
                        qrClientName = null
                    },
                    title = {
                        Text(
                            text = "단어를 선택해주세요",
                            style = DodamTheme.typography.heading2Bold(),
                            color = DodamTheme.colors.labelNormal,
                        )
                        Spacer(Modifier.height(8.dp))
                    },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = "웹에서 뜬 단어를 선택하여 인증해주세요.",
                                style = DodamTheme.typography.body1Medium(),
                                color = DodamTheme.colors.labelAssistive,
                            )
                            Spacer(Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                qrWords.forEach { character ->
                                    DodamButton(
                                        modifier = Modifier.weight(1f),
                                        onClick = {
                                            selectedWord = character
                                            showWordSelectionSheet = false
                                            showLoginConfirmationSheet = true
                                        },
                                        text = character,
                                        buttonSize = ButtonSize.Large,
                                        buttonRole = ButtonRole.Assistive,
                                    )
                                }
                            }
                        }
                    }
                )
            }

            if (showLoginConfirmationSheet) {
                DodamModalBottomSheet(
                    onDismissRequest = {
                        showLoginConfirmationSheet = false
                        selectedWord = null
                        qrClientName = null
                    },
                    title = {
                        Text(
                            text = "${qrClientName ?: ""}에 로그인 하시겠습니까?",
                            style = DodamTheme.typography.heading2Bold(),
                            color = DodamTheme.colors.labelNormal,
                        )
                        Spacer(Modifier.height(8.dp))
                    },
                    content = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                        ) {
                            Text(
                                text = "확인을 클릭하면 자동으로 로그인 됩니다.",
                                style = DodamTheme.typography.body1Medium(),
                                color = DodamTheme.colors.labelAssistive,
                            )
                            Spacer(Modifier.height(24.dp))
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                DodamButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        showLoginConfirmationSheet = false
                                        selectedWord = null
                                        qrClientName = null
                                    },
                                    text = "취소",
                                    buttonSize = ButtonSize.Large,
                                    buttonRole = ButtonRole.Assistive,
                                )
                                DodamButton(
                                    modifier = Modifier.weight(1f),
                                    onClick = {
                                        performQrLogin()
                                        showLoginConfirmationSheet = false
                                    },
                                    text = "확인",
                                    buttonSize = ButtonSize.Large,
                                    buttonRole = ButtonRole.Primary,
                                )
                            }
                        }
                    }
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
                com.b1nd.dodam.designsystem.DodamTheme {
                    DodamTheme {
                        if (toastState != null && toastMessage.isNotEmpty()) {
                            when (toastState) {
                                "SUCCESS", "ERROR" -> {
                                    LaunchedEffect(toastState, toastMessage) {
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

    private fun performQrLogin() {
        val clientId = qrClientId
        val code = qrCode
        val word = selectedWord


        if (clientId != null && code != null && word != null) {

            lifecycleScope.launch {
                try {
                    val user = datastoreRepository.user.first()
                    val token = user.token

                    val response = loginDataSource.qrLogin(
                        code = code,
                        access = token,
                        refresh = token,
                        clientId = clientId,
                        word = word
                    )

                    if (response.accessToken != token) {
                        withContext(Dispatchers.Main) {
                            datastoreRepository.saveToken(response.accessToken)
                        }
                    }

                    qrClientId = null
                    qrWords = emptyList()
                    qrCode = null
                    selectedWord = null
                    qrClientName = null

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()

                        toastState = "SUCCESS"
                        toastMessage = "로그인에 성공했습니다!"
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "로그인 실패: ${e.message}", Toast.LENGTH_SHORT).show()

                        toastState = "ERROR"
                        toastMessage = e.message ?: "로그인에 실패했습니다"
                    }
                }
            }
        } else {
            toastState = "ERROR"
            toastMessage = "로그인 정보가 부족합니다"
        }
    }
}
