package org.uni.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking

class PeriodicTaskWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            // 1. Get data from API
            val response = runBlocking {
                RetrofitClient.apiService.getSunriseAlert()
            }

            // 2. Show notification
            NotificationHelper.sendNotification(
                applicationContext,
                "☀️ Sunrise Alert",
                response.message
            )

            Log.d("SunriseWorker", "Notification sent: ${response.message}")
            Result.success()
        } catch (e: Exception) {
            Log.e("SunriseWorker", "API Error", e)

            // Fallback notification
            NotificationHelper.sendNotification(
                applicationContext,
                "Daily Reminder",
                "Don't forget to get some sunlight today!"
            )

            Result.retry() // Will retry later
        }
    }
}