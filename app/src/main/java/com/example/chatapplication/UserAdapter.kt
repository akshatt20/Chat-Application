package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context:Context, val userList:ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): UserViewHolder {
        val view:View= LayoutInflater.from(context).inflate(R.layout.user_layout,p0,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
       return userList.size
    }

    override fun onBindViewHolder(p0: UserViewHolder, p1: Int) {
        val currentUser=userList[p1]
        p0.textName.text=currentUser.name
        p0.itemView.setOnClickListener{
            val intent= Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
            context.startActivity(intent)
        }
    }

    class UserViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val textName=itemView.findViewById<TextView>(R.id.txt_name)

    }

}