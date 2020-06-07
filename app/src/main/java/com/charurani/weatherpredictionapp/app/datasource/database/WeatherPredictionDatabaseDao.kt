package com.charurani.weatherpredictionapp.app.datasource.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.charurani.weatherpredictionapp.app.data.entity.WeatherPredictionDataEntity

@Dao
interface WeatherPredictionDatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertEntry(weatherPredictionDataEntityList: List<WeatherPredictionDataEntity>)

    @Query("Select * from weather_prediction_table where lat_long = :latLong")
    fun getAllEntriesForLatLon(latLong: String): LiveData<List<WeatherPredictionDataEntity>>
}