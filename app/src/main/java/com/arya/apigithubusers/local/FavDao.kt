package com.arya.apigithubusers.local

import android.database.Cursor
import androidx.room.*
import com.arya.apigithubusers.model.UserItems


@Dao
interface FavDao {

    @Insert
    suspend fun saveUser(favorite: UserItems)

    @Query("select * from UserItems")
    suspend fun readUser(): List<UserItems>

    @Delete
    suspend fun delUser(favorite: UserItems)

    @Query("select exists (select 1 from UserItems where id = :id)")
    suspend fun isFavoriteUser(id: Int): Int

    @Query("select * from UserItems")
    fun cursorSelectAll(): Cursor

    @Query("select * from UserItems where id = :id")
    fun cursorSelectById(id: Long): Cursor

    @Query("select * from UserItems")
    fun readFav(): List<UserItems>
}