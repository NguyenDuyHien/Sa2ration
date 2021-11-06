package com.xda.sa2ration.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xda.sa2ration.repository.Sa2rationRepository
import com.xda.sa2ration.utils.changeSaturationValue
import com.xda.sa2ration.utils.setEnableDci
import com.xda.sa2ration.utils.setSaturationValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Sa2rationRepository) : ViewModel() {
    val applyOnBoot = repository.readData(Sa2rationRepository.APPLY_ON_BOOT, false)

    fun updateSaturationValue(value: Float, isApply: Boolean = false) {
        if (isApply) setSaturationValue(value) else changeSaturationValue(value)

        viewModelScope.launch {
            repository.storeData(Sa2rationRepository.SATURATION_VALUE, value)
        }
    }

    fun updateEnableDci(isEnable: Boolean) {
        setEnableDci(isEnable)

        viewModelScope.launch {
            repository.storeData(Sa2rationRepository.ENABLE_DCI, isEnable)
        }
    }

    fun updateApplyOnBoot(isEnable: Boolean) {
        viewModelScope.launch {
            repository.storeData(Sa2rationRepository.APPLY_ON_BOOT, isEnable)
        }
    }
}