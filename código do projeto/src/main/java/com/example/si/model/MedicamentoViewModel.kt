package com.example.si.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MedicamentoViewModel(application: Application) : AndroidViewModel(application) {
    private val medicamentoDao = AppDatabase.getDatabase(application).medicamentoDao()
    private val _medicamentos = MutableStateFlow<List<Medicamento>>(emptyList())
    val medicamentos: StateFlow<List<Medicamento>> = _medicamentos

    fun loadMedicamentosForUser(userId: Int) {
        viewModelScope.launch {
            _medicamentos.value = medicamentoDao.getMedicamentosByUserId(userId)
        }
    }

    fun addMedicamento(medicamento: Medicamento) {
        viewModelScope.launch {
            medicamentoDao.insert(medicamento)
            _medicamentos.value = medicamentoDao.getMedicamentosByUserId(medicamento.userId)
        }
    }

    fun updateMedicamento(updatedMedicamento: Medicamento) {
        viewModelScope.launch {
            medicamentoDao.update(updatedMedicamento)
            _medicamentos.value = medicamentoDao.getMedicamentosByUserId(updatedMedicamento.userId)
        }
    }

    fun deleteMedicamento(medicamentoToDelete: Medicamento) {
        viewModelScope.launch {
            medicamentoDao.delete(medicamentoToDelete)
            _medicamentos.value = medicamentoDao.getMedicamentosByUserId(medicamentoToDelete.userId)
        }
    }

    fun updateIsActive(id: Int, isActive: Boolean) {
        viewModelScope.launch {
            medicamentoDao.updateIsActive(id, isActive)
            val userId = _medicamentos.value.find { it.id == id }?.userId
            if (userId != null) {
                _medicamentos.value = medicamentoDao.getMedicamentosByUserId(userId)
            }
        }
    }

    fun updateIsTakenToday(id: Int, isTaken: Boolean) {
        viewModelScope.launch {
            medicamentoDao.updateIsTakenToday(id, isTaken)
            val updatedMedicamentos = _medicamentos.value.map {
                if (it.id == id) it.copy(isTakenToday = isTaken) else it
            }
            _medicamentos.value = updatedMedicamentos
        }
    }
}
