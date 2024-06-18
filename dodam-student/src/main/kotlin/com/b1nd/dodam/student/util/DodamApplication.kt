package com.b1nd.dodam.student.util

import android.app.Application
import com.b1nd.dodam.common.network.di.COROUTINE_SCOPE_MODULE
import com.b1nd.dodam.common.network.di.DISPATCHERS_MODULE
import com.b1nd.dodam.datastore.di.DATA_STORE_MODULE
import com.b1nd.dodam.network.core.di.NETWORK_CORE_MODULE
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

@HiltAndroidApp
class DodamApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DodamApplication)
            modules(
                NETWORK_CORE_MODULE,
                DATA_STORE_MODULE,
                DISPATCHERS_MODULE,
                COROUTINE_SCOPE_MODULE,
            )
        }
    }
}
