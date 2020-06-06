package com.charurani.weatherpredictionapp.app.view.main.di

import androidx.lifecycle.ViewModel
import com.charurani.weatherpredictionapp.app.view.main.listing.ListingViewModel
import com.charurani.weatherpredictionapp.app.view.main.location.LocationViewModel
import com.charurani.weatherpredictionapp.core.di.viewmodel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ListingViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ListingViewModel::class)
    internal abstract fun bindIntoListingViewModel(listingViewModel: ListingViewModel): ViewModel
}