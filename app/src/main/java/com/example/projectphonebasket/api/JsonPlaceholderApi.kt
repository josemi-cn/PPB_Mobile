package com.example.projectphonebasket.api

// JsonPlaceholderApi.kt
import com.example.projectphonebasket.`class`.Command
import com.example.projectphonebasket.`class`.Product
import com.example.projectphonebasket.`class`.Product_Command
import com.example.projectphonebasket.`class`.Verification
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface JsonPlaceholderApi {
    @GET("api/verifyIdentity")
    fun getVerification(): Call<Verification>

    @GET("api/products/{id}")
    fun getProduct(@Path("id") id: Int): Call<Product>

    @GET("api/products/barcode/{barcode}")
    fun getProduct(@Path("barcode") barcode: String): Call<Product>

    @GET("api/commands/filter/last")
    fun getLastFilteredCommand(): Call<Command>

    @POST("api/productscommands")
    fun sendProductCommand(@Body productcommand : Product_Command): Call<Product_Command>

    @Headers("Content-Type: application/json")
    @POST("api/commands")
    fun sendCommand(@Body command : Command): Call<Command>
}
