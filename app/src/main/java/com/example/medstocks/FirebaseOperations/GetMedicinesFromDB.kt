package com.example.medstocks.FirebaseOperations

import com.example.medstocks.Models.Medicine
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GetMedicinesFromDB {
    constructor()

    fun getMedicinesFromDB(callback: onMedicinesLoadedListener) {
        // get medicines from realtime database...

        val medicines = ArrayList<Medicine>()
        val dbRef = FirebaseDatabase.getInstance().getReference("Medicines")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                medicines.clear()
                for(medicineSnapshot in snapshot.children){
                    val medicine = medicineSnapshot.getValue(Medicine::class.java)
                    medicines.add(medicine!!)
                }
                callback.onMedicinesLoaded(medicines)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    interface onMedicinesLoadedListener {
        fun onMedicinesLoaded(medicines: ArrayList<Medicine>)
    }
}