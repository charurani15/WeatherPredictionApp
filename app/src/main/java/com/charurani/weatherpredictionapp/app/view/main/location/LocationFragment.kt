package com.charurani.weatherpredictionapp.app.view.main.location

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.charurani.weatherpredictionapp.R
import com.charurani.weatherpredictionapp.app.Constants
import com.charurani.weatherpredictionapp.app.WeatherPredictionApplication
import com.charurani.weatherpredictionapp.app.data.model.CheckPermissionInputModel
import com.charurani.weatherpredictionapp.app.view.main.di.MainActivityModule
import com.charurani.weatherpredictionapp.databinding.FragmentLocationBinding
import javax.inject.Inject

class LocationFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var locationViewModel: LocationViewModel
    private lateinit var binding: FragmentLocationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectDependencies()
        locationViewModel =
            ViewModelProvider(this, viewModelFactory).get(LocationViewModel::class.java)
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        addObserverForPermission()
        addObserverForLocation()
        checkPermission()
        return binding.root
    }

    private fun addObserverForLocation() {
        locationViewModel.location?.observe(viewLifecycleOwner, Observer { location ->
            Log.e("charu", "location fragment")
            location?.let {
                Log.e("charu", "location navigaition")
                findNavController().navigate(
                    LocationFragmentDirections.actionLocationFragmentToListingFragment(
                        location.latitude.toFloat(),
                        location.longitude.toFloat()
                    )
                )
            }
        })
    }

    private fun addObserverForPermission() {
        locationViewModel.permissionGranted.observe(
            viewLifecycleOwner,
            Observer { checkPermissionInputModel ->
                if (checkPermissionInputModel.permissionGrantedAlready) {
                    locationViewModel.getLocation(viewLifecycleOwner)
                } else {
                    if (!checkPermissionInputModel.shouldShowRequestPermissionRationale) {
                        ActivityCompat.requestPermissions(
                            activity as Activity,
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            Constants.REQUEST_LOCATION_PERMISSION
                        )
                    } else {
                        buildPermissionRationaleDialog(DialogInterface.OnClickListener { dialogInterface, i ->
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(
                                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                    Constants.REQUEST_LOCATION_PERMISSION
                                )
                            }
                        })?.show()
                    }
                }
            })
    }

    private fun checkPermission() {
        locationViewModel.checkPermission(
            CheckPermissionInputModel(
                Constants.REQUEST_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Constants.REQUEST_LOCATION_PERMISSION -> checkForPermissionGrant(grantResults)
            else -> {
            }
        }
    }


    private fun checkForPermissionGrant(grantResults: IntArray) {
        if (grantResults.size == 1) {
            when (grantResults[0]) {
                PackageManager.PERMISSION_GRANTED ->
                    locationViewModel.getLocation(viewLifecycleOwner)
                PackageManager.PERMISSION_DENIED ->
                    Log.e("charu", "here")
            }
        }
    }

    private fun injectDependencies() {
        WeatherPredictionApplication.applicationComponent.plus(MainActivityModule(activity as Activity))
            .inject(this)
    }

    private fun buildPermissionRationaleDialog(listener: DialogInterface.OnClickListener): AlertDialog.Builder? {
        return AlertDialog.Builder(activity as Activity)
            .setTitle(R.string.requesting_location_permission)
            .setMessage(R.string.requesting_location_access_rationale)
            .setPositiveButton(R.string.ok, listener)
            .setCancelable(true)
    }
}