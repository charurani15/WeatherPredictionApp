package com.charurani.weatherpredictionapp.app.datasource

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.charurani.weatherpredictionapp.app.data.model.CheckPermissionInputModel
import javax.inject.Inject

class HardwarePermissionDataStore @Inject constructor(val context: Context) : PermissionDataStore {
    override fun checkForPermission(checkPermissionInputModel: CheckPermissionInputModel): CheckPermissionInputModel {
        if (ContextCompat.checkSelfPermission(
                context,
                checkPermissionInputModel.permissionType
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermissionInputModel.shouldShowRequestPermissionRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            checkPermissionInputModel.permissionGrantedAlready = false
        } else {
            checkPermissionInputModel.shouldShowRequestPermissionRationale = false
            checkPermissionInputModel.permissionGrantedAlready = true
        }
        return checkPermissionInputModel
    }
}