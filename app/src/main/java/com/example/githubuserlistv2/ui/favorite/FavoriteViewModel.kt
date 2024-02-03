package com.example.githubuserlistv2.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserlistv2.model.User
import com.example.githubuserlistv2.repository.UserRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val userRepository: UserRepository = UserRepository(application)

    var username: String = "default"

    fun getAllUser(): LiveData<List<User>> = userRepository.getAllUser()

    fun getUser(login: String): LiveData<User> = userRepository.getUser(login)

    fun setFavoriteUser(login: String): Boolean = userRepository.setFavoriteUser(login)

    fun insert(user: User) {
        userRepository.insert(user)
    }

    fun delete(login : String) {
        userRepository.delete(login)
    }

}

