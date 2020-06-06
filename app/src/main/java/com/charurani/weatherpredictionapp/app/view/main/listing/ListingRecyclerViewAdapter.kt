package com.charurani.weatherpredictionapp.app.view.main.listing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.charurani.weatherpredictionapp.app.data.model.currentWeather.PredictionListDataModel
import com.charurani.weatherpredictionapp.databinding.ViewListingCellViewBinding

class ListingRecyclerViewAdapter(var weatherPredictionDataModelList: List<PredictionListDataModel>) :
    RecyclerView.Adapter<ListingRecyclerViewAdapter.ListingViewHolder>() {

    class ListingViewHolder(val binding: ViewListingCellViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(weatherPredictionDataModel: PredictionListDataModel) {
            binding.temperature =
                weatherPredictionDataModel.predictionMainModel.currentTemp.toString()
            binding.cloudiness = weatherPredictionDataModel.cloudModel.value
            binding.backgroundColor = weatherPredictionDataModel.dateTime
            binding.dateReported = weatherPredictionDataModel.dateTime
            binding.imageCode = weatherPredictionDataModel.weatherModelList[0].icon
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