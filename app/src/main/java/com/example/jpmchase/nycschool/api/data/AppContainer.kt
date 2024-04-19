package com.example.jpmchase.nycschool.api.data

import com.example.jpmchase.nycschool.api.network.ApiService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

interface AppContainer {
    val nycSchoolRepository : NYCSchoolRepository
}


@Module
@InstallIn(ActivityComponent::class)
class DefaultAppContainer : AppContainer {

    private val baseUrl = "https://data.cityofnewyork.us/"
    private val okHttp = OkHttpClient.Builder()
        .readTimeout(100, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .baseUrl(baseUrl)
        .client(okHttp)
        .build()

    /**
     * Retrofit service object for creating api calls
     */
    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    override val nycSchoolRepository: NYCSchoolRepository by lazy {
        NYCSchoolRepositoryImpl(retrofitService)
    }

}