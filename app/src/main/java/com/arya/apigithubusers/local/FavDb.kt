package com.arya.apigithubusers.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.arya.apigithubusers.model.UserItems

@Database (entities = [(UserItems::class)], version = 2, exportSchema = false)
@TypeConverters(Converter::class)
abstract class FavDb: RoomDatabase() {
    abstract fun favDao(): FavDao

    companion object {
        @Volatile private var instance: FavDb? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context)
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            FavDb::class.java,
            "FavoriteUser"
        ).build()
    }
}