package com.charurani.weatherpredictionapp.app.data.model

data class CheckPermissionInputModel(
    var permissionRequestCode: Int,
    var permissionType: String,
    var shouldShowRequestPermissionRationale: Boolean = false,
    var permissionGrantedAlready: Boolean = false
)