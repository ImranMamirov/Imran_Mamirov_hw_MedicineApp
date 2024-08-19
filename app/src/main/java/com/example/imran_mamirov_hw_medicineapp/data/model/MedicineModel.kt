package com.example.imran_mamirov_hw_medicineapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "model")
data class MedicineModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int ?= null,
    var title: String,
    var description: String,
    var time: String,
    var quantity: Int,
    var date: String? = null,
    var counter: Int = 0
)