package com.example.hilt_coroutine_payment.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponentManager::class)
object NetworkModule {
    private const val BASE_URL = "https://kauth.kakao.com"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            .build()

    @Provides
    @Singleton
    fun provideRetrofitClient(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(provideOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

//    @Provides
//    @Singleton
//    fun providersSignInRemoteDataSource(retrofit: Retrofit) : ArenaRemoteDataSource{
//        return retrofit.create(ArenaRemoteDataSource::class.java)
//    }

}