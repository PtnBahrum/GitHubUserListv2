package com.example.githubuserlistv2.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserlistv2.api.ClientConfig
import com.example.githubuserlistv2.model.User
import com.example.githubuserlistv2.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel: ViewModel() {
    private val _users = MutableLiveData<ArrayList<User>>()
    val users : LiveData<ArrayList<User>> get() = _users

    fun setUser(query: String) {
        ClientConfig.api
            .getUser(query)
            .enqueue(object: Callback<UserResponse> {
                override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                    if(response.isSuccessful) {
                        _users.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    t.message?.let { Log.d("Failure", it) }
                }
            })
    }
}