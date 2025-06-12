package org.uni.myapplication

import android.app.Application
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit


@HiltAndroidApp
class MyApp: Application(){
    override fun onCreate() {
        super.onCreate()

        val request = PeriodicWorkRequestBuilder<PeriodicTaskWorker>(15, TimeUnit.MINUTES).build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "PeriodicWork",
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
