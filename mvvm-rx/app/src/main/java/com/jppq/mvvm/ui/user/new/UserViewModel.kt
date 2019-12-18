package com.jppq.mvvm.ui.user.new

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jppq.mvvm.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers


class UserViewModel(private val repository: Repository): ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val error = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()

    fun getError(): LiveData<Boolean> = error

    fun getLoading(): LiveData<Boolean> = loading

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun saveUserOnDb(name: String, lastname: String, email: String) {
        loading.setValue(true)
        subscriptions.add(repository.insertUser(name, lastname, email)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    error.setValue(false)
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