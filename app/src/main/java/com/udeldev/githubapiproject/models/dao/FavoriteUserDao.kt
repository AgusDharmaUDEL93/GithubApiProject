package com.udeldev.githubapiproject.models.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udeldev.githubapiproject.models.data.FavoriteUserModel

@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavoriteUser(favoriteUser: FavoriteUserModel)

    @Delete
    fun deleteFavoriteUser(favoriteUser: FavoriteUserModel)

    @Query("SELECT * from favoriteusermodel")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUserModel>>

    @Query("SELECT * FROM favoriteusermodel WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUserModel>
}