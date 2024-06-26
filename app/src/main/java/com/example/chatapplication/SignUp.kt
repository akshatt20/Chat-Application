package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edit_name: EditText
    private lateinit var edit_email: EditText
    private lateinit var edit_password: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mdbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        edit_name=findViewById(R.id.edit_name)
        edit_email=findViewById(R.id.edit_email)
        edit_password=findViewById(R.id.edit_password)
        btnSignup=findViewById(R.id.btnSignup)
        mAuth= FirebaseAuth.getInstance()

        btnSignup.setOnClickListener {
            val name=edit_name.text.toString()
            val email=edit_email.text.toString()
            val password=edit_password.text.toString()

            signup(name,email,password)
        }

    }
    private fun signup(name:String,email:String,password:String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent= Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                  Toast.makeText(this@SignUp,"some error occurred" ,Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun addUserToDatabase(name:String,email:String,uid:String)
    {  mdbRef=FirebaseDatabase.getInstance().getReference()
        mdbRef.child("Users").child(uid).setValue(User(name,email,uid))

    }

}