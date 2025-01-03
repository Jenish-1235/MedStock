package com.example.medstocks.Models
class Medicine{

    var id:String = ""
    var name:String = ""
    var quantity:Long = 0
    var expiryDate:String = ""
    var companyName:String = ""

    constructor(id:String, name:String, quantity:Long, expiryDate:String, companyName:String){
        this.id = id
        this.name = name
        this.quantity = quantity
        this.expiryDate = expiryDate
        this.companyName = companyName
    }
    constructor(){}

}
