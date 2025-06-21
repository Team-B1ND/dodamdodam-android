package com.b1nd.dodam.student.util

import android.app.Application
import com.b1nd.dodam.all.di.parentAllViewModelModule
import com.b1nd.dodam.approvenightstudy.approveNightStudyViewModelModule
import com.b1nd.dodam.asknightstudy.di.askNightStudyViewModelModule
import com.b1nd.dodam.askout.di.askOutViewModelModule
import com.b1nd.dodam.askwakeupsong.di.askWakeUpSongViewModelModule
import com.b1nd.dodam.bus.di.busDataSourceModule
import com.b1nd.dodam.bus.di.busRepositoryModule
import com.b1nd.dodam.bus.di.busViewModelModule
import com.b1nd.dodam.club.di.clubDataSourceModule
import com.b1nd.dodam.club.di.clubRepositoryModule
import com.b1nd.dodam.club.di.clubViewModelModule
import com.b1nd.dodam.club.di.myClubViewModelModule
import com.b1nd.dodam.common.network.di.coroutineScopeModule
import com.b1nd.dodam.common.network.di.dispatchersModule
import com.b1nd.dodam.data.banner.di.bannerRepositoryModule
import com.b1nd.dodam.data.bundleidinfo.di.bundleIdInfoRepositoryModule
import com.b1nd.dodam.data.division.di.divisionRepositoryModule
import com.b1nd.dodam.data.login.di.loginRepositoryModule
import com.b1nd.dodam.data.meal.di.mealRepositoryModule
import com.b1nd.dodam.data.nightstudy.di.nightStudyRepositoryModule
import com.b1nd.dodam.data.notice.di.noticeRepositoryModule
import com.b1nd.dodam.data.outing.di.outingRepositoryModule
import com.b1nd.dodam.data.point.di.pointRepositoryModule
import com.b1nd.dodam.data.schedule.di.scheduleRepositoryModule
import com.b1nd.dodam.data.upload.di.uploadRepositoryModule
import com.b1nd.dodam.datastore.di.dataStoreModule
import com.b1nd.dodam.editmemberinfo.di.editMemberInfoViewModelModule
import com.b1nd.dodam.group.di.groupViewModelModule
import com.b1nd.dodam.groupadd.di.groupAddViewModelModule
import com.b1nd.dodam.groupcreate.di.groupCreateViewModelModule
import com.b1nd.dodam.groupdetail.di.groupDetailViewModelModule
import com.b1nd.dodam.groupwaiting.di.groupWaitingViewModelModule
import com.b1nd.dodam.keystore.keystoreManagerModule
import com.b1nd.dodam.login.di.loginViewModelModule
import com.b1nd.dodam.member.di.memberDataSourceModule
import com.b1nd.dodam.member.di.memberRepositoryModule
import com.b1nd.dodam.network.banner.di.bannerDataSourceModule
import com.b1nd.dodam.network.core.di.networkCoreModule
import com.b1nd.dodam.network.division.di.divisionDataSourceModule
import com.b1nd.dodam.network.login.di.loginDataSourceModule
import com.b1nd.dodam.network.meal.di.mealDataSourceModule
import com.b1nd.dodam.network.nightstudy.di.nightStudyDataSourceModule
import com.b1nd.dodam.network.notice.di.noticeDatasourceModule
import com.b1nd.dodam.network.outing.di.outingDataSourceModule
import com.b1nd.dodam.network.point.di.pointDataSourceModule
import com.b1nd.dodam.network.schedule.di.scheduleDatasourceModule
import com.b1nd.dodam.network.upload.di.uploadDatasourceModule
import com.b1nd.dodam.nightstudy.di.nightStudyViewModelModule
import com.b1nd.dodam.notice.di.noticeViewModelModule
import com.b1nd.dodam.outing.di.outingViewModelModule
import com.b1nd.dodam.parent.childrenmanage.di.childrenManageViewModelModule
import com.b1nd.dodam.parnet.home.di.parentHomeViewModelModule
import com.b1nd.dodam.register.di.infoViewModelModule
import com.b1nd.dodam.register.di.registerDataSourceModule
import com.b1nd.dodam.register.di.registerRepositoryModule
import com.b1nd.dodam.register.di.registerViewModelModule
import com.b1nd.dodam.setting.di.settingViewModelModule
import com.b1nd.dodam.student.main.di.mainViewModelModules
import com.b1nd.dodam.student.point.di.pointViewModelModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongDataSourceModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongRepositoryModule
import com.b1nd.dodam.wakeupsong.di.wakeupSongViewModelModule
import com.seugi.network.bundleidinfo.di.bundleIdInfoDataSourceModule
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
                    bundleIdInfoRepositoryModule,
                    bundleIdInfoDataSourceModule,
                    editMemberInfoViewModelModule,
                    uploadDatasourceModule,
                    uploadRepositoryModule,
                    parentHomeViewModelModule,
                    parentAllViewModelModule,
                    noticeViewModelModule,
                    groupViewModelModule,
                    groupAddViewModelModule,
                    groupCreateViewModelModule,
                    groupDetailViewModelModule,
                    groupWaitingViewModelModule,
                    childrenManageViewModelModule,
                    infoViewModelModule,
                    clubDataSourceModule,
                    clubRepositoryModule,
                    myClubViewModelModule,
                    clubViewModelModule,
                    divisionRepositoryModule,
                    divisionDataSourceModule,
                    noticeRepositoryModule,
                    noticeDatasourceModule,
                    approveNightStudyViewModelModule
                ) + mainViewModelModules,
            )
        }
    }
}
