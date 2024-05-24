package com.example.projectphonebasket

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectphonebasket.adapter.AdapterProduct
import com.example.projectphonebasket.`class`.Product
import com.example.projectphonebasket.`class`.Product_Command
import com.example.projectphonebasket.databinding.ActivityListBinding
import com.example.projectphonebasket.api.ApiClient
import com.example.projectphonebasket.`class`.Command
import com.example.projectphonebasket.popup.CodePopup
import com.example.projectphonebasket.popup.PayOptionsPopup
import com.example.projectphonebasket.popup.ProductPopup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ListActivity : AppCompatActivity() {
    lateinit var binding: ActivityListBinding
    val product_commands: ArrayList<Product_Command> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        /*val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "serversData"
        ).build()

        val productDao = db.productDao()

        CoroutineScope(Dispatchers.IO).launch {
            product_commands.addAll(productDao.getAllPCSaveByServer(ApiClient.BASE_URL))
        }*/

        supportActionBar!!.title = intent.getStringExtra("name")

        updateRecyclerView()

        binding.send.setOnClickListener {
            var intent = Intent(this, PayOptionsPopup::class.java)
            startActivityForResult(intent, 3)
        }
    }

    fun updateRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(binding.recyclerView.context)
        binding.recyclerView.adapter = AdapterProduct(product_commands, this)
    }

    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        menuInflater.inflate(R.menu.menu_1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.code_button -> {
                var intent = Intent(this, CodePopup::class.java)
                startActivityForResult(intent, 0)

            }
            R.id.qr_button -> {
                var intent = Intent(this, QRActivity::class.java)
                startActivityForResult(intent, 1)
            }
            else -> Toast.makeText(this, "Que has seleccionat?", Toast.LENGTH_LONG).show()
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            0, 1 -> {
                if (data?.getStringExtra("value") != null) {
                    var call = ApiClient.apiService.getProduct(data?.getStringExtra("value")!!)
                    call.enqueue(object : Callback<Product> {
                        override fun onResponse(call: Call<Product>, response: Response<Product>) {
                            if (response.isSuccessful) {
                                val o: Product? = response.body()

                                var intent = Intent(this@ListActivity, ProductPopup::class.java)

                                intent.putExtra("id", o?.id)
                                intent.putExtra("barcode", o?.barcode)
                                intent.putExtra("image", o?.image)
                                intent.putExtra("name", o?.name)
                                intent.putExtra("price", o?.price)

                                startActivityForResult(intent, 2)
                            } else {
                                Toast.makeText(this@ListActivity, "El valor enviat no es compatible", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Product>, t: Throwable) {
                            Toast.makeText(this@ListActivity, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
            2 -> {
                if (data!!.getIntExtra("productId", 0) != 0) {
                    Toast.makeText(this, "Producte agregat a la llista!", Toast.LENGTH_SHORT).show()

                    var productCommand = Product_Command(
                        0,
                        data!!.getIntExtra("productId", 0),
                        data!!.getIntExtra("commandId", 0),
                        data!!.getIntExtra("quantity", 1)
                    )

                    /*val db = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java, "serversData"
                    ).build()

                    val productDao = db.productDao()

                    CoroutineScope(Dispatchers.IO).launch {
                        var lastId = 0 + productDao.getLastPCSave().id

                        productDao.insertPCSave(
                            PC_Save(
                                lastId + 1,
                                ApiClient.BASE_URL,
                                productCommand
                            )
                        )
                    }*/

                    product_commands.add(
                        productCommand
                    )

                    updateRecyclerView()
                }
            }
            3 -> {
                var value = data?.getIntExtra("value", 0)
                if (value == 1) {
                    var call = ApiClient.apiService.getLastFilteredCommand()
                    call.enqueue(object : Callback<Command> {
                        override fun onResponse(call: Call<Command>, response: Response<Command>) {
                            if (response.isSuccessful) {
                                val o: Command? = response.body()

                                var c = Command(0, o?.number!!.plus(1), LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME), false, false)

                                sendCommand(c)

                                var intent = Intent(this@ListActivity, NumberActivity::class.java)
                                intent.putExtra("number", c.number)
                                startActivity(intent)

                            } else {
                                Toast.makeText(this@ListActivity, "El valor enviat no es compatible", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<Command>, t: Throwable) {
                            Toast.makeText(this@ListActivity, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
                        }
                    })
                } else if (value == 2) {
                    var intent = Intent(this@ListActivity, PayActivity::class.java)
                    startActivityForResult(intent, 4)
                    finishActivity(RESULT_OK)
                }
            }
            4 -> {
                var call = ApiClient.apiService.getLastFilteredCommand()
                call.enqueue(object : Callback<Command> {
                    override fun onResponse(call: Call<Command>, response: Response<Command>) {
                        if (response.isSuccessful) {
                            val o: Command? = response.body()

                            var c = Command(0, o?.number!!.plus(1), LocalDateTime.now().format(
                                DateTimeFormatter.ISO_DATE_TIME), false, false)

                            sendCommand(c)

                            var intent = Intent(this@ListActivity, NumberActivity::class.java)
                            intent.putExtra("number", c.number)
                            startActivity(intent)

                        } else {
                            Toast.makeText(this@ListActivity, "El valor enviat no es compatible", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Command>, t: Throwable) {
                        Toast.makeText(this@ListActivity, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
                    }
                })
            }
            5 -> {
                if (data != null) {
                    if (data!!.getIntExtra("position", -1) != -1 && data!!.getIntExtra("value", 1) != 1) {
                        product_commands.remove(product_commands[data!!.getIntExtra("position", -1)])

                        updateRecyclerView()
                    }
                }
            }
        }
    }

    fun sendCommand(c : Command) {
        var call = ApiClient.apiService.sendCommand(c)
        call.enqueue(object : Callback<Command> {
            override fun onResponse(call: Call<Command>, response: Response<Command>) {
                if (response.isSuccessful) {
                    var command = response.body()

                    product_commands.forEach {
                        it.commandId = command?.id!!
                        sendProductCommand(it)
                    }

                } else {
                    Toast.makeText(this@ListActivity, "El valor enviat no es compatible", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Command>, t: Throwable) {
                Toast.makeText(this@ListActivity, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun sendProductCommand(c : Product_Command) {
        var call = ApiClient.apiService.sendProductCommand(c)
        call.enqueue(object : Callback<Product_Command> {
            override fun onResponse(call: Call<Product_Command>, response: Response<Product_Command>) {
                if (response.isSuccessful) {
                    val o: Product_Command? = response.body()

                    Toast.makeText(this@ListActivity, o?.commandId.toString(), Toast.LENGTH_SHORT).show()

                } else {
                    Log.d("resposta", response.body().toString())
                    Toast.makeText(this@ListActivity, "El valor enviat no es compatible", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Product_Command>, t: Throwable) {
                Toast.makeText(this@ListActivity, "Error en la sol·licitud", Toast.LENGTH_SHORT).show()
            }
        })
    }
}