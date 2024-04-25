package com.luthtan.github_user_android.data.local

import androidx.room.*
import com.luthtan.github_user_android.data.dtos.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BaseDao {
    @Query("SELECT * FROM github_user")
    fun getUsers(): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(users: UserEntity): Long

    @Query("DELETE FROM github_user WHERE id = :id")
    fun deleteUser(id: Int): Int
}