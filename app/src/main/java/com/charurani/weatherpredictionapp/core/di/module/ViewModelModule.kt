package com.charurani.weatherpredictionapp.core.di.module

import androidx.lifecycle.ViewModelProvider
import com.charurani.weatherpredictionapp.core.di.viewmodel.ViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory{
        return factory
    }
}