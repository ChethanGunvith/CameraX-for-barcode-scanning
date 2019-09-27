package com.chethan.airchip.utils.runtimepermissions

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Chethan on 8/17/2019.
 */

@Singleton
class PermissionsHandlerAndroid : PermissionsHandler {

  @Inject
  constructor()

  override fun checkHasPermission(activity: AppCompatActivity, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
  }

  override fun requestPermission(activity: AppCompatActivity, permissions: Array<String>, requestCode: Int) {
    ActivityCompat.requestPermissions(activity, permissions, requestCode)
  }
}