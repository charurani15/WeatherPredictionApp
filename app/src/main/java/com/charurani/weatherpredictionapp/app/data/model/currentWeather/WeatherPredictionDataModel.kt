package com.charurani.weatherpredictionapp.app.data.model.currentWeather

import com.squareup.moshi.Json

data class WeatherPredictionDataModel (
    @Json(name = "city") val cityDataModel: CityDataModel,
    @Json(name = "list") val predictionListDataModelList: List<PredictionListDataModel>
)