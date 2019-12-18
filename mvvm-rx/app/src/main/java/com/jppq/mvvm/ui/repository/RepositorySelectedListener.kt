package com.jppq.mvvm.ui.repository

import com.jppq.mvvm.data.api.RepositoryEntity

interface RepositorySelectedListener {

    fun onRepositorySelected(repo: RepositoryEntity)
}
