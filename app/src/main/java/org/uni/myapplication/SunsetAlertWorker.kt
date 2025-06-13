package org.uni.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking

class SunsetAlertWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            // 1. Get data from API
            val response = runBlocking {
                RetrofitClient.apiService.getSunsetAlert()
            }

            // 2. Show notification
            NotificationHelper.sendNotification(
                applicationContext,
                "🌇 Sunset Warning",
                response.message
            )

            Log.d("SunsetWorker", "Notification sent: ${response.message}")
            Result.success()
        } catch (e: Exception) {
            Log.e("SunsetWorker", "API Error", e)

            // Fallback notification
            NotificationHelper.sendNotification(
                applicationContext,
                "Sunset Reminder",
                "Sun's setting soon! Take a moment to go outside."
            )

            Result.retry()
        }
    }
}