package com.jppq.mvvm.repository.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jppq.mvvm.data.db.UserModel
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getUsers(): Single<List<UserModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: UserModel): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(users: List<UserModel>): Completable
}