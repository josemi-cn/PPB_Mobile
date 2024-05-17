package com.example.projectphonebasket.popup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projectphonebasket.R
import com.example.projectphonebasket.databinding.ActivityPayOptionsPopupBinding

class PayOptionsPopup : AppCompatActivity() {
    lateinit var binding: ActivityPayOptionsPopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOptionsPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent()
        intent.putExtra("value", 0)

        binding.fisic.setOnClickListener {
            intent.putExtra("value", 1)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.online.setOnClickListener {
            Toast.makeText(this, "Funció no disponible", Toast.LENGTH_LONG).show()
            /*intent.putExtra("value", 2)
            setResult(RESULT_OK, intent)
            finish()*/
        }
    }
}