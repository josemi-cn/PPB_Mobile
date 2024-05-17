package com.example.projectphonebasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectphonebasket.databinding.ActivityMainBinding
import com.example.projectphonebasket.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendButton.setOnClickListener {
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            finishActivity(RESULT_CANCELED)
        }
    }
}