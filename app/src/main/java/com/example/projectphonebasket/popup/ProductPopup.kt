package com.example.projectphonebasket.popup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.example.projectphonebasket.R
import com.example.projectphonebasket.api.ApiClient
import com.example.projectphonebasket.`class`.Product
import com.example.projectphonebasket.databinding.ActivityCodePopupBinding
import com.example.projectphonebasket.databinding.ActivityProductPopupBinding

class ProductPopup : AppCompatActivity() {
    lateinit var binding: ActivityProductPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var product = Product(
            intent.getIntExtra("id", 0),
            "" + intent.getStringExtra("barcode"),
            "" + intent.getStringExtra("image"),
            "" + intent.getStringExtra("name"),
            intent.getDoubleExtra("price", 0.0)
        )

        Glide.with(this).load(ApiClient.BASE_URL + "api/Images/" + product.image).into(binding.image)

        binding.name.setText(product.name)
        binding.price.setText(product.price.toString() + "€")
        binding.totalPrice.setText(product.price.toString() + "€")

        setResult(RESULT_CANCELED, intent)

        binding.quantity.addTextChangedListener {
            if (binding.quantity.text.isNotEmpty()) {
                if (binding.quantity.text.toString().toInt() > 0) {
                    binding.totalPrice.setText(
                        (
                                product.price * binding.quantity.text.toString().toInt()
                        ).toString() + "€"
                    )

                    intent.putExtra("quantity", binding.quantity.text.toString().toInt())
                }
            }
        }

        binding.addButton.setOnClickListener {
            intent.putExtra("productId", product.id)
            intent.putExtra("commandId", 0)
            intent.putExtra("quantity", binding.quantity.text.toString().toInt())
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            setResult(RESULT_CANCELED, intent)
            finish()
        }
    }
}