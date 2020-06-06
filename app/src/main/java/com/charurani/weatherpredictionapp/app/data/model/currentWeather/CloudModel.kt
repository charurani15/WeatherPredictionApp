package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class CloudModel(@Json(name = "all") val value: Int)