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
        // Do your background task here (e.g. check condition, send notification, etc.)
        Log.d("PeriodicTaskWorker", "Running background task")

        return Result.success()
    }
}
