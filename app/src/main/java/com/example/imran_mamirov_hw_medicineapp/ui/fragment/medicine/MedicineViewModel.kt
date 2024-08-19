package com.example.imran_mamirov_hw_medicineapp.ui.fragment.medicine

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel
import com.example.imran_mamirov_hw_medicineapp.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineViewModel @Inject constructor(private val repository: MedicineRepository) : ViewModel() {

    fun getAll(): LiveData<List<MedicineModel>> {
        return repository.getAll()
    }

    suspend fun delete(medicinesModel: MedicineModel) {
        return repository.delete(medicinesModel)
    }

    fun update(medicinesModel: MedicineModel) = viewModelScope.launch {
        repository.update(medicinesModel)
    }
}