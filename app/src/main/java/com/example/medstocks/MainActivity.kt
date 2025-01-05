package com.example.medstocks

import android.app.Notification.Action.Extender
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.util.copy
import com.example.medstocks.Adapters.MedicineListAdapter
import com.example.medstocks.DialogFragments.AddNewMedicineDialog
import com.example.medstocks.FirebaseOperations.GetMedicinesFromDB
import com.example.medstocks.Models.Medicine
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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

        val getMedicinesFromDB = GetMedicinesFromDB()
        getMedicinesFromDB.getMedicinesFromDB(object : GetMedicinesFromDB.onMedicinesLoadedListener {
            override fun onMedicinesLoaded(medicines: ArrayList<Medicine>) {
                medicineList.layoutManager = LinearLayoutManager(this@MainActivity)
                medicineList.adapter = MedicineListAdapter(this@MainActivity, medicines)
                medicineList.adapter?.notifyDataSetChanged()

                // if is sorted by expiry date button is clicked... we sort by expiry and then make the button to behave like sorting by name button


                val sortByExpiryDateButton:ExtendedFloatingActionButton = findViewById(R.id.sortByExpiryDateButton)
                sortByExpiryDateButton.setOnClickListener {
                    if(sortByExpiryDateButton.text == "Sort by Expiry"){
                        val sortedMedicines = normalizeAndSortMedicines(medicines)
                        medicineList.adapter = MedicineListAdapter(this@MainActivity, sortedMedicines)
                        medicineList.adapter?.notifyDataSetChanged()
                        sortByExpiryDateButton.text = "Sort by Name"
                    }else{
                        val sortedMedicines = ArrayList(medicines)
                        sortedMedicines.sortWith(compareBy(String.CASE_INSENSITIVE_ORDER) { it.name })
                        medicineList.adapter = MedicineListAdapter(this@MainActivity, sortedMedicines)
                        medicineList.adapter?.notifyDataSetChanged()
                        sortByExpiryDateButton.text = "Sort by Expiry"
                    }
                }

                val medicineSearchView = findViewById<SearchView>(R.id.search_view)
                medicineSearchView.setOnQueryTextListener(object : OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        val filteredMedicines = ArrayList<Medicine>()
                        for (medicine in medicines) {
                            if (medicine.name.toLowerCase().contains(newText!!.toLowerCase())) {
                                filteredMedicines.add(medicine)
                            }
                            medicineList.adapter = MedicineListAdapter(this@MainActivity, filteredMedicines)
                            medicineList.adapter?.notifyDataSetChanged()
                        }
                        return true
                    }

                })
            }
        })



    }

    fun normalizeAndSortMedicines(medicines: List<Medicine>): ArrayList<Medicine> {
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val possibleFormats = listOf(
            SimpleDateFormat("d/M/yyyy", Locale.getDefault()),
            SimpleDateFormat("dd/M/yyyy", Locale.getDefault()),
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()),
            SimpleDateFormat("d/MM/yyyy", Locale.getDefault())
        )

        // Function to parse and format expiry date
        fun parseExpiryDate(date: String): Date? {
            for (format in possibleFormats) {
                try {
                    return format.parse(date)
                } catch (e: Exception) {
                    // Ignore and try next format
                }
            }
            return null
        }

        val sortedMedicines = ArrayList(medicines)
        sortedMedicines.sortWith(compareBy { medicine ->
            val expiryDate = parseExpiryDate(medicine.expiryDate)
            expiryDate?.time ?: Long.MAX_VALUE
        })
        return sortedMedicines
    }
}