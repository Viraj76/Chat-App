package com.example.authentication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.authentication.adapters.MessageAdapter
import com.example.authentication.databinding.ActivityChatBinding
import com.example.authentication.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatBinding
    private lateinit var chatToolbar: Toolbar
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private  var senderRoom: String?=null
    private var receiverRoom: String?=null
    private lateinit var databaseReference: DatabaseReference
    private lateinit var senderUid:String
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        progressBar = binding.progressbar
        val nameOfUser = intent.getStringExtra("name")
        val uidOfReceiver = intent.getStringExtra("userId")
        senderUid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        // main thing
        senderRoom = "$uidOfReceiver $senderUid"
        receiverRoom = "$senderUid $uidOfReceiver"
        //
        messageList = ArrayList()
        settingUpToolBar(nameOfUser!!)
        prepareRecyclerViewForMessage()

        chitChatImplementation()

    }

    private fun chitChatImplementation() {
        progressBar.visibility = View.VISIBLE
        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child("Chats").child(senderRoom!!).child("Messages")
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(messages in snapshot.children){
                        val message = messages.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.setMessageList(messageList)
                    progressBar.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        binding.ivSendChat.setOnClickListener {
            val message = binding.tvMessage.text.toString()
            val messageObj = Message(message,senderUid)

            databaseReference.child("Chats").child(senderRoom!!).child("Messages").push()
                .setValue(messageObj).addOnSuccessListener {
                    databaseReference.child("Chats").child(receiverRoom!!).child("Messages").push()
                        .setValue(messageObj)
                }
            binding.tvMessage.setText("")
        }
    }

    private fun prepareRecyclerViewForMessage() {
        messageAdapter = MessageAdapter(binding.rvChats)
        binding.rvChats.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter = messageAdapter
        }
    }

    private fun settingUpToolBar(name :String) {
        binding.chatToolBar.title = name
        binding.chatToolBar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(binding.chatToolBar)
    }
}