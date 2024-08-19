package com.example.imran_mamirov_hw_medicineapp.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel

@Dao
interface MedicineDao {
    @Query("SELECT * FROM model")
    fun getAllMedicines(): LiveData<List<MedicineModel>>

    @Query("SELECT * FROM model WHERE id = :id")
    fun getMedicineById(id: Int): LiveData<MedicineModel>

    @Insert
    suspend fun insertMedicines(medicines: MedicineModel)

    @Delete
    suspend fun deleteMedicines(medicines: MedicineModel)

    @Update
    suspend fun updateMedicines(medicines: MedicineModel)

}