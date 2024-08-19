package com.example.imran_mamirov_hw_medicineapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.imran_mamirov_hw_medicineapp.data.dao.MedicineDao
import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel

@Database(entities = [MedicineModel::class], version = 1)
abstract class DataBase(): RoomDatabase() {
    abstract fun medicineDao(): MedicineDao
}