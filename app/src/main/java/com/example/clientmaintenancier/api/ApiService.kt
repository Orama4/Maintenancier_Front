package com.example.clientmaintenancier.api

import com.example.clientmaintenancier.entities.device
import com.example.clientmaintenancier.entities.realtask
import com.example.clientmaintenancier.entities.task
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET;
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    //devices
    @GET("api/devices/{maintainerId}/device-stats")
    suspend fun getDeviceStats(
        @Path("maintainerId") maintainerId: Int
    ): Response<DeviceStatsResponse>

    @GET("api/devices/maintainer/{maintainerId}")
    suspend fun getDevicesByMaintainerId(
        @Path("maintainerId") maintainerId: Int
    ): Response<List<device>>

    @GET("api/devices/{deviceId}")
    suspend fun getDeviceById(
        @Path("deviceId") deviceId: Int
    ): Response<device>

    @PUT("api/devices/{id}/status")
    suspend fun updateDeviceStatus(
        @Path("id") deviceId: Int,
        @Body status: Map<String, String>
    ): Response<device>

    //interventions
    @GET("api/interventions/maintainer/{maintainerId}/")
    suspend fun getInterventionsByMaintainerId(
        @Path("maintainerId") maintainerId: Int
    ): Response<List<task>>

    @GET("api/interventions/{taskId}")
    suspend fun getInterventionById(@Path("taskId") taskId: Int): Response<task>

    @GET("api/interventions/device/{deviceId}")
    suspend fun getInterventionsByDeviceId(@Path("deviceId") deviceId: Int): Response<List<task>>

    @PUT("api/interventions/{id}/plan-date")
    suspend fun updatePlanDate(
        @Path("id") interventionId: Int,
        @Body planDate: Map<String, String>
    ): Response<realtask>

    @PUT("api/interventions/{id}/status")
    suspend fun updateStatus(
        @Path("id") interventionId: Int,
        @Body status: Map<String, String>
    ): Response<realtask>

    @PUT("api/interventions/{id}/description")
    suspend fun updateInterventionDescription(
        @Path("id") interventionId: Int,
        @Body description: Map<String, String>
    ): Response<realtask>


}

data class DeviceStatsResponse(
    val totalDevices: Int,
    val connectedDevices: Int,
    val disconnectedDevices: Int,
    val downDevices: Int,
    val inMaintenanceDevices: Int
)
