package com.example.permission

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

// определеяетс когда выдано разрешение
fun AppCompatActivity.checkSelfPermissionCompat(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission)

//когда надо показывать кокое-либо уведомление
fun AppCompatActivity.shouldShowRequestPermissionRationaleCompat(permission: String) =
    ActivityCompat.shouldShowRequestPermissionRationale(this, permission)

fun AppCompatActivity.requestPermissionsCompat(permissionsArray: Array<String>,
                                               requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissionsArray, requestCode)
}