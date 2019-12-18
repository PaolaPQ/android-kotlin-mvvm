package com.jppq.mvvm.data.api

import com.google.gson.annotations.SerializedName

data class RepositoryEntity(
    val id: Long,
    val name: String,
    val description: String,
    val owner: UserEntity,
    @SerializedName("stargazers_count") val stars: Long,
    @SerializedName("forks_count") val forks: Long
) {
    data class UserEntity(
        val login: String
    )
}