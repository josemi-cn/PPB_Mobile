package com.example.projectphonebasket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectphonebasket.databinding.ActivityListBinding
import com.example.projectphonebasket.databinding.ActivityNumberBinding

class NumberActivity : AppCompatActivity() {
    lateinit var binding: ActivityNumberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var number = intent.getIntExtra("number", 0)

        if (number < 10) {
            binding.number.text = "00" + number.toString()
        } else if (number < 100) {
            binding.number.text = "0" + number.toString()
        } else {
            binding.number.text = number.toString()
        }

    }
}