package com.arya.apigithubusers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.apigithubusers.BuildConfig.API_KEY
import com.arya.apigithubusers.model.UserItems
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject

class DetailViewModel: ViewModel() {
    val detailUser = MutableLiveData<UserItems>()

    fun setDetail(username: String) {
        val userItems = UserItems()

        val url = "https://api.github.com/users/${username}"
        Log.d("@URL", url)

        val client = AsyncHttpClient()
        client.addHeader("Authorization", API_KEY)
        client.addHeader("User-Agent", "request")
        client.get(url, object: AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    userItems.id = responseObject.getInt("id")
                    userItems.login = responseObject.getString("login")
                    userItems.avatarUrl = responseObject.getString("avatar_url")
                    userItems.name = responseObject.getString("name")
                    userItems.repository = responseObject.getString("public_repos") + " Repository"
                    userItems.company = responseObject.getString("company")
                    userItems.location = responseObject.getString("location")
                    detailUser.postValue(userItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("@onFailureDetail", errorMessage)
            }

        })
    }

    fun getDetail(): LiveData<UserItems> {
        return detailUser
    }
}