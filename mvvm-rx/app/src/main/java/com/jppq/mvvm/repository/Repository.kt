package com.jppq.mvvm.repository

import com.jppq.mvvm.data.api.RepositoryEntity
import com.jppq.mvvm.data.db.UserModel
import com.jppq.mvvm.repository.api.Api
import com.jppq.mvvm.repository.db.UserDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class Repository(val api: Api, val userDao: UserDao) {

    fun getUsers(): Single<List<UserModel>> {
        return userDao.getUsers()
    }

    fun getUsersObservable(): Observable<List<UserModel>> {
        return userDao.getUsers().filter { it.isNotEmpty() }.toObservable()
        /*return userDao.getUsers().filter { it.isNotEmpty() }
            .toObservable()
            .doOnNext {
                Timber.d("Dispatching ${it.size} users from DB...")
            }*/
    }

    fun insertUser(name: String, lastname: String, email: String): Completable {
        return userDao.insert(UserModel(name, lastname, email))
    }

    fun getRepositories(): Single<List<RepositoryEntity>> {
        return api.getRepositories()
    }

    fun getRepository(owner: String, name: String): Single<RepositoryEntity> {
        return api.getRepo(owner, name)
    }
}