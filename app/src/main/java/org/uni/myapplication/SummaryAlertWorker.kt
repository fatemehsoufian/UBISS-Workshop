package org.uni.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import java.util.Calendar

class SummaryAlertWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        return try {
            // 1. Get data from API
            val response = runBlocking {
                RetrofitClient.apiService.getSummaryAlert()
            }

            // 2. Determine which message to show based on time or other logic
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val isDaytime = currentHour in 6..18 // Example: between 6AM and 6PM

            // Mock logic - in real app you'd track actual sunlight exposure
            val message = if (isDaytime) {
                response.messages["success"] ?: "You did it! 90 minutes of sunshine in the bag."
            } else {
                response.messages["fail"] ?: "The sun's gone to bed. Did you catch your 90 mins?"
            }

            // 3. Show notification
            NotificationHelper.sendNotification(
                applicationContext,
                "🌞 Daily Summary",
                message
            )

            Log.d("SummaryWorker", "Notification sent: $message")
            Result.success()
        } catch (e: Exception) {
            Log.e("SummaryWorker", "API Error", e)

            // Fallback notification
            NotificationHelper.sendNotification(
                applicationContext,
                "Daily Summary",
                "✨ How was your sun exposure today?"
            )

            Result.retry()
        }
    }
}