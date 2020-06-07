package com.charurani.weatherpredictionapp.app.work

import android.content.Context
import android.content.SharedPreferences
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.charurani.weatherpredictionapp.app.Constants
import com.charurani.weatherpredictionapp.app.repository.GetWeatherDataRepository

class RefreshWeatherDataWorker(
    private val context: Context,
    private val params: WorkerParameters
) : CoroutineWorker(context, params) {
    //private val repository: GetWeatherDataRepository = GetWeatherDataRepository()
    //private val sharedPreferences: SharedPreferences
    override suspend fun doWork(): Result {
        try {
//            val latitude = sharedPreferences.getFloat(Constants.SHARED_PREFERENCE_KEY_LATITUDE, 0f)
//            val longitude =
//                sharedPreferences.getFloat(Constants.SHARED_PREFERENCE_KEY_LONGITUDE, 0f)
//            if (latitude != 0f && longitude != 0f)
//                repository.refreshWeatherPredictions(
//                    latitude, longitude
//                )
        } catch (exception: Exception) {
            return Result.retry()
        }
        return Result.success()
    }
}