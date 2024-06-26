package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var userList: ArrayList<User>
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth= FirebaseAuth.getInstance()
        mdbRef= FirebaseDatabase.getInstance().getReference()
        userList= ArrayList()
        adapter=UserAdapter(this,userList)
        userRecyclerView=findViewById(R.id.userRecyclerView)
        userRecyclerView.layoutManager=LinearLayoutManager(this)
        userRecyclerView.adapter=adapter

        mdbRef.child("Users").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                userList.clear()
                for(postSnapShot in p0.children)
                {
                    val currentUser=postSnapShot.getValue(User::class.java)
                    if(mAuth.currentUser?.uid !=currentUser?.uid)
                    { userList.add(currentUser!!) }

                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       if(item.itemId==R.id.logout)
       {
           //logic for logout
           mAuth.signOut()
           val intent=Intent(this@MainActivity,Login::class.java)
           finish()
           startActivity(intent)
           return true
       }
        return true
    }
}