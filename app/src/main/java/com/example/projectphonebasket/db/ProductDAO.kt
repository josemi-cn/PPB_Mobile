package com.example.projectphonebasket.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.projectphonebasket.`class`.Product
import com.example.projectphonebasket.`class`.Product_Command
import com.example.projectphonebasket.`class`.PC_Save

@Dao
interface ProductDAO {

    @Query("SELECT * FROM Product")
    fun getAllProducts(): List<Product>

    @Query("SELECT * FROM Product WHERE id = :id")
    fun getProduct(id: Int): Product

    @Insert
    fun insertProduct(product: Product)

    @Update
    fun updateProduct(product: Product)

    @Insert
    fun insertProducts(products: List<Product>)

    @Delete
    fun deleteProduct(product: Product)

    @Query("SELECT * FROM Product_Command")
    fun getAllProductCommands(): List<Product_Command>

    @Query("SELECT * FROM Product_Command WHERE id = :id")
    fun getProductCommand(id: Int): Product_Command

    @Insert
    fun insertProductCommand(productCommand: Product_Command)

    @Update
    fun updateProductCommand(productCommand: Product_Command)

    @Insert
    fun insertProductCommands(productCommand: List<Product_Command>)

    @Delete
    fun deleteProductCommand(productCommand: Product_Command)

    @Query("SELECT * FROM PC_Save")
    fun getAllPCSave(): List<PC_Save>

    @Query("SELECT productCommand FROM PC_Save WHERE server == :server")
    fun getAllPCSaveByServer(server: String): List<Product_Command>

    @Query("SELECT * FROM PC_Save WHERE id = :id")
    fun getPCSave(id: Int): PC_Save

    @Query("SELECT * FROM PC_Save ORDER BY id DESC LIMIT 1")
    fun getLastPCSave(): PC_Save

    @Insert
    fun insertPCSave(pcSave: PC_Save)

    @Update
    fun updatePCSave(pcSave: PC_Save)

    @Insert
    fun insertPCSaves(pcSave: List<PC_Save>)

    @Delete
    fun deletePCSave(pcSave: PC_Save)
}