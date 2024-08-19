package com.example.imran_mamirov_hw_medicineapp.ui.interfaces

import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel

interface OnClickItem {
    fun onLongClick(medicinesModel: MedicineModel)

    fun onClick(medicinesModel: MedicineModel)
}