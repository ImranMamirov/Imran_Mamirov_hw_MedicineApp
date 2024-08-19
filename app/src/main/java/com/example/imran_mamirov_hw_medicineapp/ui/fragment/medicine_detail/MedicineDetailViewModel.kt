package com.example.imran_mamirov_hw_medicineapp.ui.fragment.medicine_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel
import com.example.imran_mamirov_hw_medicineapp.data.repository.MedicineRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MedicineDetailViewModel @Inject constructor(private val repository: MedicineRepository) : ViewModel() {

    fun insertMedicines(medicinesModel: MedicineModel) {
        viewModelScope.launch {
            repository.insert(medicinesModel)
        }
    }

    fun updateMedicine(medicinesModel: MedicineModel) {
        viewModelScope.launch {
            repository.update(medicinesModel)
        }
    }


    fun getMedicineId(id: Int): LiveData<MedicineModel> {
        Log.e("TAG", "getMedicineId ${repository.getMedicineById(id)}")
        return repository.getMedicineById(id)
    }
}