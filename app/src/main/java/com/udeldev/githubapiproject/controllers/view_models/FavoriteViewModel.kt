package com.udeldev.githubapiproject.controllers.view_models

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udeldev.githubapiproject.models.response.UserItemModel
import com.udeldev.githubapiproject.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    companion object {
        private const val TAG = "FavoriteViewModel"
    }

    private val _userFavoriteList = MutableLiveData<List<UserItemModel>>()
    val userFavoriteList: LiveData<List<UserItemModel>> = _userFavoriteList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val favoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun gettingUserFavorite() = favoriteUserRepository.getAllFavoriteUser()
}