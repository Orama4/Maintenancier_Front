package com.example.clientmaintenancier.repositories

import com.example.clientmaintenancier.api.ApiService
import com.example.clientmaintenancier.entities.device
import com.example.clientmaintenancier.entities.realtask
import com.example.clientmaintenancier.entities.task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class TaskRepository(private val apiService: ApiService) {
    suspend fun getInterventionsByMaintainerId(maintainerId: Int): Response<List<task>> {
        return withContext(Dispatchers.IO) {
            apiService.getInterventionsByMaintainerId(maintainerId)
        }
    }
    suspend fun getInterventionById(taskId: Int): Response<task> {
        return withContext(Dispatchers.IO) {
            apiService.getInterventionById(taskId)
        }
    }

    suspend fun getInterventionsByDeviceId(deviceId: Int):  Response<List<task>>  {
        return withContext(Dispatchers.IO) {
            apiService.getInterventionsByDeviceId(deviceId)
        }
    }

    suspend fun updatePlanDate(interventionId: Int, planDate: String): Response<realtask> {
        return withContext(Dispatchers.IO) {
            apiService.updatePlanDate(interventionId, mapOf("planDate" to planDate))
        }
    }

    suspend fun updateStatus(interventionId: Int, status: String): Response<realtask> {
        return withContext(Dispatchers.IO) {
            apiService.updateStatus(interventionId, mapOf("status" to status))
        }
    }

    suspend fun updateInterventionDescription(interventionId: Int, description: String): Response<realtask> {
        return withContext(Dispatchers.IO) {
            apiService.updateInterventionDescription(interventionId, mapOf("description" to description)
            )
        }
    }

    suspend fun updateDeviceStatus(deviceId: Int, status: String): Response<device> {
        return withContext(Dispatchers.IO) {
            apiService.updateDeviceStatus(deviceId, mapOf("status" to status))
        }
    }

}