package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class CurrentWeatherModel constructor(
    @Json(name = "sunrise")
    val sunriseTime: Long,
    @Json(name = "sunset")
    val sunsetTime: Long,
    @Json(name = "temp")
    val temperatureValue: Float,
    @Json(name = "feels_like")
    val feelsLikeTemperatureValue: Float,
    @Json(name = "clouds")
    val clouds: Int,
    @Json(name = "weather")
    val weatherList: MutableList<WeatherModel>

)