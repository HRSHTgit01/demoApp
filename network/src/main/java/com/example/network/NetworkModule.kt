package com.example.network;
import com.example.network.CaastleConfig.Companion.appConfig
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject


val networkClient = NetworkClient()
val client = networkClient.client

suspend fun authenticate(request: GBNetworkRequest): Boolean {
        val networkRequest = GBNetworkRequest("https://login-olana-qa.caastle.com:5443/v1/login/user/login/refresh/access-token?site=olana")
        val postData = "output=json&refresh_token=" +  "uuid/949e4e89-6658-3ada-bd8c-b5ad13a54e66/7000:api-qa-v1:1698392207:jx%2Fe25JdvvJPIz2e9MxMAMa4bv4%3D" +
            "&cookie_domain=" + ".haverdash.com"
        networkRequest.postData = postData
        networkRequest.addParameter("uuid", "949e4e89-6658-3ada-bd8c-b5ad13a54e66")
        networkRequest.type = GBNetworkRequest.GBRequestType.POST
       // networkRequest.setContentType(platform.caastle.com.caastleinfa.network.GBHttpClient.CONTENT_TYPE_APPLICATION)
        networkRequest.disableAuthentication()
        networkRequest.numberOfRetryAttempts = 3

        try{
           val response = fetchResponse(networkRequest)
            val authToken: AuthToken = GBObjectMapper.getObjectFromJson(
                response.response,
                AuthToken::class.java
            ) as AuthToken
            if (authToken != null && authToken.getStatus().getStatusCode() === Status.OK) {
                CaastleConfig.userConfig.setApiAccessToken(
                    "uuid/949e4e89-6658-3ada-bd8c-b5ad13a54e66/7000:api-qa-v1:1698392207:jx%2Fe25JdvvJPIz2e9MxMAMa4bv4%3D",
                    "2023-10-31T17:50:39.000Z"
                )
            }
        } catch (ex: java.lang.Exception) {
            //     handleHttpResponseStatus(HttpURLConnection.HTTP_FORBIDDEN)
        }

    return !(CaastleConfig.userConfig.isApiAccessTokenExpired() || CaastleConfig.userConfig.isLoginCookieExpired())
}

suspend fun fetchResponse(request: GBNetworkRequest): ResponseObject {
    return try {
        val url = request.getEncodedUrlWithQueryParameters()

   //  checking connectivity
        val appConfig:AppConfig = CaastleConfig.appConfig

        val connectivityInfo = ConnectivityInfo(appConfig.applicationContext)

        if (connectivityInfo.isNetworkAvailable()) {
            println("connected to network")
        } else {
           println("network not available")
            return ResponseObject(null,StatusCodes.NOT_STARTED)
        }

       // authentication
//       val uuid = "949e4e89-6658-3ada-bd8c-b5ad13a54e66"
//        if (request.isAuthenticationEnabled && (uuid == null || "0".equals(
//                uuid,
//                ignoreCase = true
//            ))
//        ) {
//            throw IOException("UUID is null or 0")
//        }
//        if (request.isAuthenticationEnabled && !authenticate(request)) {
//            throw IOException("Authentication Failed")
//        }

        val response: String = when (request.getType()) {
            GBNetworkRequest.GBRequestType.GET -> {
                client.get(url) {
                    setupRequest(this, request)
                }
            }
            GBNetworkRequest.GBRequestType.POST -> {
                client.post(url) {
                    setupRequest(this, request)
                }
            }
            GBNetworkRequest.GBRequestType.PUT -> {
                client.put(url) {
                    setupRequest(this, request)
                }
            }
            GBNetworkRequest.GBRequestType.DELETE -> {
                client.request<String> {
                    method = HttpMethod.Delete
                    body = FormDataContent(Parameters.build {
                        append("postData", request.getPostData())
                    })
                    setupRequest(this, request)
                }
            }
            else -> throw IllegalArgumentException("Unsupported request type")
        }


        ResponseObject(response, StatusCodes.SUCCESS)
    }

    catch (e: Exception) {
        // Handle exceptions
       // Log.d("Exception occurred during network request", e)
        throw e
        ResponseObject(null,StatusCodes.ERROR)
    }

}

private fun HttpRequestBuilder.setupRequest(builder: HttpRequestBuilder, request: GBNetworkRequest) {

    if ((request.type == GBNetworkRequest.GBRequestType.DELETE ||
                request.type == GBNetworkRequest.GBRequestType.POST ||
                request.type == GBNetworkRequest.GBRequestType.PUT) && request.postData != null
    ) {
        val authHeaderKey = "api-qa-v1_auth_token"
        val apiAccessToken =
            "uuid/d011ced2-1110-3f36-8785-1d4912db728e/7000:api-qa-v1:1700679047:RwiWjIEbdZGk9J7qfd4w2aSzBRM%3D"
        val cookieName = "p_login_qa"
        val cookieValue = "1116761213510951715"
        builder.header("Accept",request.contentType)
        builder.header("Content-type", request.contentType)

        builder.header("cookie", "$authHeaderKey=$apiAccessToken; $cookieName=$cookieValue")
        builder.header(authHeaderKey, apiAccessToken)
        builder.header("User-Agent","Haverdash-Closet-Android/master-1.0.0-SNAPSHOT 20230829/Platform-1.0.0-SNAPSHOT DeviceInfo/28-google")

        val jsonObject: JsonObject = Json.decodeFromString(request.postData)
        this.body=jsonObject

    }
}



