package com.example.hilt_coroutine_payment.data.remote

import com.example.hilt_coroutine_payment.data.model.SignInResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SignInRemoteDataSource {
    @GET("/v2/user/me")
    suspend fun getArenaInfo(
        @Query("query") query: String,
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("sort") sort: String,
    ): SignInResponse
}