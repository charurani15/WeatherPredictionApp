package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class CurrentWeatherDataModel(@Json(name = "current") val current: CurrentWeatherModel)