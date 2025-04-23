package com.example.clientmaintenancier.repositories

import com.example.clientmaintenancier.api.DeviceStatsResponse
import com.example.clientmaintenancier.api.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call

class DeviceRepository(private val apiService: ApiService) {
    suspend fun getDeviceStats(): Result<DeviceStatsResponse> {
        return try {
            val response = apiService.getDeviceStats()
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("API error: ${response.code()} - ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

