package com.udeldev.githubapiproject.controllers.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udeldev.githubapiproject.models.data.FavoriteUserModel
import com.udeldev.githubapiproject.models.response.UserDetailModel
import com.udeldev.githubapiproject.providers.config.ApiConfig
import com.udeldev.githubapiproject.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {
    companion object {
        private const val TAG = "DetailViewModel"
    }

    private val _userDetail = MutableLiveData<UserDetailModel>()
    val userDetail: LiveData<UserDetailModel> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun insertFavoriteUser(favoriteUser: FavoriteUserModel) {
        favoriteUserRepository.insertFavoriteUser(favoriteUser)
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUserModel) {
        favoriteUserRepository.deleteFavoriteUser(favoriteUser)
    }

    fun getFavoriteUserByUsername(username: String) = favoriteUserRepository.getFavoriteUserByUsername(username)


    fun gettingUserDetail(username: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(
            object : Callback<UserDetailModel> {
                override fun onResponse(call: Call<UserDetailModel>, response: Response<UserDetailModel>) {
                    if (response.isSuccessful) {
                        _userDetail.value = response.body()
                    } else {
                        Log.e(TAG, "onFailure: ${response.message()}")
                    }
                    _isLoading.value = false
                }

                override fun onFailure(call: Call<UserDetailModel>, t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message.toString()}")
                    _isLoading.value = false
                }
            }
        )
    }
}