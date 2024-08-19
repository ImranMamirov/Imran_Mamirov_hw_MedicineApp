package com.example.imran_mamirov_hw_medicineapp.data.repository

import androidx.lifecycle.LiveData
import com.example.imran_mamirov_hw_medicineapp.data.dao.MedicineDao
import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel
import javax.inject.Inject

class MedicineRepository @Inject constructor(private val dao: MedicineDao) {

    fun getAll(): LiveData<List<MedicineModel>> = dao.getAllMedicines()

    fun getMedicineById(id: Int): LiveData<MedicineModel> {
        return dao.getMedicineById(id)
    }

    suspend fun insert(medicinesModel: MedicineModel) {
        dao.insertMedicines(medicinesModel)
    }

    suspend fun delete(medicinesModel: MedicineModel) {
        dao.deleteMedicines(medicinesModel)
    }

    suspend fun update(medicinesModel: MedicineModel) {
        dao.updateMedicines(medicinesModel)
    }
}