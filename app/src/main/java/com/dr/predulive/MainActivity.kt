package com.dr.predulive

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dr.predulive.authenticationDirectory.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    val who = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){

        }
    }

    fun studentPanel(view: View) {
        var intent: Intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("who", "STUDENT")
        startActivity(intent)
    }

    fun instructorPanel(view: View) {

        Log.d("instructor panel", "--")
        var intent: Intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("who", "INSTRUCTOR")
        startActivity(intent)
    }
}