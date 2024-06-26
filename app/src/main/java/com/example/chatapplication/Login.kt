package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var edit_email:EditText
    private lateinit var edit_password:EditText
    private lateinit var btnLogin:Button
    private lateinit var btnSignup:Button
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        edit_email=findViewById(R.id.edit_email)
        edit_password=findViewById(R.id.edit_password)
        btnLogin=findViewById(R.id.btnLogin)
        btnSignup=findViewById(R.id.btnSignup)
        mAuth= FirebaseAuth.getInstance()

        btnSignup.setOnClickListener {
            val intent= Intent(this,SignUp::class.java)
            startActivity(intent)
        }
        btnLogin.setOnClickListener {
            val email=edit_email.text.toString()
            val password=edit_password.text.toString()
            login(email,password)
        }

    }
    private fun login(email:String,password:String)
    {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@Login,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@Login,"User Does Not exist",Toast.LENGTH_SHORT).show()

                }
            }

    }
}