package com.expensetracker.domain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

object AlertNotificationManager {

    fun checkAndTriggerAlert(
        context: Context,
        appName: String,
        currentSpent: Double,
        allocatedBudget: Double
    ) {
        val percent = (currentSpent / allocatedBudget) * 100

        if (percent >= 100) {
            sendNotification(
                context,
                title = "🚨 Budget Exceeded for $appName",
                message = "You spent ₹$currentSpent, crossing your limit of ₹$allocatedBudget on $appName!"
            )
        } else if (percent >= 80) {
            sendNotification(
                context,
                title = "⚠️ 80% Budget Warning: $appName",
                message = "You have used ${percent.toInt()}% of your allocated ₹$allocatedBudget limit on $appName."
            )
        }
    }

    private fun sendNotification(context: Context, title: String, message: String) {
        val channelId = "BUDGET_ALERTS"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Budget Alerts", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
