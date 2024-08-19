package com.example.imran_mamirov_hw_medicineapp.ui.fragment.medicine

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel
import com.example.imran_mamirov_hw_medicineapp.databinding.ItemMedicineBinding
import com.example.imran_mamirov_hw_medicineapp.ui.interfaces.OnClickItem
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MedicineAdapter(
    private val onLongClickItem: OnClickItem,
    private val onClickItem: OnClickItem,
    private val onQuantityChanged: (MedicineModel) -> Unit
) : androidx.recyclerview.widget.ListAdapter<MedicineModel, MedicineAdapter.ViewHolder>(
    DiffCallback()
) {

    class ViewHolder(private val binding: ItemMedicineBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(model: MedicineModel, onQuantityChanged: (MedicineModel) -> Unit) = with(binding) {
            medicineTitle.text = model.title
            medicineDescription.text = model.description
            progressBar.progress = model.quantity
            medicineCount.text = model.quantity.toString()
            time.text = if (model.date != "No date selected") "${model.date} ${model.time}" else model.time

            val currentDate = Calendar.getInstance().time
            val delayedDate = if (model.date != null && model.date != "No date selected")
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(model.date)
            else
                null

            if (delayedDate != null && currentDate.before(delayedDate)) {
                (root as CardView).setCardBackgroundColor(Color.GRAY)
            } else {
                (root as CardView).setCardBackgroundColor(Color.WHITE)
            }

            btnPlus.setOnClickListener {
                model.quantity++
                medicineCount.text = model.quantity.toString()
                progressBar.progress = model.quantity
                onQuantityChanged(model)
            }

            btnMinus.setOnClickListener {
                if (model.quantity > 0) {
                    model.quantity--
                    medicineCount.text = model.quantity.toString()
                    progressBar.progress = model.quantity
                    onQuantityChanged(model)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemMedicineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), onQuantityChanged)
        holder.itemView.setOnLongClickListener {
            onLongClickItem.onLongClick(getItem(position))
            true
        }
        holder.itemView.setOnClickListener {
            onClickItem.onClick(getItem(position))
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MedicineModel>() {
        override fun areItemsTheSame(oldItem: MedicineModel, newItem: MedicineModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MedicineModel, newItem: MedicineModel): Boolean {
            return oldItem == newItem
        }
    }
}