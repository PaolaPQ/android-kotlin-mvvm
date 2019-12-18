package com.jppq.mvvm.ui.repository

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.jppq.mvvm.App
import com.jppq.mvvm.R
import com.jppq.mvvm.data.api.RepositoryEntity
import com.jppq.mvvm.ui.core.BaseFragment
import kotlinx.android.synthetic.main.repositories_fragment.*

class RepositoriesFragment: BaseFragment(), RepositorySelectedListener {

    lateinit var viewModel: RepositoriesViewModel

    override fun bindLayout(): Int = R.layout.repositories_fragment

    override fun onRepositorySelected(repo: RepositoryEntity) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        //viewModel = ViewModelProviders.of(this).get(RepositoriesViewModel::class.java)
        viewModel = App.injectRepositoriesViewModel()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pb_loading.visibility = View.VISIBLE
        initializeRecycler()
        observableViewModel()
        loadData()
    }

    private fun initializeRecycler() {
        val adapter = RepositoriesAdapter(viewModel, this, this)
        val gridLayoutManager = GridLayoutManager(activity, 1)
        gridLayoutManager.orientation = LinearLayoutManager.VERTICAL

        rc_repositories.apply {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            setAdapter(adapter)
            setLayoutManager(LinearLayoutManager(context))
        }
    }

    fun loadData() {
        viewModel.getRepositoriesFromApi()
    }

    private fun observableViewModel() {
        viewModel.getRepositories().observe(this, Observer<List<RepositoryEntity>> { repos ->
            if (repos != null) rc_repositories.setVisibility(View.VISIBLE)
        })

        viewModel.getError().observe(this, Observer<Boolean> { isError ->
            if (isError != null) {
                if (isError) {
                    tv_error.setVisibility(View.VISIBLE)
                    rc_repositories.setVisibility(View.GONE)
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
                    rc_repositories.setVisibility(View.GONE)
                }
            }
        })
    }
}