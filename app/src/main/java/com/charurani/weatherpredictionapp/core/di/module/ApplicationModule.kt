package com.charurani.weatherpredictionapp.core.di.module

import android.app.Application
import android.content.Context
import com.charurani.weatherpredictionapp.app.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {

    @Provides
    @Singleton
    public fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    @Named("applicationContext")
    public fun provideApplicationContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideEndpoint(): HttpUrl {
        return HttpUrl.Builder()
            .scheme("https")
            .host("api.openweathermap.org")
            .addPathSegment("data")
            .addPathSegment("2.5")
            .addPathSegment("").build()
    }

    @Provides
    @Singleton
    @Named("networkTimeout")
    fun provideNetworkTimeoutInSeconds(): Long {
        return 30L
    }

    @Provides
    @Singleton
    fun provideMoshiConverterFactory(): Converter.Factory {
        return MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
    }

    @Provides
    @Singleton
    fun provideCallAdapterFactory(): CallAdapter.Factory {
        return CoroutineCallAdapterFactory()
    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }

    @Provides
    @Singleton
    public fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        @Named("networkTimeout") networkTimeout: Long
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(networkTimeout, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    public fun provideRetrofit(
        baseUrl: HttpUrl,
        converter: Converter.Factory,
        callAdapter: CallAdapter.Factory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converter)
            .addCallAdapterFactory(callAdapter)
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Named("appid")
    fun provideAppId():String{
        return Constants.APP_ID
    }
}