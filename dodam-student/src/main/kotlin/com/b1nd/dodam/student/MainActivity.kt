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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.b1nd.dodam.dds.theme.DodamTheme
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
import android.util.Log
import com.b1nd.dodam.network.login.datasource.LoginDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

        intent?.data?.let { uri ->

            if (uri.toString().startsWith("https://deeplink.b1nd.com/student")) {
                val clientId = uri.getQueryParameter("clientId")
                val word = uri.getQueryParameter("words")
                val code = uri.getQueryParameter("code")

                if (clientId != null && word != null && code != null) {
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
                            Log.d("QR_LOGIN", "QR login successful: ${response.accessToken}")

                            // Update token if needed
                            if (response.accessToken != token) {
                                withContext(Dispatchers.Main) {
                                    datastoreRepository.saveToken(response.accessToken)
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("QR_LOGIN", "QR login failed", e)
                        }
                    }
                } else {
                    Log.e("QR_LOGIN", "Missing parameters in QR code URL")
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
}
