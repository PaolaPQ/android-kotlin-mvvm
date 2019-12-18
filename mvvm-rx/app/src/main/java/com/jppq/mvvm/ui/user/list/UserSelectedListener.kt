package com.jppq.mvvm.ui.user.list

import com.jppq.mvvm.data.db.UserModel

interface UserSelectedListener {

    fun onUserSelected(user: UserModel)
}