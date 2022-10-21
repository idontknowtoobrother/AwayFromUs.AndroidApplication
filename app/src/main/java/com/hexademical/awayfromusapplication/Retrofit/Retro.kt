package com.hexademical.awayfromusapplication.Retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Retro {

    //@ API URL
    private val URL = "http://localhost:3000/"

    fun getRetroClientInstance(): Retrofit {
        val gson = GsonBuilder().setLenient().create()
        return Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }
}