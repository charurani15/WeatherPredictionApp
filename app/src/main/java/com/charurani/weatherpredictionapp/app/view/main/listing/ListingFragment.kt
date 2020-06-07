package com.charurani.weatherpredictionapp.app.view.main.listing

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.charurani.weatherpredictionapp.app.WeatherPredictionApplication
import com.charurani.weatherpredictionapp.app.data.entity.WeatherPredictionDataEntity
import com.charurani.weatherpredictionapp.app.view.main.di.MainActivityModule
import com.charurani.weatherpredictionapp.databinding.FragmentListingBinding
import javax.inject.Inject

class ListingFragment : Fragment() {

    private lateinit var viewModel: ListingViewModel

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var predictionList: RecyclerView
    private lateinit var predictionListLoadingPb: ProgressBar
    private var listingRecyclerViewAdapter: ListingRecyclerViewAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        injectDependencies()
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListingViewModel::class.java)
        val binding = FragmentListingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
        initViews(binding)
        setUpRecyclerView()
        getWeatherData()
        return binding.root
    }

    private fun injectDependencies() {
        WeatherPredictionApplication.applicationComponent.plus(MainActivityModule(activity as Activity))
            .inject(this)
    }

    private fun initViews(binding: FragmentListingBinding) {
        predictionList = binding.weatherPredictionList
        predictionListLoadingPb = binding.weatherPredictionListLoadingPb
    }

    private fun setUpRecyclerView() {
        val manager = GridLayoutManager(activity, 2)
        predictionList.layoutManager = manager
    }

    private fun getWeatherData() {
        viewModel.weatherPredictionDataEntityLiveData.observe(
            viewLifecycleOwner,
            Observer { weatherPredictionDataEntityList ->
                weatherPredictionDataEntityList?.let {
                    if(weatherPredictionDataEntityList.isNotEmpty()) {
                        setVisibilityToView(predictionListLoadingPb, false)
                        setVisibilityToView(predictionList, true)
                        addDataToPredictionList(weatherPredictionDataEntityList = weatherPredictionDataEntityList)
                    }
                }
            })
        viewModel.getWeatherPredictionList(
            ListingFragmentArgs.fromBundle(arguments!!).latitude,
            ListingFragmentArgs.fromBundle(arguments!!).longitude,
            viewLifecycleOwner
        )
    }

    private fun addDataToPredictionList(weatherPredictionDataEntityList: List<WeatherPredictionDataEntity>) {
        if (listingRecyclerViewAdapter == null) {
            listingRecyclerViewAdapter = ListingRecyclerViewAdapter(weatherPredictionDataEntityList)
            predictionList.adapter = listingRecyclerViewAdapter
        } else {
            listingRecyclerViewAdapter?.weatherPredictionDataModelList =
                weatherPredictionDataEntityList
        }
    }

    private fun setVisibilityToView(view: View, isVisible: Boolean) {
        when (isVisible) {
            true -> view.visibility = View.VISIBLE
            false -> view.visibility = View.GONE
        }
    }
}
