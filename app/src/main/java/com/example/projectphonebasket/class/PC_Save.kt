package com.example.projectphonebasket.`class`

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PC_Save(
    @PrimaryKey
    var id : Int,
    var server : String,
    var productCommand: Product_Command,
)
