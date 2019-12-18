package com.jppq.mvvm.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.jppq.mvvm.data.api.RepositoryEntity
import com.jppq.mvvm.repository.Repository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RepositoriesViewModel(private val repository: Repository): ViewModel() {

    private val subscriptions = CompositeDisposable()

    private val error = MutableLiveData<Boolean>()
    private val loading = MutableLiveData<Boolean>()
    private val repositories = MutableLiveData<List<RepositoryEntity>>()

    fun getRepositories(): LiveData<List<RepositoryEntity>> = repositories

    fun getError(): LiveData<Boolean> = error

    fun getLoading(): LiveData<Boolean> = loading

    override fun onCleared() {
        super.onCleared()
        subscriptions.clear()
    }

    fun getRepositoriesFromApi() {
        loading.setValue(true)
        subscriptions.add(repository.getRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object :
                DisposableSingleObserver<List<RepositoryEntity>>() {
                override fun onSuccess(value: List<RepositoryEntity>) {
                    error.setValue(false)
                    repositories.setValue(value)
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