package com.example.clientmaintenancier.entities

data class device(
    val id: Int,
    val nom: String,
    val macAdresse: String,
    val status: String,
    val peripheriques: Map<String, List<String>>,
    val localisation: Map<String, Double>,
    val cpuUsage: Double,
    val ramUsage: Double,
    val price: Int,
    val manufacturingCost: Int,
    val type: String
)