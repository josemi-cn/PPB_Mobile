package com.example.projectphonebasket.`class`

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @PrimaryKey
    var id : Int,
    var barcode : String,
    var image : String,
    var name : String,
    var price : Double,
)
