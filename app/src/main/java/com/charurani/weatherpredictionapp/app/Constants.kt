package com.charurani.weatherpredictionapp.app

import android.content.SharedPreferences

interface Constants {
    companion object {
        const val APP_ID = "7711f70361b0f741bf8dd9d123d8a3ac"
        const val REQUEST_LOCATION_PERMISSION = 1555
        const val SHARED_PREFERENCE_KEY_LATITUDE = "latitude"
        const val SHARED_PREFERENCE_KEY_LONGITUDE = "longitude"
        const val WORK_NAME = "com.charurani.weatherpredictionapp.app.work.RefreshDataWorker"
    }
}