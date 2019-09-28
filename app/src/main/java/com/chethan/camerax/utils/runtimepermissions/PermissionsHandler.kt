package com.chethan.camerax.utils.runtimepermissions

import androidx.appcompat.app.AppCompatActivity



/**
 * Created by Chethan on 8/17/2019.
 */

interface PermissionsHandler {
  fun checkHasPermission(activity: AppCompatActivity, permission: String): Boolean
  fun requestPermission(activity: AppCompatActivity, permissions: Array<String>, requestCode: Int)
}