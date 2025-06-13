package org.uni.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking

class MiddayAlertWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            // 1. Get data from API
            val response = runBlocking {
                RetrofitClient.apiService.getMiddayAlert()
            }

            // 2. Show notification
            NotificationHelper.sendNotification(
                applicationContext,
                "🌞 Midday Reminder",
                response.message
            )

            Log.d("MiddayWorker", "Notification sent: ${response.message}")
            Result.success()
        } catch (e: Exception) {
            Log.e("MiddayWorker", "API Error", e)

            // Fallback notification
            NotificationHelper.sendNotification(
                applicationContext,
                "Midday Reminder",
                "Still time to chase the sun! Stretch, walk, dance — whatever gets you outside."
            )

            Result.retry()
        }
    }
}