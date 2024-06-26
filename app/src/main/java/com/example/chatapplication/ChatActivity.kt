package com.example.chatapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapplication.Message
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView : RecyclerView
    private lateinit var messageBox:EditText
    private lateinit var sentButton:ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList:ArrayList<Message>
    private lateinit var mdbRef:DatabaseReference
    var senderRoom:String?= null
    var recieverRoom:String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val recieverUid=intent.getStringExtra("uid")
        val senderUid= FirebaseAuth.getInstance().currentUser?.uid

        senderRoom=recieverUid+senderUid
        recieverRoom=senderUid+recieverUid

        supportActionBar?.title=name
        chatRecyclerView=findViewById(R.id.chatRecyclerView)
        messageBox=findViewById(R.id.messageBox)
        sentButton=findViewById(R.id.sentButton)
        mdbRef= FirebaseDatabase.getInstance().getReference()
        messageList=ArrayList()
        messageAdapter=MessageAdapter(this,messageList)
        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        // adding data to recyclerView
        mdbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(p0: DataSnapshot) {
                    messageList.clear()
                   for(postSnapshot in p0.children)
                   {
                       val message=postSnapshot.getValue(Message::class.java)
                       messageList.add(message!!)
                   }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        //adding message to database
        sentButton.setOnClickListener {
            val message=messageBox.text.toString()
            val messageObject=Message(message,senderUid)

            mdbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mdbRef.child("chats").child(recieverRoom!!).child("messages").push().setValue(messageObject)
            }
            messageBox.setText("")
        }
    }
}