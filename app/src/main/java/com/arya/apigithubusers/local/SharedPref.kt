package com.arya.apigithubusers.local

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private var sharedPref: SharedPreferences =
        context.getSharedPreferences("filename", Context.MODE_PRIVATE)

    fun setReminderState(state: Boolean) {
        val editor = sharedPref.edit()
        editor.putBoolean("DailyReminder", state)
        editor.apply()
    }

    fun loadReminderState(): Boolean? {
        val state = sharedPref.getBoolean("DailyReminder", false)
        return state
    }
}