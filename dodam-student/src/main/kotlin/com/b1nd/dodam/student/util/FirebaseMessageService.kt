package com.b1nd.dodam.student.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.content.Intent
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.core.app.NotificationCompat
import com.b1nd.dodam.datastore.repository.DataStoreRepository
import com.b1nd.dodam.student.MainActivity
import com.b1nd.dodam.student.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.random.Random
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
class FirebaseMessageService : FirebaseMessagingService() {

    val datastore: DataStoreRepository by inject()

    private val serviceScope = CoroutineScope(EmptyCoroutineContext)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch(Dispatchers.IO) {
            datastore.savePushToken(pushToken = token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        message.notification?.let { message ->
            sendNotification(message)
        }
    }

    private fun sendNotification(message: RemoteMessage.Notification) {
        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            FLAG_IMMUTABLE,
        )

        val channelId = getString(R.string.default_notification_channel_id)
        val channelName = getString(R.string.default_notification_channel_name)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.drawable.ic_dodam_logo)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        manager.notify(Random.nextInt(), notificationBuilder.build())
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }
}
