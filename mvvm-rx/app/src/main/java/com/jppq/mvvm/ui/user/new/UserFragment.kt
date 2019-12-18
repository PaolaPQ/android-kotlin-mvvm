package com.jppq.mvvm.ui.user.new

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.jppq.mvvm.App
import com.jppq.mvvm.R
import com.jppq.mvvm.ui.core.BaseFragment
import com.jppq.mvvm.ui.user.list.UsersFragment
import kotlinx.android.synthetic.main.user_fragment.*

class UserFragment : BaseFragment() {

    lateinit var viewModel: UserViewModel

    override fun bindLayout(): Int = R.layout.user_fragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        viewModel = App.injectUserViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeView()
        observableViewModel()
    }

    private fun initializeView() {
        btn_save_user.setOnClickListener({view ->
            viewModel.saveUserOnDb(
                et_name.text.toString().trim(),
                et_last_name.text.toString().trim(),
                et_eamil.text.toString().trim())
        })
    }

    private fun observableViewModel() {
        viewModel.getError().observe(this, Observer<Boolean> { isError ->
            if (isError != null) {
                if (isError) {
                    tv_error.setVisibility(View.VISIBLE)
                    tv_error.setText("An Error Occurred While Saving Data!")
                } else {
                    tv_error.setVisibility(View.GONE)
                    tv_error.setText(null)
                    goBack()
                }
            }
        })

        viewModel.getLoading().observe(this, Observer<Boolean> { isLoading ->
            if (isLoading != null) {
                pb_loading.setVisibility(if (isLoading) View.VISIBLE else View.GONE)
                if (isLoading) {
                    tv_error.setVisibility(View.GONE)
                }
            }
        })
    }

    fun goBack() {
        if (activity != null) {
            activity!!
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, UsersFragment())
                .addToBackStack(null).commit()
        }
    }
}