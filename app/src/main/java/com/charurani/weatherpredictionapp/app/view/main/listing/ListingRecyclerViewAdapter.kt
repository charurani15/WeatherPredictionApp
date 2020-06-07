package com.charurani.weatherpredictionapp.app.view.main.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charurani.weatherpredictionapp.app.data.entity.WeatherPredictionDataEntity
import com.charurani.weatherpredictionapp.app.data.model.currentWeather.PredictionListDataModel
import com.charurani.weatherpredictionapp.databinding.ViewListingCellViewBinding

class ListingRecyclerViewAdapter(var weatherPredictionDataModelList: List<WeatherPredictionDataEntity>) :
    RecyclerView.Adapter<ListingRecyclerViewAdapter.ListingViewHolder>() {

    class ListingViewHolder(val binding: ViewListingCellViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherPredictionDataModel: WeatherPredictionDataEntity) {
            binding.temperature =
                weatherPredictionDataModel.currentTemp.toString()
            binding.cloudiness = weatherPredictionDataModel.cloudiness
            binding.backgroundColor = weatherPredictionDataModel.dateRecorded
            binding.dateReported = weatherPredictionDataModel.dateRecorded
            binding.imageCode = weatherPredictionDataModel.icon
            binding.descrption = weatherPredictionDataModel.description
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListingViewHolder {
        val binding =
            ViewListingCellViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ListingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return weatherPredictionDataModelList.size
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        holder.bind(weatherPredictionDataModelList[position])
    }
}