package com.example.chatapplication
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_SENT=2
    val ITEM_RECIEVE=1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        if(p1==1)
        {
            val view:View= LayoutInflater.from(context).inflate(R.layout.recieve,p0,false)
            return RecieveViewHolder(view)
        }
        else{
            val view:View= LayoutInflater.from(context).inflate(R.layout.sent,p0,false)
            return SentViewHolder(view)

        }

    }

    override fun getItemCount(): Int {
  return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage=messageList[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        { return ITEM_SENT}
        else{ return ITEM_RECIEVE}
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        val currentMessage=messageList[p1]
        if(p0.javaClass== SentViewHolder::class.java)
        {
            // for sent

            val viewHolder=p0 as SentViewHolder
            p0.sentMessage.text=currentMessage.message


        }
        else{
            // for recieved

            val viewHolder=p0 as RecieveViewHolder
            p0.recieveMessage.text=currentMessage.message
        }
    }
    class SentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
   val sentMessage=itemView.findViewById<TextView>(R.id.txt_sent)
    }

    class RecieveViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
    {
        val recieveMessage=itemView.findViewById<TextView>(R.id.txt_recieve)
    }


}