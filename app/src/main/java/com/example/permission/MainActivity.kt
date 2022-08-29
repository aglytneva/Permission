package com.example.permission


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat



//  Конструкция :Toast.makeText (this, "Любой текст", Toast.LENGTH_LONG).show()
//  предполагает вывод системного сообщения

//Обязательное наследование от ActivityCompat.OnRequestPermissionsResultCallback
class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    companion object{
        const val PERMISSION_REQUEST_CAMERA = 0
    }

    private val btnCamera:Button by lazy {findViewById(R.id.btnCamera)}
    private val layout: ConstraintLayout by lazy { findViewById(R.id.mainLayout) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnCamera.setOnClickListener {
           showCameraPreview()
        }




    }

    private fun showCameraPreview() {
        // Check if the Camera permission has been granted
        if (checkSelfPermissionCompat(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already available, start camera preview
            startCamera()
            Toast.makeText (this, "Разрешение предоставлено", Toast.LENGTH_LONG).show()
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission()
        }
    }


    private fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationaleCompat(Manifest.permission.CAMERA)){
            Toast.makeText (this, "Разрешение не предоставлено", Toast.LENGTH_LONG).show()

        } else {

//            layout.showSnackbar(R.string.go_to_settings,
//                Snackbar.LENGTH_INDEFINITE, R.string.ok) {
//                requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
//                startAppSettings()
//            }
            // Request the permission. The result will be received in onRequestPermissionResult().
            Toast.makeText (this, "Разрешение не предоставлено", Toast.LENGTH_LONG).show()
            requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
        }
    }

    private fun startCamera () {
        Log.d("Debug", "CAMERA OPENED")
        val intent = Intent(this, CameraPreviewActivity::class.java)
        startActivity(intent)

    }
    // Функция, открывающая настройки приложения
    private fun startAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts(
            "package",
            packageName, null
        )
        intent.setData (uri)
        startActivity(intent)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode== PERMISSION_REQUEST_CAMERA) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Разрешение предоставлено", Toast.LENGTH_LONG).show()
                requestPermissionsCompat(
                    arrayOf(Manifest.permission.CAMERA),
                    PERMISSION_REQUEST_CAMERA
                )
                startCamera()
            } else {

//                layout.showSnackbar(R.string.go_to_settings,
//                Snackbar.LENGTH_INDEFINITE, R.string.ok) {
//                requestPermissionsCompat(arrayOf(Manifest.permission.CAMERA), PERMISSION_REQUEST_CAMERA)
//                startAppSettings()
//            }
                    Toast.makeText(this, "Разрешение может быть изменено только в настройках",
                        Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
