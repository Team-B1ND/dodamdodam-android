package com.b1nd.dodam.teacher

import com.b1nd.dodam.logging.KmLogging
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import platform.posix.printf

fun onApplicationStartPlatformSpecific() {
    NotifierManager.initialize(
        NotificationPlatformConfiguration.Ios(
            showPushNotification = true,
            askNotificationPermissionOnStart = true,
            notificationSoundName = "default"
        )
    )
}