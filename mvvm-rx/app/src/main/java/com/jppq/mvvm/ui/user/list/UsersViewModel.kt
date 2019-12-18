package com.jppq.mvvm.ui.user.list

import androidx.lifecycle.ViewModel
import com.jppq.mvvm.data.ui.UsersFragmentModel
import com.jppq.mvvm.repository.Repository
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import com.jppq.mvvm.data.api.RepositoryEntity
import com.jppq.mvvm.data.db.UserModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers


class UsersViewModel(private val repository: Repository): ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val error = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()
    private val users = MutableLiveData<List<UserModel>>()

    fun getUsers(): LiveData<List<UserModel>> = users

    fun getError(): LiveData<Boolean> = error

    fun getLoading(): LiveData<Boolean> = loading

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun getUsersFromDb() {
        loading.setValue(true)
        subscriptions.add(repository.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<UserModel>>() {
                override fun onSuccess(value: List<UserModel>) {
                    error.setValue(false)
                    users.setValue(value)
                    loading.setValue(false)
                }

                override fun onError(e: Throwable) {
                    error.setValue(true)
                    loading.setValue(false)
                }
            })
        )
    }
}