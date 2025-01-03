package com.example.medstocks

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.medstocks.Adapters.MedicineListAdapter
import com.example.medstocks.DialogFragments.AddNewMedicineDialog
import com.example.medstocks.FirebaseOperations.GetMedicinesFromDB
import com.example.medstocks.Models.Medicine
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val addNewMedicineButton:Button = findViewById(R.id.newMedicineButton)
        addNewMedicineButton.setOnClickListener {
            val dialog = AddNewMedicineDialog()
            dialog.show(supportFragmentManager, "AddNewMedicineDialog")
        }

        var medicineList:RecyclerView = findViewById(R.id.medicineList)
        var medicines:ArrayList<Medicine> = ArrayList()

        val getMedicinesFromDB = GetMedicinesFromDB()
        getMedicinesFromDB.getMedicinesFromDB(object : GetMedicinesFromDB.onMedicinesLoadedListener {
            override fun onMedicinesLoaded(medicines: ArrayList<Medicine>) {
                medicineList.layoutManager = LinearLayoutManager(this@MainActivity)
                medicineList.adapter = MedicineListAdapter(this@MainActivity, medicines)
                medicineList.adapter?.notifyDataSetChanged()
            }
        })





    }
}