package org.uni.myapplication

import android.app.Application
import android.util.Log
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar
import java.util.concurrent.TimeUnit

// MyApp.kt
@HiltAndroidApp
class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()
        scheduleDailyNotification(11, 46)    // Sunrise alert at 6 AM
        scheduleMiddayNotification(11, 47)  // Midday alert at 12 PM
        scheduleSunsetNotification(11, 48) // Sunset alert at 5:30 PM (adjust as needed)
        scheduleSummaryNotification(11, 49) // Sunset alert at 5:30 PM (adjust as needed)
    }

    private fun scheduleDailyNotification(hour: Int, minute: Int) {
        val currentTime = Calendar.getInstance()
        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (before(currentTime)) {
                add(Calendar.DATE, 1)
            }
        }

        val initialDelay = scheduledTime.timeInMillis - currentTime.timeInMillis

        val dailyRequest = PeriodicWorkRequestBuilder<PeriodicTaskWorker>(
            24, // Repeat interval (hours)
            TimeUnit.HOURS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailySunriseAlert",
            ExistingPeriodicWorkPolicy.UPDATE,
            dailyRequest
        )
    }

    private fun scheduleMiddayNotification(hour: Int, minute: Int) {
        val currentTime = Calendar.getInstance()
        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (before(currentTime)) {
                add(Calendar.DATE, 1)
            }
        }

        val initialDelay = scheduledTime.timeInMillis - currentTime.timeInMillis

        val middayRequest = PeriodicWorkRequestBuilder<MiddayAlertWorker>(
            24, // Repeat interval (hours)
            TimeUnit.HOURS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyMiddayAlert",
            ExistingPeriodicWorkPolicy.UPDATE,
            middayRequest
        )
    }
    private fun scheduleSunsetNotification(hour: Int, minute: Int) {
        val currentTime = Calendar.getInstance()
        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (before(currentTime)) {
                add(Calendar.DATE, 1)
            }
        }

        val initialDelay = scheduledTime.timeInMillis - currentTime.timeInMillis

        val sunsetRequest = PeriodicWorkRequestBuilder<SunsetAlertWorker>(
            24, // Repeat interval (hours)
            TimeUnit.HOURS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailySunsetAlert",
            ExistingPeriodicWorkPolicy.UPDATE,
            sunsetRequest
        )

        Log.d("Scheduler", "Next sunset notification at: ${scheduledTime.time}")
    }
    private fun scheduleSummaryNotification(hour: Int, minute: Int) {
        val currentTime = Calendar.getInstance()
        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)

            if (before(currentTime)) {
                add(Calendar.DATE, 1)
            }
        }

        val initialDelay = scheduledTime.timeInMillis - currentTime.timeInMillis

        val summaryRequest = PeriodicWorkRequestBuilder<SummaryAlertWorker>(
            24, // Repeat interval (hours)
            TimeUnit.HOURS
        )
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailySummaryAlert",
            ExistingPeriodicWorkPolicy.UPDATE,
            summaryRequest
        )

        Log.d("Scheduler", "Next summary notification at: ${scheduledTime.time}")
    }
}

