package com.arya.apigithubusers.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.io.Serializable

@Parcelize
@Entity(tableName = "UserItems")
class UserItems (
    @PrimaryKey
    var id: Int = 0,
    var login: String? = null,
    var avatarUrl: String? = null,
    var name: String? = null,
    var repository: String? = null,
    var company: String? = null,
    var location: String? = null,
    var follower: @RawValue List<Follow>? = null,
    var following: @RawValue List<Follow>? = null
): Parcelable

class Follow (
    var login: String? = null,
    var avatarUrl: String? = null
): Serializable