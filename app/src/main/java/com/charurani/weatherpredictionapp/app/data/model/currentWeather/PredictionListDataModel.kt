package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class PredictionListDataModel(
    @Json(name = "main") val predictionMainModel: PredictionMainModel,
    @Json(name = "weather") val weatherModelList: List<WeatherModel>,
    @Json(name = "clouds") val cloudModel: CloudModel,
    @Json(name = "dt_txt") val dateTime: String
)