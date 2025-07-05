package com.example.clientmaintenancier.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientmaintenancier.api.DeviceStatsResponse
import com.example.clientmaintenancier.entities.device
import com.example.clientmaintenancier.entities.task
import com.example.clientmaintenancier.repositories.DeviceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class DeviceViewModel(private val repository: DeviceRepository) : ViewModel() {

    private val _deviceStats = MutableStateFlow<DeviceStatsResponse?>(null)
    val deviceStats: StateFlow<DeviceStatsResponse?> = _deviceStats

    private val _devices = MutableStateFlow<List<device>>(emptyList())
    val devices: StateFlow<List<device>> = _devices

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    private val _device = mutableStateOf<device?>(null)
    val device: State<device?> = _device

    private val _loading2 = MutableStateFlow(false)
    val loading2: StateFlow<Boolean> = _loading2

    private val _error2 = MutableStateFlow<String?>(null)
    val error2: StateFlow<String?> = _error2

    fun fetchDeviceStats(maintainerId: Int) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getDeviceStats(maintainerId)
                if (response.isSuccessful) {
                    _deviceStats.value = response.body()
                } else {
                    _error.value = "Failed to fetch device stats: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchDevicesByMaintainer(maintainerId: Int) {
        Log.d("am in view of device","maintainer id = $maintainerId")

        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getDevicesByMaintainerId(maintainerId)
                Log.d("response device","response device = $response")

                if (response.isSuccessful) {
                    _devices.value = response.body() ?: emptyList()
                } else {
                    _error.value = "Failed to fetch devices: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = "Error: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchDeviceById(deviceId: Int) {
        _loading2.value = true
        _error2.value = null

        viewModelScope.launch {
            try {
                val response: Response<device> = repository.getDeviceById(deviceId)
                if (response.isSuccessful) {
                    _device.value = response.body()
                } else {
                    _error2.value = "Error: ${response.code()} ${response.message()}"
                }
            } catch (e: Exception) {
                _error2.value = "Exception: ${e.localizedMessage ?: "Unknown error"}"
            } finally {
                _loading2.value = false
            }
        }
    }



}
