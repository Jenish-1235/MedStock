package com.example.medstocks.FirebaseOperations

import com.google.firebase.database.FirebaseDatabase

class UpdateMedicineQuantityInDB {
    constructor()
    fun updateMedicineQuantityInDB(medicineId: String, newQuantity: Long){
        val dbRef = FirebaseDatabase.getInstance().getReference("Medicines")
        dbRef.child(medicineId).child("quantity").setValue(newQuantity)
    }
}