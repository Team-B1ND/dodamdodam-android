package com.b1nd.dodam.teacher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.b1nd.dodam.keystore.keystoreManagerModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initKoin {
            androidLogger()
            androidContext(this@MainActivity)
            androidFileProperties()
            modules(
                keystoreManagerModule,
            )
        }
        setContent {
            DodamTeacherApp()
        }
    }
}
