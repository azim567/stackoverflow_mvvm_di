package com.azimsiddiqui.stackoverflow.di

import com.azimsiddiqui.stackoverflow.helper.Constants.BASE_URL
import com.azimsiddiqui.stackoverflow.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    @Singleton
    @Provides
    fun getApi()= getRetrofitInstance().create<ApiService>(ApiService::class.java)
}