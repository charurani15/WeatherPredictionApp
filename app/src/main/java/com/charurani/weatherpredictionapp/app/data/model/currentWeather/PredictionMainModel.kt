package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class PredictionMainModel(
    @Json(name = "temp") val currentTemp: Float,
    @Json(name="temp_min") val minimumTemp:Float,
    @Json(name="temp_max") val maximumTemp:Float
)