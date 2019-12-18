package com.jppq.mvvm.repository.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jppq.mvvm.data.db.UserModel

@Database(entities = arrayOf(UserModel::class), exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}