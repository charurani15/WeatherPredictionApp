package com.charurani.weatherpredictionapp.app.repository

import com.charurani.weatherpredictionapp.app.data.model.currentWeather.CurrentWeatherDataModel
import com.charurani.weatherpredictionapp.app.data.model.currentWeather.WeatherPredictionDataModel
import com.charurani.weatherpredictionapp.app.datasource.GetWeatherNetworkDataStore
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class GetWeatherDataRepository @Inject constructor(
    val retrofit: Retrofit,
    @Named("appid") val appId: String
) {
    fun getCurrentWeatherData(
        latitude: Float,
        longitude: Float,
        exclude: String
    ): Deferred<CurrentWeatherDataModel> {
        return retrofit.create(GetWeatherNetworkDataStore::class.java)
            .getCurrentWeather(latitude, longitude, exclude, appId)
    }

    fun getWeatherPrediction(
        latitude: Float,
        longitude: Float,
        units: String = "metric"
    ): Deferred<WeatherPredictionDataModel> {
        return retrofit.create(GetWeatherNetworkDataStore::class.java)
            .getForeCast(latitude, longitude, units, appId)
    }
}