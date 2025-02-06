package com.b1nd.dodam.teacher

import com.b1nd.dodam.logging.KmLogging
import com.b1nd.dodam.teacher.AppInitializer.viewModel
import com.mmk.kmpnotifier.notification.NotifierManager
import com.mmk.kmpnotifier.notification.PayloadData
import com.mmk.kmpnotifier.notification.configuration.NotificationPlatformConfiguration
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object AppInitializer: KoinComponent {

    private val viewModel: DodamTeacherAppViewModel by inject()

    fun onApplicationStart() {
        NotifierManager.addListener(object : NotifierManager.Listener {
            override fun onNewToken(token: String) {
                viewModel.savePushToken(pushToken = token)
            }

            override fun onPushNotification(title: String?, body: String?) {
                super.onPushNotification(title, body)
                println("Push Notification notification type message is received: Title: $title and Body: $body")
            }

            override fun onPayloadData(data: PayloadData) {
                super.onPayloadData(data)
                println("Push Notification payloadData: $data")
            }

            override fun onNotificationClicked(data: PayloadData) {
                super.onNotificationClicked(data)
                println("Notification clicked, Notification payloadData: $data")
            }
        })
    }
}




