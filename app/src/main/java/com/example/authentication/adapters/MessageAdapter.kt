package com.example.authentication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.authentication.databinding.ReceiveMessageBinding
import com.example.authentication.databinding.SendMessageBinding
import com.example.authentication.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter (private val recyclerView: RecyclerView): RecyclerView.Adapter<ViewHolder>() {

    private var  messageList = ArrayList<Message>()
    private val ITEM_RECEIVE = 1
    private val ITEM_SENT =2

    class SentViewHolder(val binding: SendMessageBinding):ViewHolder(binding.root)
    class ReceiveViewHolder(val binding: ReceiveMessageBinding):ViewHolder(binding.root)

    fun setMessageList( messageList : ArrayList<Message>){
        this.messageList = messageList
        notifyDataSetChanged()

        recyclerView.post {
            // Scroll to the last item in the RecyclerView
            recyclerView.smoothScrollToPosition(itemCount - 1)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if(viewType == 1){
            ReceiveViewHolder(ReceiveMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        } else{
            SentViewHolder(SendMessageBinding.inflate(LayoutInflater.from(parent.context),parent,false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (holder.javaClass == SentViewHolder::class.java){
            val viewHolder = holder as SentViewHolder
            holder.binding.tvSendMessage.text = currentMessage.message

        }
        else{
            val viewHolder = holder as ReceiveViewHolder
            holder.binding.tvReceiveMessage.text = currentMessage.message
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {                                        //to get to know about which view we have to inflate it
        val currentMessage = messageList[position]
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid
        return if(currentUserId == currentMessage.senderId) ITEM_SENT
        else ITEM_RECEIVE
    }



}