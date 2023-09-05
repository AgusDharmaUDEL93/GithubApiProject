package com.udeldev.githubapiproject.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.udeldev.githubapiproject.controllers.database.FavoriteUserDatabase
import com.udeldev.githubapiproject.models.dao.FavoriteUserDao
import com.udeldev.githubapiproject.models.data.FavoriteUserModel
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val favoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteUserDatabase.getDatabase(application)
        favoriteUserDao = db.favoriteUserDao()
    }


    fun insertFavoriteUser(favoriteUser: FavoriteUserModel) {
        executorService.execute { favoriteUserDao.insertFavoriteUser(favoriteUser) }
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUserModel) {
        executorService.execute { favoriteUserDao.deleteFavoriteUser(favoriteUser) }
    }

    fun getFavoriteUserByUsername(username: String) = favoriteUserDao.getFavoriteUserByUsername(username)


    fun getAllFavoriteUser() = favoriteUserDao.getAllFavoriteUser()


}