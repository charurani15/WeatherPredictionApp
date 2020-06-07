package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class CoordinatesModel(
    @Json(name = "lat") val latitude: Float,
    @Json(name = "lon") val longitude: Float
)