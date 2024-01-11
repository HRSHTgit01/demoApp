package com.example.network

import android.content.Context

class AppConfig {

    open lateinit var applicationId:String
    open lateinit var versionName:String
    open lateinit var flavor:String
    open var sdkInt:Int = 0
    open lateinit var brand:String
    open lateinit var userAgent:String
    open lateinit var userAgentForInAppWebPage:String
    open var debug:Boolean = false
    open lateinit var gtmContainerId:String
    open lateinit var buildEnvironment:String // dev, qa, prod
    open lateinit var currentSourceEventId:String
    open lateinit var baseEventId: String
    open lateinit var baseEventTs: String
    open lateinit var eventId: String
    open lateinit var applicationContext: Context
    open lateinit var cookieDomainName:String
    open lateinit var reCaptchaSiteKey : String
    open lateinit var tokenVerify : String
    open var isTestMode:Boolean = false
    open var isDBCCollection:Boolean = false

}