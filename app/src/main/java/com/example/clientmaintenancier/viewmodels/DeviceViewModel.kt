package com.example.clientmaintenancier.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientmaintenancier.api.DeviceStatsResponse
import com.example.clientmaintenancier.repositories.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DeviceViewModel(private val repository: DeviceRepository) : ViewModel() {

    private val _stats = MutableStateFlow<DeviceStatsResponse?>(null)
    val stats: StateFlow<DeviceStatsResponse?> get() = _stats

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchDeviceStats() {
        viewModelScope.launch {
            _loading.value = true
            try {
                repository.getDeviceStats()
                    .onSuccess { result ->
                        _stats.value = result
                        _error.value = null
                    }
                    .onFailure { exception ->
                        _error.value = "Failed to fetch stats: ${exception.message}"
                    }
            } catch (e: Exception) {
                _error.value = "Failed to fetch stats: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }
}