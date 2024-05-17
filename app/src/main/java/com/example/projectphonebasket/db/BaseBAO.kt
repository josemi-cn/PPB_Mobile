package com.example.projectphonebasket.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.projectphonebasket.`class`.Product
import com.example.projectphonebasket.`class`.Product_Command

@Database(entities = [Product::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO
}

abstract class BaseBao {



}