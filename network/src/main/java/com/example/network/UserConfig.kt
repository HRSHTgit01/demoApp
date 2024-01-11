package com.example.network

import java.text.SimpleDateFormat
import java.util.*

open class UserConfig() {
    open var uuid: String = ""
//        set(value) {
//            dataManager.putString("userConfig_uuid", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_uuid", "0")!!
//            return field
//        }
    open var userFullName: String = ""
//        set(value) {
//            dataManager.putString("userConfig_userFullName", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_userFullName", "")!!
//            return field
//        }
    open var userEmail: String = ""
//        set(value) {
//            dataManager.putString("userConfig_userEmail", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_userEmail", "")!!
//            return field
//        }
    open var closetLoginCookie: String = ""
//        set(value) {
//            dataManager.putString("userConfig_closetLoginCookie", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_closetLoginCookie", "")!!
//            return field
//        }
    open lateinit var apiAuthHeaderKey: String
    open var pluckCookie: String = ""
//        set(value) {
//            dataManager.putString("userConfig_pluckCookie", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_pluckCookie", "")!!
//            return field
//        }
    open var apiAccessToken: String = ""
//        set(value) {
//            dataManager.putString("userConfig_apiAccessToken", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_apiAccessToken", "")!!
//            return field
//        }
    open var apiAccessTokenExpiry: String = ""
//        set(value) {
//            dataManager.putString("userConfig_apiAccessTokenExpiry", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_apiAccessTokenExpiry", "")!!
//            return field
//        }
    open var cookieValue: String = ""
//        set(value) {
//            dataManager.putString("userConfig_cookieValue", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_cookieValue", "")!!
//            return field
//        }

    open var cookieName: String = ""
//        set(value) {
//            dataManager.putString("userConfig_cookieName", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_cookieName", "")!!
//            return field
//        }

    open var cookieExpiry: String = ""
//        set(value) {
//            dataManager.putString("userConfig_cookieExpiry", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_cookieExpiry", "")!!
//            return field
//        }
    open var userState: String = ""
//        set(value) {
//            dataManager.putString("userConfig_userState", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_userState", "")!!
//            return field
//        }
    open var userStatus: String = ""
//        set(value) {
//            dataManager.putString("userConfig_userStatus", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_userStatus", "")!!
//            return field
//        }
    open var userCurrentTenureMonth: String = ""
//        set(value) {
//            dataManager.putString("userConfig_userCurrentTenureMonth", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_userCurrentTenureMonth", "")!!
//            return field
//        }
    open var userTags: String = ""
//        set(value) {
//            dataManager.putString("userConfig_userTags", value)
//            field = value
//        }
//        get() {
//            field = dataManager.getString("userConfig_userTags", "")!!
//            return field
//        }

    open fun isUserLoggedIn(): Boolean {
        return !(uuid.isBlank() || uuid == "0" || isLoginCookieExpired())
    }

    open fun isApiAccessTokenExpired(): Boolean {
        try {
            // cookieExpiryTime = "2016-11-19T12:55:00.000Z";
            val currentTime = Calendar.getInstance()
            currentTime.add(Calendar.HOUR, 1)
            val expires = Calendar.getInstance()
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("en", "US"))
            format.timeZone = TimeZone.getTimeZone("UTC")
            expires.time = format.parse(apiAccessTokenExpiry)
            return expires.before(currentTime)
        } catch (e: Exception) {
            return true
        }

    }

    open fun isLoginCookieExpired(): Boolean {
        try {
            // cookieExpiryTime = "2016-11-19T12:55:00.000Z";
            val tomorrow = Calendar.getInstance()
            tomorrow.add(Calendar.DAY_OF_MONTH, 1) // check for tomorrow.
            val expires = Calendar.getInstance()
            val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale("en", "US"))
            format.timeZone = TimeZone.getTimeZone("UTC")
            expires.time = format.parse(cookieExpiry)
            return expires.before(tomorrow)
        } catch (e: Exception) {
            return true
        }

    }

    open fun setApiAccessToken(apiAccessToken: String, apiAccessTokenExpiry: String) {
        this.apiAccessToken = apiAccessToken
        this.apiAccessTokenExpiry = apiAccessTokenExpiry
    }
}
