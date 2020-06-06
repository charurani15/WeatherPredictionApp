package com.charurani.weatherpredictionapp.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.charurani.weatherpredictionapp.core.di.component.ApplicationComponent
import com.charurani.weatherpredictionapp.core.di.component.DaggerApplicationComponent
import com.charurani.weatherpredictionapp.core.di.module.ApplicationModule

class WeatherPredictionApplication : MultiDexApplication() {
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        buildDaggerGrapgh()
    }

    private fun buildDaggerGrapgh() {
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }
}