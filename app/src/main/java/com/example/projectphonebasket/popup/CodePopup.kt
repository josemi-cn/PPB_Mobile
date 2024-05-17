package com.example.projectphonebasket.popup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projectphonebasket.R
import com.example.projectphonebasket.databinding.ActivityCodePopupBinding
import com.example.projectphonebasket.databinding.ActivityListBinding

class CodePopup : AppCompatActivity() {
    lateinit var binding: ActivityCodePopupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCodePopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sendButton.setOnClickListener {
            val intent = Intent()
            intent.putExtra("value", binding.code.text.toString())
            setResult(RESULT_OK, intent)
            finish()
        }

        binding.cancelButton.setOnClickListener {
            finish()
        }
    }
}