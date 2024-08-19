package com.example.imran_mamirov_hw_medicineapp.ui.fragment.medicine_detail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.imran_mamirov_hw_medicineapp.R
import com.example.imran_mamirov_hw_medicineapp.data.model.MedicineModel
import com.example.imran_mamirov_hw_medicineapp.databinding.FragmentMedicineBinding
import com.example.imran_mamirov_hw_medicineapp.databinding.FragmentMedicineDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class MedicineDetailFragment : Fragment() {
    private var _binding: FragmentMedicineDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MedicineDetailViewModel by viewModels()
    private val args: MedicineDetailFragmentArgs by navArgs()
    private var existingMedicine: MedicineModel? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMedicineDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBottomNavigation()
        setupUI()
        initializeData()
        setUpListeners()
    }

    private fun hideBottomNavigation() {
        activity?.findViewById<Button>(R.id.btn_plus)?.visibility = View.GONE
    }

    private fun showBottomNavigation() {
        activity?.findViewById<Button>(R.id.btn_plus)?.visibility = View.VISIBLE
    }

    private fun setupUI() = with(binding) {
        selectTimeButton.setOnClickListener { showTimePickerDialog() }
        selectDateButton.setOnClickListener { showDatePickerDialog() }
        selectDosageButton.setOnClickListener { showDosagePickerDialog() }
        timeCheckbox.setOnCheckedChangeListener { _, isChecked ->
            dateLayout.isVisible = isChecked
            delayedMedicineButton.isEnabled = isChecked
            if (!isChecked) dateTextView.text = null
        }
    }

    private fun initializeData() {
        val medicineId = args.Id
        if (medicineId != -1) {
            viewModel.getMedicineId(medicineId).observe(viewLifecycleOwner) { medicine ->
                medicine?.let {
                    existingMedicine = it
                    bindMedicineData(it)
                }
            }
        }
    }

    private fun bindMedicineData(medicine: MedicineModel) = with(binding) {
        etTitle.setText(medicine.title)
        etDescription.setText(medicine.description)
        dosageTextView.text = medicine.quantity.toString()
        timeTextView.text = medicine.time
        dateTextView.text = medicine.date
    }

    private fun setUpListeners() = with(binding) {
//        closeButton.setOnClickListener { navigateBack() }
        addButton.setOnClickListener { handleMedicineAction(isUpdate = false) }
        changeButton.setOnClickListener { handleMedicineAction(isUpdate = true) }
        delayedMedicineButton.setOnClickListener {  }
    }

//    private fun navigateBack() {
//        findNavController().navigate(R.id.navigation_medicines)
//    }

    private fun handleMedicineAction(isUpdate: Boolean) = with(binding) {
        val medicineName = etTitle.text.toString()
        val description = etDescription.text.toString()
        val dosageText = dosageTextView.text.toString()
        val time = timeTextView.text.toString()
        val date = dateTextView.text.toString()

        if (medicineName.isEmpty() || description.isEmpty() || dosageText.isEmpty() || time.isEmpty() || date.isEmpty()) {
            showToast("Заполните все поля")
            return
        }

        val dosage = dosageText.toIntOrNull() ?: 1
        if (dosage == 0) {
            showToast("Некорректная дозировка")
            return
        }

        val medicinesModel = existingMedicine?.copy(
            title = medicineName, description = description, quantity = dosage, time = time, date = date
        ) ?: MedicineModel(
            title = medicineName, description = description, quantity = dosage, time = time, date = date
        )

        if (isUpdate) {
            viewModel.updateMedicine(medicinesModel)
            showToast("Медикамент обновлен")
        } else {
            viewModel.insertMedicines(medicinesModel)
            showToast("Медикамент добавлен")
        }
//        navigateBack()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            binding.timeTextView.text = String.format("%02d:%02d", selectedHour, selectedMinute)
        }, hour, minute, true).show()
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
            binding.dateTextView.text = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
        }, year, month, day).show()
    }

    private fun showDosagePickerDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Dosage")
            val input = EditText(requireContext()).apply { inputType = InputType.TYPE_CLASS_NUMBER }
            setView(input)
            setPositiveButton("OK") { _, _ ->
                val dosage = input.text.toString()
                if (dosage.isNotEmpty()) {
                    binding.dosageTextView.text = dosage
                } else {
                    showToast("Dosage cannot be empty")
                }
            }
            setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        showBottomNavigation()
        _binding = null
    }
}