package org.uni.myapplication

data class SunLightModel(
    val sunrise: String,
    val sunset: String,
    val timezone: String? = null,
    val weatherDescription: String,
    val cloudiness: Int,        // Percentage
    val temperature: Float,
    val humidity: Int,
    val exposureAnalysis: ExposureAnalysis? = null  // From your API analysis
)

data class ExposureAnalysis(
    val healthScore: Int,
    val fullSunlightMinutes: Float,
    val partialSunMinutes: Float,
    val shadeOutdoorMinutes: Float,
    val indoorMinutes: Float,
    val recommendations: List<String>
)