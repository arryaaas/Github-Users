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
import java.lang.Exception

class SearchViewModel: ViewModel() {
    val listSearch = MutableLiveData<ArrayList<UserItems>>()

    fun setSearch(username: String) {
        val listItems = ArrayList<UserItems>()

        val url = "https://api.github.com/search/users?q=${username}"
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
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val user = list.getJSONObject(i)
                        val userItems = UserItems()
                        userItems.id = user.getInt("id")
                        userItems.login = user.getString("login")
                        userItems.avatarUrl = user.getString("avatar_url")
                        listItems.add(userItems)
                    }

                    listSearch.postValue(listItems)
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
                Log.d("@onFailureSearch", errorMessage)
            }

        })
    }

    fun getSearch(): LiveData<ArrayList<UserItems>> {
        return listSearch
    }
}