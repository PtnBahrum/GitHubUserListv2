package com.example.githubuserlistv2.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.githubuserlistv2.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Query("SELECT * FROM user_table")
    fun getAllUser(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE login = :login")
    fun getUser(login: String) : LiveData<User>

    @Query("DELETE FROM user_table WHERE login = :login")
    fun delete(login : String)

    @Query("SELECT EXISTS(SELECT * FROM user_table WHERE login = :login)")
    fun setFavoriteUser(login: String): Boolean
}