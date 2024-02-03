package com.example.githubuserlistv2.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int? = null,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,

    @field:SerializedName("items")
    val items: ArrayList<User>
)

@Entity(tableName = "user_table")
@Parcelize
data class User(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "login")
    var login: String? = null,

    @ColumnInfo(name = "name")
    var name: String? = null,

    @ColumnInfo(name = "followers_url")
    var followers_url: String? = null,

    @ColumnInfo(name = "following_url")
    var following_url: String? = null,

    @ColumnInfo(name = "followers")
    var followers: Int? = 0,

    @ColumnInfo(name = "following")
    var following: Int = 0,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null,

    @ColumnInfo(name = "repos_url")
    var repos_url: String? = null,

    @ColumnInfo(name = "bio")
    var bio: String? = null,

    @ColumnInfo(name = "company")
    var company: String? = null,

    @ColumnInfo(name = "public_repos")
    var public_repos: Int? = 0,

    @ColumnInfo(name = "url")
    var url: String? = null,

    @ColumnInfo(name = "html_url")
    var html_url: String? = null,

    @ColumnInfo(name = "blog")
    var blog : String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

) : Parcelable