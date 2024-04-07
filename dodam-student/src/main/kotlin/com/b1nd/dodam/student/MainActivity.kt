package com.b1nd.dodam.student

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.b1nd.dodam.datastore.repository.DatastoreRepository
import com.b1nd.dodam.dds.theme.DodamTheme
import com.b1nd.dodam.ui.icons.B1NDLogo
import com.b1nd.dodam.ui.icons.DodamLogo
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var datastoreRepository: DatastoreRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val firebaseCrashlytics = FirebaseCrashlytics.getInstance()

        setContent {
            var isLogin: Boolean? by remember { mutableStateOf(null) }

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
                    isLogin = datastoreRepository.token.first().isNotEmpty()
                }
            }

            DodamTheme {
                isLogin?.let {
                    DodamApp(
                        isLogin = it,
                        logout = {
                            lifecycleScope.launch {
                                datastoreRepository.deleteUser()
                                finish()
                            }
                        },
                        firebaseAnalytics = firebaseAnalytics,
                        firebaseCrashlytics = firebaseCrashlytics,
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
