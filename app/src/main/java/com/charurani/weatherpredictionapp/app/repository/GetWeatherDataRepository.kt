package com.charurani.weatherpredictionapp.app.repository

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.charurani.weatherpredictionapp.app.data.entity.WeatherPredictionDataEntity
import com.charurani.weatherpredictionapp.app.data.model.currentWeather.WeatherPredictionDataModel
import com.charurani.weatherpredictionapp.app.datasource.GetWeatherNetworkDataStore
import com.charurani.weatherpredictionapp.app.datasource.database.WeatherPredictionDatabase
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class GetWeatherDataRepository
@Inject constructor(
    private val retrofit: Retrofit,
    @Named("appid") private val appId: String,
    private val weatherPredictionDatabase: WeatherPredictionDatabase
) {
    var latitude: Float? = null
    var longitude: Float? = null
    var weatherPredictionDataEntityLiveData: MutableLiveData<List<WeatherPredictionDataEntity>> =
        MutableLiveData()

    private fun getWeatherPrediction(
        latitude: Float,
        longitude: Float,
        units: String = "metric"
    ): Deferred<WeatherPredictionDataModel> {
        return retrofit.create(GetWeatherNetworkDataStore::class.java)
            .getForeCast(latitude, longitude, units, appId)
    }

    suspend fun refreshWeatherPredictions(
        latitude: Float,
        longitude: Float,
        units: String = "metric"
    ) {
        withContext(Dispatchers.IO) {
            val weatherPredictionDataModel =
                getWeatherPrediction(latitude, longitude, units).await()
            weatherPredictionDatabase.dao.insertEntry(
                getWeatherEntityList(
                    weatherPredictionDataModel
                )
            )
        }
    }

    suspend fun getData(lifecycleOwner: LifecycleOwner) {
        weatherPredictionDatabase.dao.getAllEntriesForLatLon(
            latLong = latitude.toString().plus(longitude)
        ).observe(lifecycleOwner, Observer {
            weatherPredictionDataEntityLiveData.value = it
        })
    }

    private fun getWeatherEntityList(weatherPredictionDataModel: WeatherPredictionDataModel): MutableList<WeatherPredictionDataEntity> {
        val weatherPredictionDataModelList = mutableListOf<WeatherPredictionDataEntity>()
        for (i in weatherPredictionDataModel.predictionListDataModelList.indices) {
            weatherPredictionDataModelList.add(
                convertDataToWeatherEntity(
                    weatherPredictionDataModel,
                    i
                )
            )
        }
        return weatherPredictionDataModelList
    }

    private fun convertDataToWeatherEntity(
        weatherPredictionDataModel: WeatherPredictionDataModel,
        index: Int
    ): WeatherPredictionDataEntity {
        return WeatherPredictionDataEntity(
            weatherPredictionDataModel.predictionListDataModelList[index].dateTime,
            weatherPredictionDataModel.cityDataModel.name,
            latitude.toString()
                .plus(longitude.toString()),
            weatherPredictionDataModel.predictionListDataModelList[index].predictionMainModel.currentTemp,
            weatherPredictionDataModel.predictionListDataModelList[index].predictionMainModel.minimumTemp,
            weatherPredictionDataModel.predictionListDataModelList[index].predictionMainModel.maximumTemp,
            weatherPredictionDataModel.predictionListDataModelList[index].weatherModelList[0].description,
            weatherPredictionDataModel.predictionListDataModelList[index].weatherModelList[0].icon,
            weatherPredictionDataModel.predictionListDataModelList[index].cloudModel.value
        )
    }
}