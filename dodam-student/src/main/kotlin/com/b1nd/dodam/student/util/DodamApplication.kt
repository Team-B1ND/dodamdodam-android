package com.b1nd.dodam.student.util

import android.app.Application
import com.b1nd.dodam.bus.di.busDataSourceModule
import com.b1nd.dodam.bus.di.busRepositoryModule
import com.b1nd.dodam.common.network.di.coroutineScopeModule
import com.b1nd.dodam.common.network.di.dispatchersModule
import com.b1nd.dodam.data.banner.di.bannerRepositoryModule
import com.b1nd.dodam.data.meal.di.mealRepositoryModule
import com.b1nd.dodam.data.nightstudy.di.nightStudyRepositoryModule
import com.b1nd.dodam.data.outing.di.outingRepositoryModule
import com.b1nd.dodam.datastore.di.dataStoreModule
import com.b1nd.dodam.keystore.keystoreManagerModule
import com.b1nd.dodam.network.banner.di.bannerDataSourceModule
import com.b1nd.dodam.network.core.di.networkCoreModule
import com.b1nd.dodam.network.meal.di.mealDataSourceModule
import com.b1nd.dodam.network.nightstudy.di.nightStudyDataSourceModule
import com.b1nd.dodam.network.outing.di.outingDataSourceModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongDataSourceModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongRepositoryModule
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
                wakeupSongRepositoryModule,
                wakeupSongDataSourceModule,
                outingRepositoryModule,
                outingDataSourceModule,
                bannerRepositoryModule,
                bannerDataSourceModule,
                busRepositoryModule,
                busDataSourceModule,
                nightStudyRepositoryModule,
                nightStudyDataSourceModule,
            )
        }
    }
}
