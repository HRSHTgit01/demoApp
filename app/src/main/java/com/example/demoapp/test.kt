package com.example.demoapp

import android.os.storage.StorageManager

import com.example.demoapp.data.EventInformation
import com.example.demoapp.data.ResponseDataModel
import com.example.network.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import java.net.URLEncoder
import kotlin.coroutines.coroutineContext

fun main() {

    //GET REQUEST
//    val baseUrl = "https://app-olana-qa.caastle.com:5443/v1/ns/7000/search/getCollectionSearchData.json?query=%7B%22checkIfStyleInCloset%22%3Atrue%2C%22paginate%22%3A%7B%22limit%22%3A30%2C%22startIndex%22%3A1%7D%2C%22query%22%3A%7B%22collectionHandle%22%3A%22atqa-collection%22%2C%22includeBrands%22%3A%5B%5D%2C%22includeColors%22%3A%5B%5D%2C%22includeRatings%22%3A%5B%5D%2C%22includeSizes%22%3A%5B%5D%2C%22userUuid%22%3A%22f1764da2-0833-30d0-8557-cd20fe20cf36%22%7D%7D"
//    val request = GBNetworkRequest(baseUrl)
//    request.type = GBNetworkRequest.GBRequestType.GET
//    request.baseUrl= baseUrl

   // POST REQUEST 1
//   val requestUrl = "https://api-dev.gwynniebee.com:4443/v1/event-manager/users/8f211b55-d6d3-3fa1-948f-e34fcf87701d/publish.json"
//
//    val request = GBNetworkRequest(requestUrl)
//    request.contentType="application/json"
//    request.type=GBNetworkRequest.GBRequestType.POST
//    request.requestThreadPriority = GBNetworkRequest.RequestThreadPriority.HIGH
//    request.setSourceEventId("383b37b1-699e-4e51-9bd4-9afdc4c2672c")
//    request.addParameter("cluster","app.android")

//
//    val hashMap: HashMap<String, Any> = HashMap()
//    hashMap["eventId"] = "a1aa699b-65e8-444f-a36b-5e462615439c"
//    hashMap["cluster"] = "app.android"
//    hashMap["developersFields"] = "{}"
//    hashMap["receivedTs"] = "2023-11-08T02:32:51Z"
//    hashMap["nameSpaceId"] = 7000
//    hashMap["eventFamily"] = "closet"
//    hashMap["eventType"] = "closet.add"
//    hashMap["eventTs"] = "2023-11-08T02:32:51Z"
//
//    val jsonObject = GBObjectMapper.toJson(hashMap)
//    val dataToBeSent = jsonObject.toString()
 //   request.postData="{\"eventId\":\"56b7bab8-a37d-40ad-afae-eeb1d762e491\",\"cluster\":\"app.android\",\"txInfo\":{\"baseEventId\":\"458a031d-6350-437f-a5bd-ef5e2e43dc33\",\"sourceEventId\":\"3e45bb43-7fea-4a9c-9d44-7b9b3d8b3239\",\"baseEventTs\":\"2023-11-07 22:54:10\"},\"eventInfo\":{\"customerUUID\":\"c5daef6d-a5c5-3bd1-8bc0-1db183ef27d2\",\"context\":\"Product\",\"skuId\":\"7002270-BLU-XL\"},\"developersFields\":{},\"receivedTs\":\"2023-11-07T22:54:42Z\",\"nameSpaceId\":7000,\"eventFamily\":\"closet\",\"eventType\":\"closet.add\",\"versionInfo\":{\"pubSchemaVersion\":\"1.0.0\"},\"eventTs\":\"2023-11-07T22:54:42Z\"}"

    //POST REQUEST 2
//    val requestUrl = "https://login-olana-qa.caastle.com:5443/v1/login/user/login/submit?site=olana"
//     val request = GBNetworkRequest(requestUrl)
//    request.disableAuthentication()
//    request.contentType="application/x-www-form-urlencoded; charset=UTF-8"
//    request.type=GBNetworkRequest.GBRequestType.POST
//    request.requestThreadPriority = GBNetworkRequest.RequestThreadPriority.MEDIUM
//    request.addParameter("cluster","app.android")
//    request.postData="output=json%2Chttps&checkout_url=%2F&clientTokenType=RecaptchaEnterprise&cookie_domain=.haverdash.com&clientToken=03AFcWeA6NNF-RlVQdDwS1OfTDEHGfGNwIHp_6oRuh0lCGiqMgD1xp4d5irzOgoFFbn3dBqzNVKejl6yJjjZUYy67QUrYHrwHKNpXtYqegve4armmJ-z55-2p5vg6XhiU_evA9pUa2oeF0I0BNcxGIGkHnadHWmQcT_1WXSnwXLVDEPG1-PXFcdoJYer57g0G55Juqjt1jeODtTAPgc38VH_wu5wHsQxWnoXfmsl48YjgKo2PAQ_C1KLQzz1UoY-ME8D7VOPg3MlCXmqWFyJ2v38DumnVlMPidROeWgYMDI3uiIoMbfmx-NcqvqSarneW1t5sfp-_A3AKz01mML10oMabMY8Qfuv397crMyjqlhLXvQDIo7VAmeROlj4M-YzdxPqK0y_v2uLn1zcP2S_uF-UVOluLjl5_QeuFm9hOdsvcFTMS8-kVj-9I2r7Fc9APDVeGoPw1_Jk1aSnCoMej5HpgBwF6onHRmBCh1yFh6AiBNBb47Tfu9-F71ObrHmlEU2pTTVHoECn0xVBXfvhbPuXWXhyUeizA2EVLQfylVqmraB6I-j7n_QmNQ3W-oB9NYtLu5e9k1iAYdZApK0YinG4coXXwHZV0bGOrsXkAh-KUE4QaOnaTQ2tIdNMUVwA-XVo3p-370qrexqCVW-TLCL1yvme5U4Ug-RuLE6FMT2WJXQ8_UOsQBgkQKkkWDgI2RrfE5A4iog7bRLHwhV5qbofkuvCyBgSirm0chgAkHINm-KDtaZcxFAw1DzqA2zQKBVrgy5rWTjSDPtLwTP8FMn5GOQKklMx3AB3puO-CCe2kFB_XPJbOaKtQlc4nSOdH6Ice0-9s4oPlnSamUrxLaYvl__yAm8P9irczmbLgkwlvt71ZL_a7GO51Gkgf8fXQQCLPw5Gg3FteiEafJfSiGmhm4qrLYepirrwn1zxDiTE-cDm3hSJAeMETmS4EGSZDA-dHwa1WE-Y1shzEDwibha7fa68P3AvorA9UStdci18TbEQU6ZHw0JId913K63bzRcvxjXqcRPwYulBA0ISmZoDqU9b24B-mWe47y6uehwCNlw_HyMLZU2jvpSpTWnnunlVAHx6jNLFrqn4-hOiS5aiVZ-SPrwTWVjQCPdrQlpMuYP2YM2mCCvyCJQQpRFyBp64VzyzAwvyjS-2lyjAYkWsayuNWE0wG_ekLCNwAB9gXaQb0g3NVFS62TMdouIPJVSSuUk27ZWpVZsuM1q5fhnPX46tqaiC8Sig2GUSioVcTkEXV4FWP3N2N85iyovOC7RvFCYv2ISJh4QdTSl1R5hWcGpIZQW44EFtpBauHTWN_DiXeMmIh9XUGIGB4nlvsFcCi9HJg3H6I1PWEyVwB8dG3R0W8u0H7ASpCrOcKIl6UFsXqx3iR0X6j83mPJYYpes4jbwyBFdXFXe01ATcSq9m88ViRqYALxCBn7zBF-MAZLpzKzxSwFe4g&action=login&customer%5Bemail%5D=test%2Bprecondition%2Bstripe%2Bsubscriber%2B1698228151162%40gwynniebee.com&device_type=android&remember_me=true&customer%5Bpassword%5D=q123456%21"


// PUT REQUEST
//        val requestUrl = "https://app-olana-qa.caastle.com:5443/v2/ns/7000/users/729c7058-7a09-3a1d-9bef-8875e80a027d/subscription/status.json"
//     val request = GBNetworkRequest(requestUrl)
//    request.type=GBNetworkRequest.GBRequestType.PUT
//    request.contentType = "application/json"
//    request.requestThreadPriority = GBNetworkRequest.RequestThreadPriority.MEDIUM
//    request.postData =  "{\n" +
//            "\t\"isPaused\": false\n" +
//            "}"


    //DELETE REQUEST

            val requestUrl = "https://app-olana-qa.caastle.com:5443/v2/ns/7000/user/729c7058-7a09-3a1d-9bef-8875e80a027d/closet/skus.json"
     val request = GBNetworkRequest(requestUrl)
    request.type=GBNetworkRequest.GBRequestType.POST
    request.contentType = "application/json"
    request.requestThreadPriority = GBNetworkRequest.RequestThreadPriority.MEDIUM
    request.postData =  "{\"operationId\":301,\"skuList\":[{\"sku\":\"7002270-BLU-M\"}]}"
        request.setSourceEventId("65589095-4955-4c3f-aa72-971e262443bc")
    request.addParameter("cluster","app.android")




    runBlocking {
        try {


            val response = fetchResponse(request)
            println("API Response: ${response}")
            // Assuming jsonResponse contains your JSON response string
//            val menuList = GBObjectMapper.getObjectFromJson(
//                moreMenuList,
//                MoreOptionMenuMapper::class.java
//            ) as MoreOptionMenuMapper
            val gson = Gson()
            val productResponse = gson.fromJson(response.response, ResponseDataModel::class.java)
         //   val products: List<Product> = productResponse.products
            val eventInformation : com.example.demoapp.data.EventInformation = productResponse.eventInformation
            println("Event INfo: ${eventInformation.baseEventId}")
        }
        catch (e: Exception) {
            println("Error: ${e.message}")
        }
    }
}





