package com.arya.apigithubusers.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.arya.apigithubusers.local.FavDao
import com.arya.apigithubusers.local.FavDb

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val CODE_FAVORITE = 1
        private const val CODE_FAVORITE_ITEM = 2
        private const val AUTHORITY = "com.arya.apigithubusers.provider"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "UserItems", CODE_FAVORITE)
            addURI(AUTHORITY, "UserItems/#", CODE_FAVORITE_ITEM)
        }
    }

    private val favDao: FavDao by lazy {
        FavDb(requireNotNull(context)).favDao()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean = true

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? = context?.run {
        val cursor = when (uriMatcher.match(uri)) {
            CODE_FAVORITE -> favDao.cursorSelectAll()
            CODE_FAVORITE_ITEM -> favDao.cursorSelectById(ContentUris.parseId(uri))
            else -> null
        }
        cursor?.setNotificationUri(contentResolver, uri)
        cursor
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}
