package com.example.demoapp
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.network.AppConfig
import com.example.network.CaastleConfig
import com.example.network.GBNetworkRequest
import com.example.network.StatusCodes
import io.ktor.client.HttpClient
import io.ktor.client.features.*
import io.ktor.client.request.get
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var responseTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        CaastleConfig.appConfig = AppConfig()
        CaastleConfig.appConfig.applicationContext=applicationContext

        val requestButton: Button = findViewById(R.id.getRequestButton)
        responseTextView = findViewById(R.id.responseTextView)

        requestButton.setOnClickListener {
            // When the button is clicked, trigger the Ktor request
            GlobalScope.launch(Dispatchers.Main) {
                val baseUrl = "https://app-olana-qa.caastle.com:5443/v1/ns/7000/search/getCollectionSearchData.json?query=%7B%22checkIfStyleInCloset%22%3Atrue%2C%22paginate%22%3A%7B%22limit%22%3A30%2C%22startIndex%22%3A1%7D%2C%22query%22%3A%7B%22collectionHandle%22%3A%22atqa-collection%22%2C%22includeBrands%22%3A%5B%5D%2C%22includeColors%22%3A%5B%5D%2C%22includeRatings%22%3A%5B%5D%2C%22includeSizes%22%3A%5B%5D%2C%22userUuid%22%3A%22f1764da2-0833-30d0-8557-cd20fe20cf36%22%7D%7D"
                val request = GBNetworkRequest(baseUrl)
                request.type = GBNetworkRequest.GBRequestType.GET
                request.baseUrl= baseUrl
                val res = async(Dispatchers.IO) {
                    com.example.network.fetchResponse(request)
                }
                try {
                    val response = res.await()
                    // Update the TextView with the response
                    if(response.statusCodes==StatusCodes.NOT_STARTED)
                    {
                        responseTextView.text = "Not connected to network"
                        // Display the response in a Toast message
                        showToast("Not connected to network")
                    }
                    else if(response.statusCodes==StatusCodes.ERROR)
                    {
                        responseTextView.text = "Some Error has been occurred"
                        // Display the response in a Toast message
                        showToast("Some Error has been occurred")
                    }
                    else{
                        responseTextView.text = response.response
                        // Display the response in a Toast message
                        showToast("Response: ${response.response}")
                    }
                } catch (e: Exception) {
                    // Handle exceptions if necessary
                    showToast("Error: ${e.message}")
                }
            }
        }
        val rb: Button= findViewById(R.id.postRequestButton)
        rb.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {
                val requestUrl = "https://api-qa.gwynniebee.com:4443/v1/event-manager/users/d011ced2-1110-3f36-8785-1d4912db728e/publish.json"
                val request = GBNetworkRequest(requestUrl)
                request.contentType="application/json"
                request.type=GBNetworkRequest.GBRequestType.POST
                request.requestThreadPriority = GBNetworkRequest.RequestThreadPriority.HIGH
                request.setSourceEventId("383b37b1-699e-4e51-9bd4-9afdc4c2672c")
                request.addParameter("cluster","app.android")
                request.postData="{\"eventId\":\"56b7bab8-a37d-40ad-afae-eeb1d762e491\",\"cluster\":\"app.android\",\"txInfo\":{\"baseEventId\":\"458a031d-6350-437f-a5bd-ef5e2e43dc33\",\"sourceEventId\":\"3e45bb43-7fea-4a9c-9d44-7b9b3d8b3239\",\"baseEventTs\":\"2023-11-07 22:54:10\"},\"eventInfo\":{\"customerUUID\":\"c5daef6d-a5c5-3bd1-8bc0-1db183ef27d2\",\"context\":\"Product\",\"skuId\":\"7002270-BLU-XL\"},\"developersFields\":{},\"receivedTs\":\"2023-11-07T22:54:42Z\",\"nameSpaceId\":7000,\"eventFamily\":\"closet\",\"eventType\":\"closet.add\",\"versionInfo\":{\"pubSchemaVersion\":\"1.0.0\"},\"eventTs\":\"2023-11-07T22:54:42Z\"}"
                val res = async(Dispatchers.IO) {
                    com.example.network.fetchResponse(request)
                }
                try {
                    val response = res.await()
                    // Update the TextView with the response
                    if(response.statusCodes==StatusCodes.NOT_STARTED)
                    {
                        responseTextView.text = "Not connected to network"
                        // Display the response in a Toast message
                        showToast("Not connected to network")
                    }
                    else if(response.statusCodes==StatusCodes.ERROR)
                    {
                        responseTextView.text = "Some Error has been occurred"
                        // Display the response in a Toast message
                        showToast("Some Error has been occurred")
                    }
                    else{
                        responseTextView.text = response.response
                        // Display the response in a Toast message
                        showToast("Response: ${response.response}")
                    }
                } catch (e: Exception) {
                    // Handle exceptions if necessary
                    showToast("Error: ${e.message}")
                }
            }
        }
        val PRB:Button= findViewById(R.id.putRequestButton)
        PRB.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {

                val requestUrl = "https://app-olana-qa.caastle.com:5443/v2/ns/7000/users/d011ced2-1110-3f36-8785-1d4912db728e/subscription/status.json"
                val request = GBNetworkRequest(requestUrl)
                request.type=GBNetworkRequest.GBRequestType.PUT
                request.contentType = "application/json"
                request.requestThreadPriority = GBNetworkRequest.RequestThreadPriority.MEDIUM
                request.postData =  "{\n" +
            "\t\"isPaused\": false\n" +
            "}"
                val res = async(Dispatchers.IO) {
                    com.example.network.fetchResponse(request)
                }
                try {
                    val response = res.await()
                    // Update the TextView with the response
                    if(response.statusCodes==StatusCodes.NOT_STARTED)
                    {
                        responseTextView.text = "Not connected to network"
                        // Display the response in a Toast message
                        showToast("Not connected to network")
                    }
                    else if(response.statusCodes==StatusCodes.ERROR)
                    {
                        responseTextView.text = "Some Error has been occurred"
                        // Display the response in a Toast message
                        showToast("Some Error has been occurred")
                    }
                    else{
                        responseTextView.text = response.response
                        // Display the response in a Toast message
                        showToast("Response: ${response.response}")
                    }
                } catch (e: ClientRequestException) {
                    // Handle exceptions if necessary
                  //  responseTextView.text = "An error occurred while processing the request."
                  //  showToast("Error: ${e.message}")
                    try {
                        if (e.response.status.value == HttpStatusCode.Forbidden.value) {
                            // Handle 403 Forbidden (Unauthorized) error
                            responseTextView.text = "Unauthorized request not allowed."
                            showToast("Unauthorized request not allowed.")
                        } else {
                            // Handle other ClientRequestException cases
                            responseTextView.text = "Client request error: ${e.response.readText()}"
                            showToast("Client request error: ${e.response.readText()}")
                        }
                    } catch (innerException: Exception) {
                        // Handle any exceptions that occur during the handling of ClientRequestException
                        responseTextView.text = "An unexpected error occurred during exception handling"
                        showToast("An unexpected error occurred during exception handling")
                        innerException.printStackTrace()
                    }
                    e.printStackTrace() // Print stack trace for debugging

                }
                catch (e: Exception){
                    responseTextView.text = "Error: ${e.message}"
                    showToast("Error: ${e.message}")
                    e.printStackTrace()
                }
                catch (e: Throwable) {
                    // Catch Throwable to ensure that no exception is unhandled
                    responseTextView.text = "An unexpected error occurred"
                    showToast("An unexpected error occurred")
                    e.printStackTrace()
                }

            }
        }
        val DRB: Button= findViewById(R.id.deleteRequestButton)
        DRB.setOnClickListener {
            GlobalScope.launch(Dispatchers.Main) {

                val requestUrl = "https://app-olana-qa.caastle.com:5443/v2/ns/7000/user/d011ced2-1110-3f36-8785-1d4912db728e/closet/skus.json"
                val request = GBNetworkRequest(requestUrl)
                request.type=GBNetworkRequest.GBRequestType.POST
                request.contentType = "application/json"
                request.requestThreadPriority = GBNetworkRequest.RequestThreadPriority.MEDIUM
                request.postData =  "{\"operationId\":301,\"skuList\":[{\"sku\":\"7002270-BLU-M\"}]}"
                request.setSourceEventId("65589095-4955-4c3f-aa72-971e262443bc")
                request.addParameter("cluster","app.android")
                val res = async(Dispatchers.IO) {
                    com.example.network.fetchResponse(request)
                }
                try {
                    val response = res.await()
                    // Update the TextView with the response
                    if(response.statusCodes==StatusCodes.NOT_STARTED)
                    {
                        responseTextView.text = "Not connected to network"
                        // Display the response in a Toast message
                        showToast("Not connected to network")
                    }
                    else if(response.statusCodes==StatusCodes.ERROR)
                    {
                        responseTextView.text = "Some Error has been occurred"
                        // Display the response in a Toast message
                        showToast("Some Error has been occurred")
                    }
                    else
                    {
                        responseTextView.text = response.response
                        // Display the response in a Toast message
                        showToast("Response: ${response.response}")
                    }
                } catch (e: Exception) {
                    // Handle exceptions if necessary
                    showToast("Error: ${e.message}")
                }
            }
        }
    }



//    private suspend fun fetchResponse(url: String): String {
//        val client = HttpClient(Android)
//        return client.get(url)
//    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
