package com.charurani.weatherpredictionapp.app.datasource

import android.location.Location
import androidx.lifecycle.LiveData

interface GetLocationDataStore {
    //Interface method in the data
    fun getCurrentOrLastLocation(): LiveData<Location?>
}