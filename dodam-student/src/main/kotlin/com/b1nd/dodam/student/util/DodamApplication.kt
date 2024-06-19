package com.b1nd.dodam.student.util

import android.app.Application
import com.b1nd.dodam.common.network.di.coroutineScopeModule
import com.b1nd.dodam.common.network.di.dispatchersModule
import com.b1nd.dodam.data.meal.di.mealRepositoryModule
import com.b1nd.dodam.datastore.di.dataStoreModule
import com.b1nd.dodam.keystore.keystoreManagerModule
import com.b1nd.dodam.network.core.di.networkCoreModule
import com.b1nd.dodam.network.meal.di.mealDataSourceModule
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
                keystoreManagerModule,
                networkCoreModule,
                dataStoreModule,
                dispatchersModule,
                coroutineScopeModule,
                mealRepositoryModule,
                mealDataSourceModule,
            )
        }
    }
}
