package com.jppq.mvvm.ui.repository

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jppq.mvvm.R
import com.jppq.mvvm.data.api.RepositoryEntity
import java.util.ArrayList

class RepositoriesAdapter(
    viewModel: RepositoriesViewModel,
    lifecycleOwner: LifecycleOwner,
    private val listener: RepositorySelectedListener
) : RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    private val data = ArrayList<RepositoryEntity>()

    init {
        viewModel.getRepositories().observe(lifecycleOwner,
            Observer<List<RepositoryEntity>> {repos ->
                data.clear()
                if (repos != null) {
                    data.addAll(repos)
                    notifyDataSetChanged()
                }
            })
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repository_list_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    class ViewHolder(itemView: View, listener: RepositorySelectedListener) :
        RecyclerView.ViewHolder(itemView) {

        var repoName: TextView? = itemView.findViewById(R.id.tv_repo_name)
        var repoDescription: TextView? = itemView.findViewById(R.id.tv_repo_description)
        var repoForks: TextView? = itemView.findViewById(R.id.tv_forks)
        var repoStars: TextView? = itemView.findViewById(R.id.tv_stars)

        private var repository: RepositoryEntity? = null

        init {
            itemView.setOnClickListener { v ->
                if (repository != null) {
                    listener.onRepositorySelected(repository!!)
                }
            }
        }

        fun bind(repository: RepositoryEntity) {
            this.repository = repository
            repoName!!.setText(repository.name)
            repoDescription!!.setText(repository.description)
            repoForks!!.setText(repository.forks.toString())
            repoStars!!.setText(repository.stars.toString())
        }
    }
}