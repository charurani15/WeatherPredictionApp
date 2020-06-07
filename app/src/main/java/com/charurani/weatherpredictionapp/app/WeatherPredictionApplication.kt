package com.charurani.weatherpredictionapp.app

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.charurani.weatherpredictionapp.app.Constants.Companion.WORK_NAME
import com.charurani.weatherpredictionapp.app.work.RefreshWeatherDataWorker
import com.charurani.weatherpredictionapp.core.di.component.ApplicationComponent
import com.charurani.weatherpredictionapp.core.di.component.DaggerApplicationComponent
import com.charurani.weatherpredictionapp.core.di.module.ApplicationModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WeatherPredictionApplication : MultiDexApplication() {
    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        buildDaggerGrapgh()
        //delayedInit()
    }

    private fun buildDaggerGrapgh() {
        applicationComponent =
            DaggerApplicationComponent.builder().applicationModule(ApplicationModule(this)).build()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }
    private fun setupRecurringWork() {
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshWeatherDataWorker>(18, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }
}