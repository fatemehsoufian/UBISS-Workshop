package org.uni.myapplication

import android.util.Log
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
                Log.d("tag", "fetchSunlightInfo: ")
                val data = RetrofitClient.apiService.getSunlightData()
                Log.d("tag", "fetchSunlightInfo $data")
                _sunlightInfo.value = data
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("tag", "$e")
            }
        }
    }
}
