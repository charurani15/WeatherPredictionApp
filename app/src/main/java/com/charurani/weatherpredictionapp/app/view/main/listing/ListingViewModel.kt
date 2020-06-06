package com.charurani.weatherpredictionapp.app.view.main.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.charurani.weatherpredictionapp.app.data.model.currentWeather.WeatherPredictionDataModel
import com.charurani.weatherpredictionapp.app.repository.GetWeatherDataRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class ListingViewModel @Inject constructor(val getWeatherDataRepository: GetWeatherDataRepository) :
    ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var _weatherPredictionDataModel = MutableLiveData<WeatherPredictionDataModel>()
    val weatherPredictionDataModel: LiveData<WeatherPredictionDataModel>
        get() = _weatherPredictionDataModel

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getWeatherPredictionList(
        latitude: Float,
        longitude: Float
    ) {
        uiScope.launch {
            val currentWeatherDataModel = getWeatherDataFromRepository(latitude, longitude)
            _weatherPredictionDataModel.value = currentWeatherDataModel
        }
    }

    private suspend fun getWeatherDataFromRepository(
        latitude: Float,
        longitude: Float
    ): WeatherPredictionDataModel {
        return withContext(Dispatchers.IO) {
            getWeatherDataRepository.getWeatherPrediction(latitude, longitude).await()
        }
    }

}