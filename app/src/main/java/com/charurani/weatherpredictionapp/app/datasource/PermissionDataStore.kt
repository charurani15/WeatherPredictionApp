package com.charurani.weatherpredictionapp.app.datasource

import com.charurani.weatherpredictionapp.app.data.model.CheckPermissionInputModel

interface PermissionDataStore {
    fun checkForPermission(checkPermissionInputModel: CheckPermissionInputModel) : CheckPermissionInputModel
}