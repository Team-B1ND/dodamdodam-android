package com.b1nd.dodam.teacher

import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import platform.posix.printf

actual fun onApplicationStartPlatformSpecific() {
    printf("fasdvc")
    NotifierManager.initialize(
        NotificationPlatformConfiguration.Ios(
            showPushNotification = true,
            askNotificationPermissionOnStart = true,
            notificationSoundName = "custom_notification_sound.wav"
        )
    )
}