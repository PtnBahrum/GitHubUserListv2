package com.example.githubuserlistv2.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserlistv2.database.UserDao
import com.example.githubuserlistv2.database.UserDatabase
import com.example.githubuserlistv2.model.User
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {

    private val userDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        userDao = db.userDao()
    }

    fun getAllUser(): LiveData<List<User>> = userDao.getAllUser()

    fun getUser(login : String): LiveData<User> = userDao.getUser(login)

    fun setFavoriteUser(login : String): Boolean = userDao.setFavoriteUser(login)

    fun insert(user: User) {
        executorService.execute {
            userDao.insert(user)
        }
    }

    fun delete(login: String) {
        executorService.execute {
            userDao.delete(login)
        }
    }


}