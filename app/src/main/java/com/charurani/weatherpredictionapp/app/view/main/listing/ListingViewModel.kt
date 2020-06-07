package com.charurani.weatherpredictionapp.app.view.main.listing

import androidx.lifecycle.*
import com.charurani.weatherpredictionapp.app.data.entity.WeatherPredictionDataEntity
import com.charurani.weatherpredictionapp.app.repository.GetWeatherDataRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class ListingViewModel
@Inject constructor(
    private val getWeatherDataRepository: GetWeatherDataRepository
) :
    ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _weatherPredictionDataEntityLiveData: MutableLiveData<List<WeatherPredictionDataEntity>> =
        MutableLiveData<List<WeatherPredictionDataEntity>>()
    val weatherPredictionDataEntityLiveData: LiveData<List<WeatherPredictionDataEntity>>
        get() = _weatherPredictionDataEntityLiveData

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun getWeatherPredictionList(
        latitude: Float,
        longitude: Float,
        lifecycleOwner: LifecycleOwner
    ) {
        uiScope.launch {
            try {
                getWeatherDataRepository.latitude = latitude
                getWeatherDataRepository.longitude = longitude
                val currentWeatherDataModel = getWeatherDataFromRepository(latitude, longitude)
                getWeatherDataRepository.weatherPredictionDataEntityLiveData?.observe(lifecycleOwner,
                    Observer {
                        _weatherPredictionDataEntityLiveData.value = it
                    })
                getWeatherDataRepository.getData(lifecycleOwner)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getWeatherDataFromRepository(
        latitude: Float,
        longitude: Float
    ) {
        withContext(Dispatchers.IO) {
            getWeatherDataRepository.refreshWeatherPredictions(latitude, longitude)
        }
    }

}