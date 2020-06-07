package com.charurani.weatherpredictionapp.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "weather_prediction_table", primaryKeys = ["date_recorded", "city_name"])
data class WeatherPredictionDataEntity(
    //@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "date_recorded")
    val dateRecorded: String,
    @ColumnInfo(name = "city_name")
    val cityName: String,
    @ColumnInfo(name = "lat_long") val latLong:String,
    @ColumnInfo(name = "temp") val currentTemp: Float,
    @ColumnInfo(name = "temp_min") val minimumTemp: Float,
    @ColumnInfo(name = "temp_max") val maximumTemp: Float,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "cloudiness") val cloudiness: Int
)