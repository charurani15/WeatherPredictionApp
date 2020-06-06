package com.charurani.weatherpredictionapp.app.view.main

import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.charurani.weatherpredictionapp.R
import java.text.SimpleDateFormat
import java.util.*

class Utility {

    companion object {

        const val WEATHER_API_URL = "http://openweathermap.org/img/wn/"

        @BindingAdapter("temperature")
        @JvmStatic
        fun TextView.temperatureValue(temperature: String) {
            text = "$temperature °C"
        }

        @BindingAdapter("cloudinesss")
        @JvmStatic
        fun ImageView.setCloudinessImage(cloudiness: Int) {
            Log.e("charu", "text is $cloudiness")
            when (cloudiness) {
                in 0..30 -> setBackgroundResource(R.drawable.ic_cloud_30)
                in 50..80 -> setBackgroundResource(R.drawable.ic_cloud_50)
                in 80..100 -> setBackgroundResource(R.drawable.ic_cloud_100)
            }
        }

        @BindingAdapter("backgroundColor")
        @JvmStatic
        fun LinearLayout.setBackgroundColor(dateTime: String) {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date = simpleDateFormat.parse(dateTime)
            val currentDateTime = Date(System.currentTimeMillis())
            when (currentDateTime.before(date)) {
                false -> setBackgroundColor(resources.getColor(R.color.color_fa9191))
                true -> setBackgroundColor(resources.getColor(R.color.color_bbf1c8))
            }
        }

        @BindingAdapter("dateReported")
        @JvmStatic
        fun TextView.setDateReported(dateTime: String){
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            val date = simpleDateFormat.parse(dateTime)
            val requiredDateFormat = SimpleDateFormat("dd-MM-YYYY hh:mm:ss")
            text = requiredDateFormat.format(date)
        }

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun ImageView.bindImage( imageCode: String?) {
            imageCode?.let {
                val imgUri = WEATHER_API_URL.plus(imageCode).plus("@2x.png").toUri().buildUpon().scheme("https").build()
                Glide.with(this.context)
                    .load(imgUri)
                    .into(this)
            }
        }
    }

}