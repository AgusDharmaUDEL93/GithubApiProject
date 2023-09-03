package com.udeldev.githubapiproject.controllers.view_models

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udeldev.githubapiproject.models.data.UserItemModel
import com.udeldev.githubapiproject.models.data.UserSearchModel
import com.udeldev.githubapiproject.providers.config.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _userList = MutableLiveData<List<UserItemModel>>()
    val userList: LiveData<List<UserItemModel>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun  gettingUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllUser()

        client.enqueue(
            object : Callback<List<UserItemModel>> {
                override fun onResponse(call: Call<List<UserItemModel>>, response: Response<List<UserItemModel>>) {
                    if (response.isSuccessful) {
                        _userList.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<List<UserItemModel>>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                    _isLoading.value = false
                }

            }
        )
    }
    fun gettingSearchUser(params : String){
        _isLoading.value = true
        _userList.value = emptyList()
        val client = ApiConfig.getApiService().getSearchUser(params)

        client.enqueue(
            object : Callback<UserSearchModel> {
                override fun onResponse(call: Call<UserSearchModel>, response: Response<UserSearchModel>) {

                    if (!response.isSuccessful){
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    if (response.body()?.items?.isEmpty() != true){
                        _userList.value = response.body()?.items!!
                    }

                    _isLoading.value = false
                }

                override fun onFailure(call: Call<UserSearchModel>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                    _isLoading.value = false
                }

            }
        )
    }
}