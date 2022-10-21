package com.luthtan.github_user_android.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.luthtan.github_user_android.data.dtos.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class BaseDatabaseDao : RoomDatabase() {
    abstract fun baseDao(): BaseDao
}