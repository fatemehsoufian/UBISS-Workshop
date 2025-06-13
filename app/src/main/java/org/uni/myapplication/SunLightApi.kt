package org.uni.myapplication

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("sunrise_sunset/aedb19dd-160a-459c-84b8-cb3f67cf171d")
    suspend fun getSunlightData(): SunLightModel

    @GET("sunrise_alert/aedb19dd-160a-459c-84b8-cb3f67cf171d")
    suspend fun getSunriseAlert(): AlertResponse

    @GET("midday_alert/aedb19dd-160a-459c-84b8-cb3f67cf171d")
    suspend fun getMiddayAlert(): AlertResponse

    @GET("sunset_alert/aedb19dd-160a-459c-84b8-cb3f67cf171d")
    suspend fun getSunsetAlert(): AlertResponse

    @GET("summary_alert/aedb19dd-160a-459c-84b8-cb3f67cf171d")
    suspend fun getSummaryAlert(): SummaryAlertResponse
}