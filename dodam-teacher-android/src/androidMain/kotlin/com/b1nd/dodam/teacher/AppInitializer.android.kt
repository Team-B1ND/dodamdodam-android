package com.b1nd.dodam.teacher

import android.util.Log
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration


fun onAndroidApplicationStartPlatformSpecific() {
    NotifierManager.initialize(
        configuration = NotificationPlatformConfiguration.Android(
            notificationIconResId = R.drawable.ic_dodam_logo,
            notificationIconColorResId = R.color.ic_launcher_background
        )
    )
}