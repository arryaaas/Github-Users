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
import org.json.JSONArray

class UserViewModel: ViewModel() {
    val listUser = MutableLiveData<ArrayList<UserItems>>()

    fun setUser() {
        val url = "https://api.github.com/users"
        Log.d("@URL", url)

        val client = AsyncHttpClient()
        client.addHeader("Authorization", API_KEY)
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray
            ) {
                val listItems = ArrayList<UserItems>()
                val result = String(responseBody)
                try {

                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.id = jsonObject.getInt("id")
                        userItems.login = jsonObject.getString("login")
                        userItems.avatarUrl = jsonObject.getString("avatar_url")
                        listItems.add(userItems)
                        Log.d("@User", userItems.login.toString())
                    }
                    listUser.postValue(listItems)
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Log.d("@onFailureUser", errorMessage)
            }

        })
    }

    fun getUser(): LiveData<ArrayList<UserItems>> {
        return listUser
    }
}