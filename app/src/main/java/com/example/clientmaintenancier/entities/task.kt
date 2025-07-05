package com.example.clientmaintenancier.entities

import com.google.gson.annotations.SerializedName


data class task(
    val id: Int,
    val deviceId: Int,
    val maintainerId: Int,
    val type: String,
    val isRemote: Boolean?,
    val planDate: String?,
    @SerializedName("Priority")
    val priority: String?,
    val description: String?,
    val status: String,
    @SerializedName("Device")
    val device: DeviceInformation
)

data class realtask(
    val id: Int,
    val deviceId: Int,
    val maintainerId: Int,
    val type: String,
    val isRemote: Boolean?,
    val planDate: String?,
    @SerializedName("Priority")
    val priority: String?,
    val description: String?,
    val status: String,
)

data class DeviceInformation(
    val nom: String,
    val status: String,
    val email: String?
)