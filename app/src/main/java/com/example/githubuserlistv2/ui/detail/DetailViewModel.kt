package com.example.githubuserlistv2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserlistv2.api.ClientConfig
import com.example.githubuserlistv2.model.User
import retrofit2.*

class DetailViewModel :  ViewModel() {

    private val _user = MutableLiveData<User>()
    val user : LiveData<User> = _user

    private val _followers = MutableLiveData<ArrayList<User>>()
    val followers : MutableLiveData<ArrayList<User>>  = _followers

    private val _followings = MutableLiveData<ArrayList<User>>()
    val followings : MutableLiveData<ArrayList<User>>  = _followings

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    var username: String = "default"

    fun setDetailUser() {
        _isLoading.value = true
        ClientConfig.api
            .getDetailUser(username)
            .enqueue(object: Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    _isLoading.value = false
                    if(response.isSuccessful) {
                        _user.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    _isLoading.value = false
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun setFollower() {
        _isLoading.value = true
        ClientConfig.api
            .getFollower(username)
            .enqueue(object: Callback<ArrayList<User>> {
                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                    _isLoading.value = false
                    if(response.isSuccessful) {
                        _followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    _isLoading.value = false
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }

    fun setFollowing() {
        _isLoading.value = true
        ClientConfig.api
            .getFollowing(username)
            .enqueue(object: Callback<ArrayList<User>> {
                override fun onResponse(call: Call<ArrayList<User>>, response: Response<ArrayList<User>>) {
                    _isLoading.value = false
                    if(response.isSuccessful) {
                        _followings.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    _isLoading.value = false
                    t.message?.let { Log.d("Failure", it) }
                }

            })
    }


}