package com.example.githubuserlistv2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientConfig {
    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val api: ApiService = retrofit.create(ApiService::class.java)
}