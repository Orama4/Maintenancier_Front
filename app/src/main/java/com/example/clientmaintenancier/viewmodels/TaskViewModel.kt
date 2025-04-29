package com.example.clientmaintenancier.viewmodels


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientmaintenancier.entities.task
import com.example.clientmaintenancier.repositories.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import retrofit2.Response


class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _interventions = MutableStateFlow<List<task>>(emptyList())
    val interventions: StateFlow<List<task>> = _interventions

    private val _loading1 = MutableStateFlow(false)
    val loading1: StateFlow<Boolean> = _loading1

    private val _error1 = MutableStateFlow<String?>(null)
    val error1: StateFlow<String?> = _error1

    private val _intervention = mutableStateOf<task?>(null)
    val intervention: State<task?> = _intervention

    private val _loading2 = MutableStateFlow(false)
    val loading2: StateFlow<Boolean> = _loading2

    private val _error2 = MutableStateFlow<String?>(null)
    val error2: StateFlow<String?> = _error2

    private val _interventionsByDevice = MutableStateFlow<List<task>>(emptyList())
    val interventionsByDevice: StateFlow<List<task>> = _interventionsByDevice

    private val _loading3 = MutableStateFlow(false)
    val loading3: StateFlow<Boolean> = _loading3

    private val _error3 = MutableStateFlow<String?>(null)
    val error3: StateFlow<String?> = _error3

    private val _updateLoading = MutableStateFlow(false)
    val updateLoading: StateFlow<Boolean> = _updateLoading

    private val _updateError = MutableStateFlow<String?>(null)
    val updateError: StateFlow<String?> = _updateError

    private val _updateSuccess = MutableStateFlow<Boolean?>(null)
    val updateSuccess: StateFlow<Boolean?> = _updateSuccess


    fun fetchInterventionsByMaintainer(maintainerId: Int) {
        viewModelScope.launch {
            _loading1.value = true
            try {
                val response = repository.getInterventionsByMaintainerId(maintainerId)
                if (response.isSuccessful) {
                    _interventions.value = response.body() ?: emptyList()
                } else {
                    _error1.value = "Failed to fetch interventions by maintainer: ${response.code()}"
                }
            } catch (e: Exception) {
                _error1.value = "Error: ${e.localizedMessage}"
            } finally {
                _loading1.value = false
            }
        }
    }


    fun fetchInterventionById(taskId: Int) {
        _loading2.value = true
        _error2.value = null

        viewModelScope.launch {
            try {
                val response: Response<task> = repository.getInterventionById(taskId)
                if (response.isSuccessful) {
                    _intervention.value = response.body()
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

    fun fetchInterventionsByDevice(deviceId: Int) {
        viewModelScope.launch {
            _loading3.value = true
            try {
                val response = repository.getInterventionsByDeviceId(deviceId)
                if (response.isSuccessful) {
                    _interventionsByDevice.value = response.body() ?: emptyList()
                } else {
                    _error3.value = "Failed to fetch interventions by device: ${response.code()}"
                }
            } catch (e: Exception) {
                _error3.value = "Error: ${e.localizedMessage}"
            } finally {
                _loading3.value = false
            }
        }
    }

    fun updateTask(
        interventionId: Int,
        deviceId: Int,
        planDate: String,
        status: String = "en_progres",
        deviceStatus: String = "En_maintenance"
    ) {
        viewModelScope.launch {
            _updateLoading.value = true
            _updateError.value = null
            _updateSuccess.value = null

            try {
                val planDateResponse = repository.updatePlanDate(interventionId, planDate)

                if (!planDateResponse.isSuccessful) {
                    _updateError.value = "Failed to update planDate: ${planDateResponse.code()}"
                    _updateSuccess.value = false
                    return@launch
                }

                val statusResponse = repository.updateStatus(interventionId, status)

                if (!statusResponse.isSuccessful) {
                    _updateError.value = "Failed to update status: ${statusResponse.code()}"
                    _updateSuccess.value = false
                    return@launch
                }

                val deviceStatusResponse = repository.updateDeviceStatus(deviceId, deviceStatus)

                if (!deviceStatusResponse.isSuccessful) {
                    _updateError.value = "Failed to update device status: ${deviceStatusResponse.code()}"
                    _updateSuccess.value = false
                    return@launch
                }

                // All updates succeeded
                _updateSuccess.value = true

            } catch (e: Exception) {
                _updateError.value = "Error: ${e.localizedMessage ?: "Unknown error"}"
                _updateSuccess.value = false
            } finally {
                _updateLoading.value = false
            }
        }
    }


    private val _updateDescriptionStatus = MutableStateFlow<Boolean?>(null)
    val updateDescriptionStatus: StateFlow<Boolean?> = _updateDescriptionStatus

    fun markInterventionAsCompleted(
        interventionId: Int,
        deviceId: Int,
        description: String
    ) {
        viewModelScope.launch {
            try {
                val descResponse = repository.updateInterventionDescription(interventionId, description)
                val statusResponse = repository.updateStatus(interventionId, "complete")
                val deviceStatusResponse = repository.updateDeviceStatus(deviceId, "Actif")

                _updateDescriptionStatus.value =
                    descResponse.isSuccessful && statusResponse.isSuccessful && deviceStatusResponse.isSuccessful

            } catch (e: Exception) {
                _updateDescriptionStatus.value = false
            }
        }
    }



    fun clearInterventionsData() {
        _interventionsByDevice.value = emptyList() // Clear the existing data
        _error3.value = null // Reset any errors
    }

    fun clearInterventionsData2() {
        _interventions.value = emptyList() // Clear the existing data
        _error1.value = null // Reset any errors
    }

    fun clearInterventionsData3() {
        _intervention.value = null // Clear the existing data
        _error2.value = null // Reset any errors
    }
}