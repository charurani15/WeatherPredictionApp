package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class CityDataModel(
    @Json(name="name")
    val name:String
)