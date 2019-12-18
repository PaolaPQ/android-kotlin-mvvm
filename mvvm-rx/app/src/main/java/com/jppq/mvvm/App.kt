package com.jppq.mvvm

import android.app.Application
import androidx.room.Room
import com.jppq.mvvm.repository.Repository
import com.jppq.mvvm.repository.api.Api
import com.jppq.mvvm.repository.db.AppDatabase
import com.jppq.mvvm.ui.repository.RepositoriesViewModel
import com.jppq.mvvm.ui.user.list.UsersViewModel
import com.jppq.mvvm.ui.user.new.UserViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class App : Application() {

    //For the sake of simplicity, for now we use this instead of Dagger
    companion object {
        private lateinit var retrofit: Retrofit
        private lateinit var api: Api
        private lateinit var repository: Repository
        private lateinit var database: AppDatabase

        private lateinit var userViewModel: UserViewModel
        private lateinit var userListViewModel: UsersViewModel
        private lateinit var repositoriesViewModel: RepositoriesViewModel

        fun injectUserApi() = api

        fun injectUserViewModel() = userViewModel

        fun injectUserListViewModel() = userListViewModel

        fun injectRepositoriesViewModel() = repositoriesViewModel

        fun injectUserDao() = database.userDao()
    }

    override fun onCreate() {
        super.onCreate()
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())

        retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl("https://api.github.com/")
            .build()

        api = retrofit.create(Api::class.java)

        database = Room.databaseBuilder(applicationContext,
            AppDatabase::class.java, "mvvm-database").build()

        repository = Repository(api, database.userDao())

        userViewModel = UserViewModel(repository)
        userListViewModel = UsersViewModel(repository)
        repositoriesViewModel = RepositoriesViewModel(repository)
    }
}