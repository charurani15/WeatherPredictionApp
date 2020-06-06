package com.charurani.weatherpredictionapp.app.view.main.di

import android.app.Activity
import android.content.Context
import com.charurani.weatherpredictionapp.app.datasource.GetLocationDataStore
import com.charurani.weatherpredictionapp.app.datasource.GoogleApiClientLocationDataStore
import com.charurani.weatherpredictionapp.app.datasource.HardwarePermissionDataStore
import com.charurani.weatherpredictionapp.app.datasource.PermissionDataStore
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule(val activity: Activity) {

    @Provides
    fun provideContext(): Context {
        return activity
    }

    @Provides
    fun provideGetLocationDataStore(googleApiClientLocationDataStore: GoogleApiClientLocationDataStore): GetLocationDataStore {
        return googleApiClientLocationDataStore
    }

    @Provides
    fun providePermissionDataStore(permissionDataStore: HardwarePermissionDataStore): PermissionDataStore {
        return permissionDataStore
    }
}