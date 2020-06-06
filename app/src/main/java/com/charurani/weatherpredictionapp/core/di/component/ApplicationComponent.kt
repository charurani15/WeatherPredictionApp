package com.charurani.weatherpredictionapp.core.di.component

import com.charurani.weatherpredictionapp.app.view.main.di.MainActivityComponent
import com.charurani.weatherpredictionapp.app.view.main.di.MainActivityModule
import com.charurani.weatherpredictionapp.core.di.module.ApplicationModule
import com.charurani.weatherpredictionapp.core.di.module.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun plus(mainActivityModule: MainActivityModule): MainActivityComponent
}