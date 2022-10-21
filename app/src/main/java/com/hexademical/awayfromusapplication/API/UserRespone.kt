package com.hexademical.awayfromusapplication.API

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserRespone {
    @SerializedName("data")
    @Expose
    var data: User? = null

    class User {
        @SerializedName("username")
        @Expose
        var username: String? = null

        @SerializedName("firstname")
        @Expose
        var firstname: String? = null

        @SerializedName("token")
        @Expose
        var token: String? = null
    }
}