package com.jppq.mvvm.ui.user.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jppq.mvvm.App
import com.jppq.mvvm.R
import com.jppq.mvvm.data.db.UserModel
import com.jppq.mvvm.ui.core.BaseFragment
import com.jppq.mvvm.ui.user.new.UserFragment
import kotlinx.android.synthetic.main.users_fragment.*

class UsersFragment : BaseFragment(), UserSelectedListener {

    lateinit var viewModel: UsersViewModel

    override fun bindLayout(): Int = R.layout.users_fragment

    override fun onUserSelected(user: UserModel) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //viewModel = ViewModelProviders.of(this).get(UsersViewModel::class.java)
        viewModel = App.injectUserListViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pb_loading.visibility = View.VISIBLE
        initializeView()
        observableViewModel()
        loadData()
    }

    private fun initializeView() {
        val adapter = UserAdapter(viewModel, this, this)
        val gridLayoutManager = GridLayoutManager(activity, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rc_users.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(context))
        }

        btn_add_user.setOnClickListener({view ->
            if (activity != null) {
                activity!!
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, UserFragment())
                    .addToBackStack(null).commit()
            }
        })
    }

    fun loadData() {
        viewModel.getUsersFromDb()
    }

    private fun observableViewModel() {
        viewModel.getUsers().observe(this, Observer<List<UserModel>> { repos ->
            if (repos != null) rc_users.setVisibility(View.VISIBLE)
        })

        viewModel.getError().observe(this, Observer<Boolean> { isError ->
            if (isError != null) {
                if (isError) {
                    tv_error.setVisibility(View.VISIBLE)
                    rc_users.setVisibility(View.GONE)
                    tv_error.setText("An Error Occurred While Loading Data!")
                } else {
                    tv_error.setVisibility(View.GONE)
                    tv_error.setText(null)
                }
            }
        })

        viewModel.getLoading().observe(this, Observer<Boolean> { isLoading ->
            if (isLoading != null) {
                pb_loading.setVisibility(if (isLoading) View.VISIBLE else View.GONE)
                if (isLoading) {
                    tv_error.setVisibility(View.GONE)
                    rc_users.setVisibility(View.GONE)
                }
            }
        })
    }
}