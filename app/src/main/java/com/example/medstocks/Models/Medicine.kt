package com.example.medstocks.Models

data class Medicine(
    val id:String,
    val name:String,
    val quantity:Long,
    val expiryDate:String,
    val companyName:String
)
