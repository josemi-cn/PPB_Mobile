package com.example.projectphonebasket.popup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectphonebasket.R
import com.example.projectphonebasket.databinding.ActivityCodePopupBinding
import com.example.projectphonebasket.databinding.ActivityDcpopupBinding

class DCPopup : AppCompatActivity() {
    lateinit var binding: ActivityDcpopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDcpopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.putExtra("value", 1)

        binding.yes.setOnClickListener {
            intent.putExtra("value", 0)
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.no.setOnClickListener {
            intent.putExtra("value", 1)
            finish()
        }
    }
}