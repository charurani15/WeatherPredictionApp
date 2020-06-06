package com.charurani.weatherpredictionapp.app.datasource

import com.charurani.weatherpredictionapp.app.data.model.currentWeather.CurrentWeatherDataModel
import com.charurani.weatherpredictionapp.app.data.model.currentWeather.WeatherPredictionDataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface GetWeatherNetworkDataStore {
    @GET("onecall")
    fun getCurrentWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("exclude") exclude: String,
        @Query("appid") appId: String
    ):Deferred<CurrentWeatherDataModel>

    @GET("forecast")
    fun getForeCast(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("units") units: String,
        @Query("appid") appId: String
    ):Deferred<WeatherPredictionDataModel>
}