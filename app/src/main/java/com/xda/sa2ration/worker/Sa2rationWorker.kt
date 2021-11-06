package com.xda.sa2ration.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.xda.sa2ration.repository.Sa2rationRepository
import com.xda.sa2ration.utils.changeSaturationValue
import com.xda.sa2ration.utils.checkSudo
import com.xda.sa2ration.utils.setEnableDci
import com.xda.sa2ration.utils.setSaturationValue
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class Sa2rationWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Sa2rationRepository
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result = try {
        when (repository.readData(Sa2rationRepository.APPLY_ON_BOOT, false).first()) {
            true -> {
                if (!checkSudo()) {
                    Log.e(TAG, "No root permission}")
                    Result.failure()
                }

                val saturationValue = repository.readData(Sa2rationRepository.SATURATION_VALUE, 1.0f).first()
                val enableDci = repository.readData(Sa2rationRepository.ENABLE_DCI, false).first()

                changeSaturationValue(saturationValue)
                setSaturationValue(saturationValue)
                setEnableDci(enableDci)
            }
        }
        Log.d(TAG, "Successful!!!")
        Result.success()
    } catch (exc: Exception) {
        Log.e(TAG, "Error: ${exc.stackTraceToString()}")
        Result.failure()
    }

    companion object {
        const val TAG = "Sa2rationWorker"
    }
}