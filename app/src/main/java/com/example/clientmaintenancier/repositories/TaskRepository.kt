package com.example.clientmaintenancier.repositories

import com.example.clientmaintenancier.api.ApiService
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

}