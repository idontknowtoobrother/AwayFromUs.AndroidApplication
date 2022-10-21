package com.hexademical.awayfromusapplication.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRespone {

    @SerializedName("username")
    @Expose
    var username: String? = null

    @SerializedName("token")
    @Expose
    var token: String? = null

}