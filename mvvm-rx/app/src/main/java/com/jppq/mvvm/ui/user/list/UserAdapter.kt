package com.jppq.mvvm.ui.user.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.jppq.mvvm.R
import com.jppq.mvvm.data.db.UserModel

class UserAdapter(
    viewModel: UsersViewModel,
    lifecycleOwner: LifecycleOwner,
    private val listener: UserSelectedListener
) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val data = ArrayList<UserModel>()

    init {
        viewModel.getUsers().observe(lifecycleOwner,
            Observer<List<UserModel>> {users ->
                data.clear()
                if (users != null) {
                    data.addAll(users)
                    notifyDataSetChanged()
                }
            })
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list_item, parent, false)
        return ViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    class ViewHolder(itemView: View, listener: UserSelectedListener) :
        RecyclerView.ViewHolder(itemView) {

        var userName: TextView? = itemView.findViewById(R.id.tv_user_name)
        var userLastName: TextView? = itemView.findViewById(R.id.tv_user_last_name)
        var userEmail: TextView? = itemView.findViewById(R.id.tv_user_email)

        private var user: UserModel? = null

        init {
            itemView.setOnClickListener { v ->
                if (user != null) {
                    listener.onUserSelected(user!!)
                }
            }
        }

        fun bind(user: UserModel) {
            this.user = user
            userName!!.setText(user.first)
            userLastName!!.setText(user.last)
            userEmail!!.setText(user.email)
        }
    }
}