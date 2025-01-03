package com.example.medstocks.DialogFragments

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
import com.example.medstocks.FirebaseOperations.UpdateMedicineQuantityInDB
import com.example.medstocks.R

class SellMedicineFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sell_medicine, container, false)
        val dialog = getDialog()
        if (dialog != null) {
            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        }

        val bundle = arguments
        val medicineId = bundle?.getString("medicineId")
        val medicineQuantity = bundle?.getString("medicineQuantity")

        val medicineQuantityInput = view.findViewById<EditText>(R.id.medicineQuantityInput)

        val sellMedicineButton = view.findViewById<Button>(R.id.sellMedicineButton)
        sellMedicineButton.setOnClickListener{
            val medicineQuantityNew = medicineQuantityInput.text.toString().toLong()
            if (medicineQuantity.toString().isEmpty()){
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }else if (medicineQuantityNew > medicineQuantity.toString().toLong()){
                Toast.makeText(requireContext(), "Not enough medicine in stock", Toast.LENGTH_SHORT).show()
            }
            else{
                var newQuantity = medicineQuantity.toString().toLong() - medicineQuantityNew
                UpdateMedicineQuantityInDB().updateMedicineQuantityInDB(medicineId.toString(), newQuantity)
                Toast.makeText(requireContext(), "Medicine sold", Toast.LENGTH_SHORT).show()
                dismiss()
            }
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
}