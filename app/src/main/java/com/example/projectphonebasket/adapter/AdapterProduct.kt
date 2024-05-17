package com.example.projectphonebasket.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projectphonebasket.ListActivity
import com.example.projectphonebasket.R
import com.example.projectphonebasket.api.ApiClient
import com.example.projectphonebasket.`class`.Product
import com.example.projectphonebasket.`class`.Product_Command
import com.example.projectphonebasket.popup.DCPopup
import com.example.projectphonebasket.popup.ProductPopup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdapterProduct(var products: ArrayList<Product_Command>, val activity: ListActivity): RecyclerView.Adapter<AdapterProduct.ViewHolder>() {
    class ViewHolder(val vista: View) : RecyclerView.ViewHolder(vista) {
        val image = vista.findViewById<ImageView>(R.id.image)
        val name = vista.findViewById<TextView>(R.id.name)
        val quantity = vista.findViewById<TextView>(R.id.quantity)
        val price = vista.findViewById<TextView>(R.id.price)
        val remove = vista.findViewById<Button>(R.id.remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        var call = ApiClient.apiService.getProduct(products[position].productId)
        call.enqueue(object : Callback<Product> {
            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                if (response.isSuccessful) {
                    val o: Product? = response.body()

                    Glide.with(holder.vista).load(ApiClient.BASE_URL + "api/Images/" + o?.image).into(holder.image)

                    holder.image.setBackgroundColor(Color.WHITE)

                    holder.name.text = o?.name

                    if (products[position].quantity == 1) {
                        holder.quantity.text = products[position].quantity.toString() + " unitat"
                    } else if (products[position].quantity > 1) {
                        holder.quantity.text = products[position].quantity.toString() + " unitats"
                    }

                    var totalPrice = o?.price!! * products[position].quantity

                    holder.price.text = totalPrice.toString() + "€"
                } else {
                    Toast.makeText(holder.vista.context, "El valor enviat no es compatible", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Toast.makeText(holder.vista.context, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
            }
        })

        holder.remove.setOnClickListener {
            var intent = Intent(activity, DCPopup::class.java)

            intent.putExtra("position", position)

            startActivityForResult(activity, intent, 5, null)
        }
    }

    override fun getItemCount() = products.size
}