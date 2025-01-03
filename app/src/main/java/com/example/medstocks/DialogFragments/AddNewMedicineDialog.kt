package com.example.medstocks.DialogFragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView.EdgeEffectFactory.EdgeDirection
import com.example.medstocks.FirebaseOperations.AddMedicineToDB
import com.example.medstocks.Models.Medicine
import com.example.medstocks.R
import java.util.Calendar

class AddNewMedicineDialog : DialogFragment() {

    lateinit var medicineExpiryDateInput:EditText
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_new_medicine_dialog, container, false)
        val dialog = getDialog()
        if (dialog != null) {
            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        val medicineNameInput = view.findViewById<EditText>(R.id.medicineNameInput)
        val medicineCompanyNameInput = view.findViewById<EditText>(R.id.medicineCompanyInput)
        medicineExpiryDateInput = view.findViewById(R.id.medicineExpiryDateInput)
        val medicineQuantityInput = view.findViewById<EditText>(R.id.medicineQuantityInput)
        val saveNewMedicineButton = view.findViewById<Button>(R.id.saveNewMedicineButton)

        medicineExpiryDateInput.setOnClickListener {
            showDatePickerDialog()
        }

        saveNewMedicineButton.setOnClickListener {
            val medicineName = medicineNameInput.text.toString()
            val medicineCompanyName = medicineCompanyNameInput.text.toString()
            val medicineExpiryDate = medicineExpiryDateInput.text.toString()
            val medicineQuantity = medicineQuantityInput.text.toString().toLong()

            if (medicineName.isEmpty() || medicineExpiryDate.isEmpty() || medicineQuantity.toString().isEmpty()){
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(requireContext(), "Medicine added", Toast.LENGTH_SHORT).show()
                var medicine = Medicine(randomFourDigitIdGenerator(), medicineName,medicineQuantity, medicineExpiryDate, medicineCompanyName)
                AddMedicineToDB().addMedicineToDB(medicine)
                dismiss()
            }
            // add new medicine to database
        }



        return view
    }

    override fun onStart() {
        super.onStart()

        val dialog: Dialog? = getDialog()

        if (dialog != null){
            val window = dialog.getWindow()
            if (window != null){
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                window.setGravity(Gravity.CENTER)

                val params = window.getAttributes()
                params.width = WindowManager.LayoutParams.MATCH_PARENT
                params.height = WindowManager.LayoutParams.WRAP_CONTENT
                params.horizontalMargin = 0f;
                params.verticalMargin = 0f;

                window.setWindowAnimations(R.style.DialogAnimation)
            }
        }
    }
    // shows date picker dialog
    private fun showDatePickerDialog() {
        val calendar: Calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(),
            { view, year, monthOfYear, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                medicineExpiryDateInput.setText(selectedDate)
            }, year, month, day)

        datePickerDialog.show()

    }

    private fun randomFourDigitIdGenerator():String{
        val random = (1000..9999).random()
        return random.toString()
    }
}