package com.hexademical.awayfromusapplication.Interface

import com.hexademical.awayfromusapplication.API.UserRequest
import com.hexademical.awayfromusapplication.API.UserRespone
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("login")
    fun login(
        @Body userRequest: UserRequest
    ):Call<UserRespone>
}