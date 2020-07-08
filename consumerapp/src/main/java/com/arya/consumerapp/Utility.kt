package com.arya.consumerapp

import android.net.Uri

object Utility {
    private const val TABLE_USER = "UserItems"
    private const val AUTHORITY_USER = "com.arya.apigithubusers.provider"

    val CONTENT_USER_URI: Uri = Uri.Builder().scheme("content")
        .authority(AUTHORITY_USER)
        .appendPath(TABLE_USER)
        .build()

    const val COLUMN_LOGIN = "login"
    const val COLUMN_AVATAR_URL = "avatarUrl"

}