package com.b1nd.dodam.student.util

import android.app.Application
import com.b1nd.dodam.asknightstudy.di.askNightStudyViewModelModule
import com.b1nd.dodam.askout.di.askOutViewModelModule
import com.b1nd.dodam.askwakeupsong.di.askWakeUpSongViewModelModule
import com.b1nd.dodam.bus.di.busDataSourceModule
import com.b1nd.dodam.bus.di.busRepositoryModule
import com.b1nd.dodam.bus.di.busViewModelModule
import com.b1nd.dodam.common.network.di.coroutineScopeModule
import com.b1nd.dodam.common.network.di.dispatchersModule
import com.b1nd.dodam.data.banner.di.bannerRepositoryModule
import com.b1nd.dodam.data.login.di.loginRepositoryModule
import com.b1nd.dodam.data.meal.di.mealRepositoryModule
import com.b1nd.dodam.data.nightstudy.di.nightStudyRepositoryModule
import com.b1nd.dodam.data.outing.di.outingRepositoryModule
import com.b1nd.dodam.data.point.di.pointRepositoryModule
import com.b1nd.dodam.data.schedule.di.scheduleRepositoryModule
import com.b1nd.dodam.datastore.di.dataStoreModule
import com.b1nd.dodam.keystore.keystoreManagerModule
import com.b1nd.dodam.login.di.loginViewModelModule
import com.b1nd.dodam.member.di.memberDataSourceModule
import com.b1nd.dodam.member.di.memberRepositoryModule
import com.b1nd.dodam.network.banner.di.bannerDataSourceModule
import com.b1nd.dodam.network.core.di.networkCoreModule
import com.b1nd.dodam.network.login.di.loginDataSourceModule
import com.b1nd.dodam.network.meal.di.mealDataSourceModule
import com.b1nd.dodam.network.nightstudy.di.nightStudyDataSourceModule
import com.b1nd.dodam.network.outing.di.outingDataSourceModule
import com.b1nd.dodam.network.point.di.pointDataSourceModule
import com.b1nd.dodam.network.schedule.di.scheduleDatasourceModule
import com.b1nd.dodam.nightstudy.di.nightStudyViewModelModule
import com.b1nd.dodam.outing.di.outingViewModelModule
import com.b1nd.dodam.register.di.registerDataSourceModule
import com.b1nd.dodam.register.di.registerRepositoryModule
import com.b1nd.dodam.register.di.registerViewModelModule
import com.b1nd.dodam.setting.di.settingViewModelModule
import com.b1nd.dodam.student.main.di.mainViewModelModules
import com.b1nd.dodam.student.point.di.pointViewModelModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongDataSourceModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongRepositoryModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class DodamApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@DodamApplication)
            modules(
                listOf(
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
                    scheduleRepositoryModule,
                    scheduleDatasourceModule,
                    bannerRepositoryModule,
                    bannerDataSourceModule,
                    busRepositoryModule,
                    busDataSourceModule,
                    nightStudyRepositoryModule,
                    nightStudyDataSourceModule,
                    pointRepositoryModule,
                    pointDataSourceModule,
                    registerRepositoryModule,
                    registerDataSourceModule,
                    memberRepositoryModule,
                    memberDataSourceModule,
                    loginRepositoryModule,
                    loginDataSourceModule,
                    busViewModelModule,
                    askNightStudyViewModelModule,
                    askWakeUpSongViewModelModule,
                    askOutViewModelModule,
                    pointViewModelModule,
                    wakeupSongViewModelModule,
                    loginViewModelModule,
                    registerViewModelModule,
                    settingViewModelModule,
                    nightStudyViewModelModule,
                    outingViewModelModule,
                ) + mainViewModelModules,
            )
        }
    }
}
