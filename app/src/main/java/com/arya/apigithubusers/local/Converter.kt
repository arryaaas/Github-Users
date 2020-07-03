package com.arya.apigithubusers.local

import androidx.room.TypeConverter
import com.arya.apigithubusers.model.Follow
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converter {
    @TypeConverter
    fun listToJson(value: List<Follow>): String {
        val type = object: TypeToken<List<Follow>>() {}.type
        return Gson().toJson(value, type)
    }

    @TypeConverter
    fun jsonToList(value: String): List<Follow> {
        val type = object: TypeToken<List<Follow>>() {}.type
        return  Gson().fromJson(value, type)
    }
}