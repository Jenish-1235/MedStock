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
import com.example.medstocks.R

class PurchaseMedicineFragment : DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_purchase_medicine, container, false)
        val dialog = getDialog()
        if (dialog != null) {
            dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
        }
        val medicineQuantityInput = view.findViewById<EditText>(R.id.medicineQuantityInput)

        val bundle = arguments
        val medicineId = bundle?.getString("medicineId")
        val medicineQuantity = bundle?.getString("medicineQuantity")

        val purchaseMedicineButton = view.findViewById<Button>(R.id.purchaseMedicineButton)
        purchaseMedicineButton.setOnClickListener{
            val medicineQuantity = medicineQuantityInput.text.toString().toLong()
            if (medicineQuantity.toString().isEmpty()){
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(requireContext(), "Medicine purchased", Toast.LENGTH_SHORT).show()
            }
        }


        return view

    }


    // sets positioning of dialog fragment to bottom of screen.
    override fun onStart() {
        super.onStart()

        val dialog: Dialog? = getDialog()

        if (dialog != null){
            val window = dialog.getWindow()
            if (window != null){
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                window.setGravity(Gravity.BOTTOM)

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