package com.example.projectphonebasket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.URLUtil
import android.widget.Toast
import androidx.core.net.toUri
import com.example.projectphonebasket.`class`.Verification
import com.example.projectphonebasket.databinding.ActivityMainBinding
import com.example.projectphonebasket.api.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.qrButton.setOnClickListener {
            var intent = Intent(this, QRActivity::class.java)
            startActivityForResult(intent, 0)
        }

        binding.loginButton.setOnClickListener {
            if (binding.serverLink.text.isNotEmpty() &&
                    (
                        binding.serverLink.text.contains("http://") ||
                        binding.serverLink.text.contains("https://")
                    )
                ) {
                ApiClient.BASE_URL = binding.serverLink.text.toString()

                var call = ApiClient.apiService.getVerification()
                call.enqueue(object : Callback<Verification> {
                    override fun onResponse(call: Call<Verification>, response: Response<Verification>) {
                        if (response.isSuccessful) {
                            val o: Verification? = response.body()

                            if (o?.successful == true) {
                                Toast.makeText(this@MainActivity, "Benvingut a " + o?.name + "!", Toast.LENGTH_SHORT).show()

                                var intent = Intent(this@MainActivity, ListActivity::class.java)
                                intent.putExtra("name", o?.name)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@MainActivity, "Aquest servidor no es compatible amb {INSERTAR NOM DE L'APP}", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Verification>, t: Throwable) {
                        Toast.makeText(this@MainActivity, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
                    }
                })
            } else if (binding.serverLink.text.isNotEmpty() && !binding.serverLink.text.contains("http://") && !binding.serverLink.text.contains("https://")) {
                Toast.makeText(this@MainActivity, "El text que has posat no es una url", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Has de posar la url per accedir al servidor", Toast.LENGTH_SHORT).show()
            }
            /*if (binding.serverLink.text.toString().isEmpty()) {
                Toast.makeText(this, resources.getText(R.string.url_empty), Toast.LENGTH_LONG).show()
            } else if (!URLUtil.isHttpUrl(binding.serverLink.text.toString())) {
                Toast.makeText(this, resources.getText(R.string.url_error), Toast.LENGTH_LONG).show()
            } else {

            }*/
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data?.getStringExtra("value") != null &&
                (
                    data?.getStringExtra("value")!!.contains("http://") ||
                    data?.getStringExtra("value")!!.contains("https://")
                )
            ) {
            ApiClient.BASE_URL = data?.getStringExtra("value").toString()

            var call = ApiClient.apiService.getVerification()
            call.enqueue(object : Callback<Verification> {
                override fun onResponse(call: Call<Verification>, response: Response<Verification>) {
                    if (response.isSuccessful) {
                        val o: Verification? = response.body()

                        if (o?.successful == true) {
                            Toast.makeText(this@MainActivity, "Benvingut a " + o?.name + "!", Toast.LENGTH_SHORT).show()

                            var intent = Intent(this@MainActivity, ListActivity::class.java)
                            intent.putExtra("name", o?.name)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Aquest servidor no es compatible amb {INSERTAR NOM DE L'APP}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Verification>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
                }
            })
        } else if (data?.getStringExtra("value") != null && !data?.getStringExtra("value")!!.contains("http://") && !data?.getStringExtra("value")!!.contains("https://")) {
            Toast.makeText(this@MainActivity, "El text que has posat no es una url", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@MainActivity, "Has de posar la url per accedir al servidor", Toast.LENGTH_SHORT).show()
        }
    }
}