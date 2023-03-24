package com.example.authentication

import android.os.Binder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.authentication.databinding.UserItemViewBinding
import com.example.authentication.models.Users

class ChatAdapter:RecyclerView.Adapter<ChatAdapter.UsersViewHolder>() {

    private var userList   = ArrayList<Users>()

    fun setUserList(userList : ArrayList<Users>){
        this.userList = userList
    }


    class UsersViewHolder( val binding:UserItemViewBinding):ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(UserItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.binding.tvUserName.text = currentUser.name
    }

    override fun getItemCount(): Int {
        return  userList.size
    }


}