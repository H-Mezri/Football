package com.mezri.football.data.network

import com.mezri.football.API_BASE_PATH
import retrofit2.Retrofit

object RetrofitClient {

    private val retrofitClient by lazy {
        Retrofit.Builder()
            .baseUrl(API_BASE_PATH)
            .addConverterFactory(HttpClient.getConverterFactory())
            .client(HttpClient.getHttpClient())
            .build()
    }

    /**
     * Return retrofit network client
     */
    fun getNetworkClient(): Retrofit = retrofitClient
}