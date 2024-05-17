package com.example.projectphonebasket.`class`

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Verification(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    var name : String,
    var link : String,
    var successful : Boolean
)
