package com.example.qrscanner

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.getSystemService
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class MainActivity : AppCompatActivity(),ZXingScannerView.ResultHandler {

  private var scannerView:ZXingScannerView?=null
  private var tvResult: TextView?=null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    scannerView=findViewById(R.id.scanner)
    tvResult=findViewById(R.id.tvScanner)

    if(!checkPermission())
    {
      requestPermission()
    }
  }

  private fun requestPermission() {
    ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA),1)
  }

  private fun checkPermission():Boolean{
  return ContextCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED
}
  override fun handleResult(p0: Result?) {
    val result :String?=p0?.text.toString()
    val vibrator=applicationContext.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    vibrator.vibrate(1000)
    tvResult?.text=result

//    val alert=AlertDialog.Builder(this)
//    alert.setTitle("Result")
//    alert.setPositiveButton("OK"){dialog, which ->
//      scannerView?.resumeCameraPreview(this)
//      startActivity(intent)
//    }
//    alert.setMessage(result)
//    val builder=AlertDialog.Builder(this)
//    builder.show()

  }
  override fun onResume() {
    super.onResume()
    if(checkPermission()){
      if(scannerView==null){
        scannerView=findViewById(R.id.scanner)
        setContentView(scannerView)
      }
      scannerView?.setResultHandler(this)
      scannerView?.startCamera()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    scannerView?.stopCamera()
  }
}
