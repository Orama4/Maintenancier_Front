package com.example.clientmaintenancier.api

import retrofit2.Response
import retrofit2.http.GET;

interface ApiService {
    @GET("/api/devices/stats")
    suspend fun getDeviceStats(): Response<DeviceStatsResponse>
}

data class DeviceStatsResponse(
    val totalDevices: Int,
    val enPanneDevices: Int,
    val connectedDevices: Int,
    val disconnectedDevices: Int
)