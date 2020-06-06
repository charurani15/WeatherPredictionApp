package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class WeatherModel constructor(
    @Json(name = "description") val description: String,
    @Json(name = "icon") val icon: String
) {
}