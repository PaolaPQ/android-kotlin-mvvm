package com.jppq.mvvm.repository.api

import com.jppq.mvvm.data.api.RepositoryEntity
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {

    @GET("orgs/Google/repos")
    fun getRepositories(): Single<List<RepositoryEntity>>

    @GET("repos/{owner}/{name}")
    fun getRepo(@Path("owner") owner: String, @Path("name") name: String): Single<RepositoryEntity>
}