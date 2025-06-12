package org.uni.myapplication

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class PeriodicTaskWorker(
    context: Context,
    params: WorkerParameters
) : Worker(context, params) {

    override fun doWork(): Result {
        sendNotification(applicationContext, "Hey!", "This is your 15-min update.")

        return Result.success()
    }
}
