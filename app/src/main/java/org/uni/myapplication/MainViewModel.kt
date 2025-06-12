package org.uni.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SunlightViewModel : ViewModel() {
    private val _sunlightInfo = MutableStateFlow<SunLightModel?>(null)
    val sunlightInfo: StateFlow<SunLightModel?> = _sunlightInfo

    init {
        fetchSunlightInfo()
    }

    private fun fetchSunlightInfo() {
        viewModelScope.launch {
            try {
                val data = RetrofitClient.apiService.getSunlightData()
                _sunlightInfo.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
