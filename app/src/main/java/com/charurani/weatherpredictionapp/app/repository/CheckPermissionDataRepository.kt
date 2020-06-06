package com.charurani.weatherpredictionapp.app.repository

import com.charurani.weatherpredictionapp.app.data.model.CheckPermissionInputModel
import com.charurani.weatherpredictionapp.app.datasource.PermissionDataStore
import javax.inject.Inject

class CheckPermissionDataRepository @Inject constructor(val permissionDataStore: PermissionDataStore) {

    fun checkForPermission(checkPermissionInputModel: CheckPermissionInputModel) : CheckPermissionInputModel{
        return permissionDataStore.checkForPermission(checkPermissionInputModel)
    }
}