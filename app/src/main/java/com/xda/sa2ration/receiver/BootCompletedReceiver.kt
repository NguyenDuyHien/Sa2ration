package com.xda.sa2ration.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.xda.sa2ration.worker.Sa2rationWorker

class BootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.d("BootCompletedReceiver", "Handle ACTION_BOOT_COMPLETED")

                WorkManager.getInstance(context.applicationContext).enqueueUniqueWork(
                    Sa2rationWorker.TAG,
                    ExistingWorkPolicy.KEEP,
                    OneTimeWorkRequestBuilder<Sa2rationWorker>().build()
                )
            }
        }
    }
}