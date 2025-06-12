package org.uni.myapplication

import retrofit2.http.GET

interface ApiService {
    @GET("/sunrise_sunset/aedb19dd-160a-459c-84b8-cb3f67cf171d")
    suspend fun getSunlightData(): SunLightModel
}