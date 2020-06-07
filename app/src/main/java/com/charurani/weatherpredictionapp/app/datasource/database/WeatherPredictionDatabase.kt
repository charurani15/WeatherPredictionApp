package com.charurani.weatherpredictionapp.app.datasource.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.charurani.weatherpredictionapp.app.data.entity.WeatherPredictionDataEntity

@Database(entities = [WeatherPredictionDataEntity::class], version = 1, exportSchema = false)
abstract class WeatherPredictionDatabase : RoomDatabase() {
    abstract val dao: WeatherPredictionDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherPredictionDatabase? = null

        public fun getDatabase(context: Context): WeatherPredictionDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        WeatherPredictionDatabase::class.java,
                        "weather_prediction_db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}