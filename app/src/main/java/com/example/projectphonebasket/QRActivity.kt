package com.example.projectphonebasket

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.projectphonebasket.databinding.ActivityQrBinding


class QRActivity : AppCompatActivity() {
    private lateinit var binding : ActivityQrBinding
    private lateinit var codeScanner: CodeScanner
    private var allPermissions = false
    private var REQUEST_PER_CAMERA : Int = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.scannerView.scaleX = 1f

        if (revisarPermisos()){
            allPermissions = true
            startScanner()
        } else {
            if (comprovarJaHaDemanatPermis()) {
                Toast.makeText(this, "Permís denegat", Toast.LENGTH_LONG).show()
            } else {
                demanarPermis()
            }
        }
    }

    private fun startScanner() {
        val scannerView: CodeScannerView = binding.scannerView
        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK
        codeScanner.formats = CodeScanner.ALL_FORMATS

        codeScanner.autoFocusMode = AutoFocusMode.SAFE
        codeScanner.scanMode = ScanMode.SINGLE
        codeScanner.isAutoFocusEnabled = true
        codeScanner.isFlashEnabled = false

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                val intent = Intent()
                intent.putExtra("value", it.text)
                setResult(RESULT_OK, intent)
                finish()
            }
        }

        codeScanner.errorCallback = ErrorCallback {
            runOnUiThread {
                Toast.makeText(this, "Camera initialization error: ${it.message}", Toast.LENGTH_LONG).show()
            }
        }

        codeScanner.startPreview()
    }

    override fun onResume() {
        super.onResume()

        if (::codeScanner.isInitialized) {
            codeScanner?.startPreview()
        }
    }

    override fun onPause() {
        if (::codeScanner.isInitialized) {
            codeScanner?.releaseResources()
        }
        super.onPause()
    }

    private fun revisarPermisos() : Boolean {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    private fun comprovarJaHaDemanatPermis() : Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)
    }

    private fun demanarPermis(){
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_PER_CAMERA)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PER_CAMERA){
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startScanner()
            }
        }
    }
}