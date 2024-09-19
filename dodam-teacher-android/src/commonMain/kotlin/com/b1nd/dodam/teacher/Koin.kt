package com.b1nd.dodam.teacher

import com.b1nd.dodam.all.di.allViewModelModule
import com.b1nd.dodam.approvenightstudy.approveNightStudyViewModelModule
import com.b1nd.dodam.approveouting.approveOutingViewModelModule
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
import com.b1nd.dodam.home.di.homeViewModelModule
import com.b1nd.dodam.login.di.loginViewModelModule
import com.b1nd.dodam.meal.di.mealViewModelModule
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
import com.b1nd.dodam.point.di.pointViewModelModule
import com.b1nd.dodam.register.di.registerDataSourceModule
import com.b1nd.dodam.register.di.registerRepositoryModule
import com.b1nd.dodam.register.di.registerViewModelModule
import com.b1nd.dodam.setting.di.settingViewModelModule
import com.b1nd.dodam.teacher.di.DodamTeacherAppViewModelModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

fun initKoin(block: KoinApplication.() -> Unit = {}) {
    startKoin {
        modules(
            dataStoreModule,
            coroutineScopeModule,
            dispatchersModule,
            networkCoreModule,
            registerViewModelModule,
            registerRepositoryModule,
            registerDataSourceModule,
            loginViewModelModule,
            loginRepositoryModule,
            loginDataSourceModule,
            homeViewModelModule,
            bannerRepositoryModule,
            bannerDataSourceModule,
            mealRepositoryModule,
            mealDataSourceModule,
            outingRepositoryModule,
            outingDataSourceModule,
            nightStudyRepositoryModule,
            nightStudyDataSourceModule,
            scheduleRepositoryModule,
            scheduleDatasourceModule,
            DodamTeacherAppViewModelModule,
            mealViewModelModule,
            nightStudyViewModelModule,
            memberRepositoryModule,
            memberDataSourceModule,
            pointViewModelModule,
            pointRepositoryModule,
            pointDataSourceModule,
            outingViewModelModule,
            approveOutingViewModelModule,
            allViewModelModule,
            approveNightStudyViewModelModule,
            settingViewModelModule,
        )
        block()
    }
}
