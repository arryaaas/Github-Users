package com.arya.apigithubusers.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arya.apigithubusers.BuildConfig.API_KEY
import com.arya.apigithubusers.model.Follow
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray

class FollowViewModel: ViewModel() {
    val listFollower = MutableLiveData<ArrayList<Follow>>()
    val listFollowing = MutableLiveData<ArrayList<Follow>>()

    fun setFollower(username: String) {
        val url = "https://api.github.com/users/${username}/followers"
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
                val listItems = ArrayList<Follow>()
                val result = String(responseBody)
                try {

                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val follow = Follow()
                        follow.login = jsonObject.getString("login")
                        follow.avatarUrl = jsonObject.getString("avatar_url")
                        listItems.add(follow)
                        Log.d("@User", follow.login.toString())
                    }
                    listFollower.postValue(listItems)
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
                Log.d("@onFailureFollower", errorMessage)
            }

        })
    }

    fun setFollowing(username: String) {
        val url = "https://api.github.com/users/${username}/following"
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
                val listItems = ArrayList<Follow>()
                val result = String(responseBody)
                try {

                    val jsonArray = JSONArray(result)

                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val follow = Follow()
                        follow.login = jsonObject.getString("login")
                        follow.avatarUrl = jsonObject.getString("avatar_url")
                        listItems.add(follow)
                        Log.d("@User", follow.login.toString())
                    }
                    listFollowing.postValue(listItems)
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
                Log.d("@onFailureFollowing", errorMessage)
            }

        })
    }

    fun getFollower(): LiveData<ArrayList<Follow>> {
        return listFollower
    }

    fun getFollowing(): LiveData<ArrayList<Follow>> {
        return listFollowing
    }
}