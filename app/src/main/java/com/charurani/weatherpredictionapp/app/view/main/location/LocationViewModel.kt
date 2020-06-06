package com.charurani.weatherpredictionapp.app.view.main.location

import android.location.Location
import android.util.Log
import androidx.arch.core.util.Function
import androidx.lifecycle.*
import com.charurani.weatherpredictionapp.app.data.model.CheckPermissionInputModel
import com.charurani.weatherpredictionapp.app.repository.CheckPermissionDataRepository
import com.charurani.weatherpredictionapp.app.repository.GetLocationDataRepository
import kotlinx.coroutines.*
import javax.inject.Inject

class LocationViewModel
@Inject constructor(
    private val getLocationDataRepository: GetLocationDataRepository?,
    private val checkPermissionDataRepository: CheckPermissionDataRepository
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
        { Log.e("charu","dd ".plus(_location.value == null))
            location.value != null })

    init {
        Log.e("charu"," ".plus(_location.value == null))
    }
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun checkPermission(checkPermissionInputModel: CheckPermissionInputModel) {
        Log.e("charu", "viewmodel cc")
        _permissionGranted.value =
            checkPermissionDataRepository.checkForPermission(checkPermissionInputModel)
    }

    fun getLocation(lifecycleOwner: LifecycleOwner) {
        //runBlocking {
        uiScope.launch {
            Log.e("charu", "viewmodel launch")

            getLocationFromRepository()?.observe(lifecycleOwner, Observer {
                _location.value = it
            })

        }
    }

    private suspend fun getLocationFromRepository(): LiveData<Location?>? {
        return withContext(Dispatchers.IO) {
            getLocationDataRepository?.getCurrentOrLastKnownLocation()
        }//.await()
    }
}