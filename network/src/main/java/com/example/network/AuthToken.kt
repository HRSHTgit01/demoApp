package com.example.network

import com.google.gson.annotations.SerializedName

class AuthToken : GBResponse() {
    @SerializedName("accessToken")
    val accessToken: String? = null

    @SerializedName("accessTokenExpire")
    val accessTokenExpire: String? = null
}