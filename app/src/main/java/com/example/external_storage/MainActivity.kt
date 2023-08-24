package com.example.external_storage

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                Array(1) { Manifest.permission.READ_EXTERNAL_STORAGE },
                121
            )
        }
        val btnsave = findViewById(R.id.btnsave) as Button
        val btnview = findViewById(R.id.btnview) as Button
        val edit1 = findViewById(R.id.edit1) as EditText
        val txt1 = findViewById(R.id.txt1) as TextView
        var filename: String = "sample.txt"
        var filepath: String = "mystorage"
        var externalfile: File;
        var data: String = "";
        externalfile = File(getExternalFilesDir(filepath), filename)
        btnsave.setOnClickListener(View.OnClickListener {
            // var fileOutputStream:FileOutputStream=FileOutputStream(externalfile);
            try {
                val fileOutPutStream = FileOutputStream(externalfile)
                fileOutPutStream.write(edit1.text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(applicationContext, "data save", Toast.LENGTH_SHORT).show()

        })
        btnview.setOnClickListener(View.OnClickListener {
            externalfile = File(getExternalFilesDir(filepath), filename)
            if (filename.toString() != null && filename.toString().trim() != "") {
                var fileInputStream = FileInputStream(externalfile)
                var inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()
                //Displaying data on EditText
                Toast.makeText(applicationContext, stringBuilder.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 121 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }
    }
}