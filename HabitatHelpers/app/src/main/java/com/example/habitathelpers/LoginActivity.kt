package com.example.habitathelpers

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    private lateinit var userNameET: EditText
    private lateinit var passwordET: EditText

    override fun onResume() {
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null){
            startActivity(user)
        }
        super.onResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_login)

        //Initialize an instance of FirebaseAuth
        userNameET = findViewById<EditText>(R.id.edit_text_email)
        passwordET = findViewById<EditText>(R.id.edit_text_password)

        val login = findViewById<Button>(R.id.login)
        login.setOnClickListener {
            login()
        }
        val createButton = findViewById<Button>(R.id.create_user)
        createButton.setOnClickListener {
            createUser()
        }

        // use custom toolbar
        // I've disabled the supportActionBar on the login activity, as a user would have to login
//         or register before creating a new projects.

//        setSupportActionBar(toolbar)
//
//        //navigation drawer setup
//        val toggle = ActionBarDrawerToggle(this, mainAct, toolbar, R.string.open_nav, R.string.close_nav)
//        mainAct.addDrawerListener(toggle)
//        toggle.syncState()
//
//        navView.setNavigationItemSelectedListener(this)
    }


    //login
    private fun login() {
        auth.signInWithEmailAndPassword(userNameET.text.toString(), passwordET.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    startActivity(user)
                } else {
                    Log.w("aaa", "signInWithEmail:failure", task.exception)
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed: "+task.exception,
                        Toast.LENGTH_SHORT).show()
                }


            }

    }

    private fun startActivity(user: FirebaseUser?){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("user", user)
        startActivity(intent)
    }

    private fun createUser() {
        auth.createUserWithEmailAndPassword(userNameET.text.toString(),
            passwordET.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    startActivity(user)
                } else {
                    Log.w("aaa", "createUserWithEmail:failure", task.exception)
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed: "+task.exception,
                        Toast.LENGTH_SHORT).show()
                }
                // ...
            }
    }


}