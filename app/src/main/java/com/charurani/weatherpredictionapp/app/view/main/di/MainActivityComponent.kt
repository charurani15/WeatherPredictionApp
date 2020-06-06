package com.charurani.weatherpredictionapp.app.view.main.di

import com.charurani.weatherpredictionapp.app.view.main.MainActivity
import com.charurani.weatherpredictionapp.app.view.main.listing.ListingFragment
import com.charurani.weatherpredictionapp.app.view.main.location.LocationFragment
import dagger.Subcomponent

@Subcomponent(
    modules = arrayOf(
        MainActivityModule::class,
        ListingViewModelModule::class,
        LocationViewModelModule::class
    )
)
interface MainActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment:LocationFragment)
    fun inject(fragment:ListingFragment)
}