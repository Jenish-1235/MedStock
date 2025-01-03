package com.example.medstocks.FirebaseOperations

import com.example.medstocks.Models.Medicine
import com.google.firebase.database.FirebaseDatabase

class AddMedicineToDB {
    constructor()

    fun addMedicineToDB(medicine: Medicine) {
        // add medicine to realtime database...
        val dbRef = FirebaseDatabase.getInstance().getReference("Medicines")
        dbRef.child(medicine.id).setValue(medicine)
    }
}