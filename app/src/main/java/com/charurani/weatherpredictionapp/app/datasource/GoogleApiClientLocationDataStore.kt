package com.charurani.weatherpredictionapp.app.datasource

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.location.*
import javax.inject.Inject

private const val REQUEST_GPS_LOCATION: Int = 1234;

class GoogleApiClientLocationDataStore
@Inject constructor(val context: Context) : GetLocationDataStore,
    ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener,
    ResultCallback<LocationSettingsResult>,
    LocationListener {

    private val googleApiClient: GoogleApiClient = GoogleApiClient.Builder(context)
        .addApi(LocationServices.API)
        .addConnectionCallbacks(this@GoogleApiClientLocationDataStore).build()
    private var lastLocation: Location? = null
    private lateinit var locationRequest: LocationRequest
    private var locationMutableLiveData = MediatorLiveData<Location>()

    override fun getCurrentOrLastLocation(): LiveData<Location?> {
        googleApiClient.connect()
        return locationMutableLiveData
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(@Nullable bundle: Bundle?) {
        lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
        locationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = 30 * 1000
        locationRequest.fastestInterval = 5 * 1000
        val locationRequestBuilder =
            LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        locationRequestBuilder.setAlwaysShow(true)
        val pendingResult = LocationServices.SettingsApi.checkLocationSettings(
            googleApiClient,
            locationRequestBuilder.build()
        )
        pendingResult.setResultCallback(this@GoogleApiClientLocationDataStore)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.e(GoogleApiClientLocationDataStore::class.java.simpleName, "On Connection Suspended")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.e(GoogleApiClientLocationDataStore::class.java.simpleName, "On Connection Failed")
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            Log.e(
                GoogleApiClientLocationDataStore::class.java.simpleName,
                "onLocationChanged $location"
            )
            lastLocation = location
            locationMutableLiveData.postValue(lastLocation)
        }
        googleApiClient?.let {
            if (googleApiClient.isConnected) {
                LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient,
                    this@GoogleApiClientLocationDataStore
                )
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onResult(@NonNull locationSettingsResult: LocationSettingsResult) {
        val status = locationSettingsResult.status
        val locationSettingsStates = locationSettingsResult.locationSettingsStates
        when (status.statusCode) {
            LocationSettingsStatusCodes.SUCCESS -> {
                Log.e(
                    GoogleApiClientLocationDataStore::class.java.simpleName,
                    "Location Code Success"
                )
                LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient,
                    locationRequest,
                    this@GoogleApiClientLocationDataStore
                )
            }
            LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                Log.e(
                    GoogleApiClientLocationDataStore::class.java.simpleName,
                    "Location Code RESOLUTION_REQUIRED"
                )
                try {
                    if (context is Activity) {
                        status.startResolutionForResult(context, REQUEST_GPS_LOCATION)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                Log.e(
                    GoogleApiClientLocationDataStore::class.java.simpleName,
                    "Location Code SETTINGS_CHANGE_UNAVAILABLE"
                )
                // Location settings are not satisfied. However, we have
                // no way to fix the
                // settings so we won't show the dialog.
            }

        }
    }
}