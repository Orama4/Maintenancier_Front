package com.example.clientmaintenancier.repositories

import com.example.clientmaintenancier.api.DeviceStatsResponse
import com.example.clientmaintenancier.api.ApiService
import com.example.clientmaintenancier.entities.device
import com.example.clientmaintenancier.entities.task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class DeviceRepository(private val apiService: ApiService) {
    suspend fun getDeviceStats(maintainerId: Int): Response<DeviceStatsResponse> {
        return withContext(Dispatchers.IO) {
            apiService.getDeviceStats(maintainerId)
        }
    }
    suspend fun getDevicesByMaintainerId(maintainerId: Int): Response<List<device>> {
        return withContext(Dispatchers.IO) {
            apiService.getDevicesByMaintainerId(maintainerId)
        }
    }
    suspend fun getDeviceById(deviceId: Int): Response<device> {
        return withContext(Dispatchers.IO) {
            apiService.getDeviceById(deviceId)
        }
    }

}

