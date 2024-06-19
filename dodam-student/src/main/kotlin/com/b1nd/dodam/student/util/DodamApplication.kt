package com.b1nd.dodam.student.util

import android.app.Application
import com.b1nd.dodam.common.network.di.COROUTINE_SCOPE_MODULE
import com.b1nd.dodam.common.network.di.DISPATCHERS_MODULE
import com.b1nd.dodam.data.meal.di.MEAL_REPOSITORY_MODULE
import com.b1nd.dodam.datastore.di.DATA_STORE_MODULE
import com.b1nd.dodam.keystore.KEYSTORE_MANAGER_MODULE
import com.b1nd.dodam.network.core.di.NETWORK_CORE_MODULE
import com.b1nd.dodam.network.meal.di.MEAL_DATA_SOURCE_MODULE
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
                KEYSTORE_MANAGER_MODULE,
                NETWORK_CORE_MODULE,
                DATA_STORE_MODULE,
                DISPATCHERS_MODULE,
                COROUTINE_SCOPE_MODULE,
                MEAL_REPOSITORY_MODULE,
                MEAL_DATA_SOURCE_MODULE,
            )
        }
    }
}
