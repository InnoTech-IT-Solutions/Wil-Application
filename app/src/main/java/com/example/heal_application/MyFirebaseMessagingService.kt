package com.example.heal_application

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
            showNotification(remoteMessage)
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
            showNotification(remoteMessage)
        }
    }

    private fun showNotification(remoteMessage: RemoteMessage) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create the Notification Channel for API 26+ (Android 8.0+)
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Channel human-readable title",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Channel description"
        }
        notificationManager.createNotificationChannel(channel)

        // Create a PendingIntent to open your app when the user taps the notification
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Build and show the notification
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)  // Replace with your own notification icon
            .setContentTitle(remoteMessage.notification?.title ?: "Notification Title")
            .setContentText(remoteMessage.notification?.body ?: "Notification Body")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "New token: $token")
        // Optionally, send this token to your server to keep it updated
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
        private const val CHANNEL_ID = "my_channel_id"
        private const val NOTIFICATION_ID = 1001
    }
}
