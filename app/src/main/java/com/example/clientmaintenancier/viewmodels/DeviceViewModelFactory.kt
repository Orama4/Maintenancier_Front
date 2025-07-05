package com.example.clientmaintenancier.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.clientmaintenancier.repositories.DeviceRepository

class DeviceViewModelFactory(private val repository: DeviceRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DeviceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DeviceViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}