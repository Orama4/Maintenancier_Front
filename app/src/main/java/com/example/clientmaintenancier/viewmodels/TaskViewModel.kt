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


    fun fetchInterventionsByMaintainer(maintainerId: Int) {
        viewModelScope.launch {
            _loading1.value = true
            try {
                val response = repository.getInterventionsByMaintainerId(maintainerId)
                if (response.isSuccessful) {
                    _interventions.value = response.body() ?: emptyList()
                } else {
                    _error1.value = "Failed to fetch interventions: ${response.code()}"
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
}