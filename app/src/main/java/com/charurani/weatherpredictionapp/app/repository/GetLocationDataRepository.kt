package com.charurani.weatherpredictionapp.app.repository

import android.location.Location
import androidx.lifecycle.LiveData
import com.charurani.weatherpredictionapp.app.datasource.GetLocationDataStore
import javax.inject.Inject

class GetLocationDataRepository
@Inject constructor(private val getLocationDataStore: GetLocationDataStore) {
    fun getCurrentOrLastKnownLocation(): LiveData<Location?> {
        return getLocationDataStore.getCurrentOrLastLocation()
    }
}