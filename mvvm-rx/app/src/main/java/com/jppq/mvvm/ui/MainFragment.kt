package com.jppq.mvvm.ui

import android.os.Bundle
import android.view.View
import com.jppq.mvvm.R
import com.jppq.mvvm.ui.core.BaseFragment
import com.jppq.mvvm.ui.repository.RepositoriesFragment
import com.jppq.mvvm.ui.user.list.UsersFragment
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment: BaseFragment() {

    override fun bindLayout(): Int = R.layout.main_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_repositories_module.setOnClickListener({ v ->
            if (activity != null) {
                activity!!
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, RepositoriesFragment())
                    .addToBackStack(null).commit()
            }
        })

        btn_users_module.setOnClickListener({ v ->
            if (activity != null) {
                activity!!
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, UsersFragment())
                    .addToBackStack(null).commit()
            }
        })
    }
}