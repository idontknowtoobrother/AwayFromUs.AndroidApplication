package com.hexademical.awayfromusapplication.API

import android.content.res.Resources
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserResponse {

    class Resource {
        @SerializedName("name")
        @Expose
        private val name: String? = null

        @SerializedName("dayLeft")
        @Expose
        private val dayLeft: Int? = null

        @SerializedName("status")
        private val status: String? = null

        fun getName(): String? {
            return name
        }

        fun getDayLeft(): Int? {
            return dayLeft
        }

        fun getStatus(): String? {
            return status
        }

        fun isPermanently(): Boolean {
            return dayLeft != null && dayLeft == -1
        }

        fun isSyncedIPAddress(): Boolean {
            return status != null || status != ""
        }
    }

    @SerializedName("username")
    @Expose
    private val username: String? = null

    @SerializedName("firstname")
    @Expose
    private val firstname: String? = null;

    @SerializedName("lastname")
    @Expose
    private val lastname: String? = null

    @SerializedName("license")
    @Expose
    private val license: String? = null

    @SerializedName("resources")
    @Expose
    private val resources: ArrayList<Resource>? = null

    @SerializedName("token")
    @Expose
    private val token: String? = null

    fun getToken(): String? {
        return token
    }

    fun getFullname(): String {
        return "$firstname $lastname"
    }

    fun getUsername(): String? {
        return username
    }

    fun getLicense(): String? {
        return license
    }

    fun getResoures(): ArrayList<Resource>? {
        return resources
    }

}