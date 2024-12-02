package com.b1nd.dodam.teacher.utiles

import android.app.Application
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import com.b1nd.dodam.keystore.keystoreManagerModule
import com.b1nd.dodam.teacher.R
import com.b1nd.dodam.teacher.getAsyncImageLoader
import com.b1nd.dodam.teacher.initKoin
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger

class DodamTeacherApplication : Application(), SingletonImageLoader.Factory {
    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return getAsyncImageLoader(this)
    }

    override fun onCreate() {
        super.onCreate()

        NotifierManager.initialize(
            configuration = NotificationPlatformConfiguration.Android(
                notificationIconResId = R.drawable.ic_launcher_foreground
            )
        )
        initKoin {
            androidLogger()
            androidContext(this@DodamTeacherApplication)
            androidFileProperties()
            modules(
                keystoreManagerModule,
            )
        }
    }
}
