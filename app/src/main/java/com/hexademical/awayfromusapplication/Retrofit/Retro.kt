package com.hexademical.awayfromusapplication.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Retro {

    //@ API URL
    private val BASE_URL = "https://api.awayfromus.dev/"

    fun getRetroClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}