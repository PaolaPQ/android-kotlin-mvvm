package com.jppq.mvvm.data.ui

import com.jppq.mvvm.data.db.UserModel

data class UsersFragmentModel(val users: List<UserModel>, val message: String, val error: Throwable? = null)