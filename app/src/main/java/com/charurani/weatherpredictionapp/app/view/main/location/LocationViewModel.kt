package com.charurani.weatherpredictionapp.app.view.main.location

import android.content.SharedPreferences
import android.location.Location
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.charurani.weatherpredictionapp.app.Constants.Companion.SHARED_PREFERENCE_KEY_LATITUDE
import com.charurani.weatherpredictionapp.app.Constants.Companion.SHARED_PREFERENCE_KEY_LONGITUDE
import com.charurani.weatherpredictionapp.app.data.model.CheckPermissionInputModel
import com.charurani.weatherpredictionapp.app.repository.CheckPermissionDataRepository
import com.charurani.weatherpredictionapp.app.repository.GetLocationDataRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class LocationViewModel
@Inject constructor(
    private val getLocationDataRepository: GetLocationDataRepository?,
    private val checkPermissionDataRepository: CheckPermissionDataRepository,
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _location: MutableLiveData<Location?> = MutableLiveData<Location?>()
    val location: LiveData<Location?>
        get() = _location
    private var _permissionGranted = MutableLiveData<CheckPermissionInputModel>()
    val permissionGranted: LiveData<CheckPermissionInputModel>
        get() = _permissionGranted
    val hideProgress: LiveData<Boolean> =
        Transformations.map(location, Function
        {
            location.value != null
        })

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun checkPermission(checkPermissionInputModel: CheckPermissionInputModel) {
        _permissionGranted.value =
            checkPermissionDataRepository.checkForPermission(checkPermissionInputModel)
    }

    fun getLocation(lifecycleOwner: LifecycleOwner) {
        uiScope.launch {
            getLocationFromRepository()?.observe(lifecycleOwner, Observer {
                val editor = sharedPreferences.edit()
                editor.putFloat(SHARED_PREFERENCE_KEY_LATITUDE, it?.latitude?.toFloat() ?: 0f)
                editor.putFloat(SHARED_PREFERENCE_KEY_LONGITUDE, it?.longitude?.toFloat() ?: 0f)
                editor.apply()
                _location.value = it
            })

        }
    }

    private suspend fun getLocationFromRepository(): LiveData<Location?>? {
        return withContext(Dispatchers.IO) {
            getLocationDataRepository?.getCurrentOrLastKnownLocation()
        }
    }
}