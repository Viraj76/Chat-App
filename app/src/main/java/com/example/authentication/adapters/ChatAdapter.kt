package com.example.authentication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.authentication.ChatActivity
import com.example.authentication.databinding.UserItemViewBinding
import com.example.authentication.models.Users

class ChatAdapter(private val context: Context):RecyclerView.Adapter<ChatAdapter.UsersViewHolder>() {

    private var userList   = ArrayList<Users>()

    fun setUserList(userList : ArrayList<Users>){
        this.userList = userList
        notifyDataSetChanged()
    }


    class UsersViewHolder( val binding:UserItemViewBinding):ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(UserItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.binding.tvUserName.text = currentUser.name
        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("userId", currentUser.userId)  //imp
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  userList.size
    }


}