package com.example.network

import kotlinx.serialization.Serializable

@Serializable
data class ResponseObject(

    val response: String?,

    val statusCodes: StatusCodes)
