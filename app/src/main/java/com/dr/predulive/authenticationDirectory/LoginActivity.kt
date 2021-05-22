package com.dr.predulive.authenticationDirectory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.dr.predulive.R
import com.dr.predulive.instructorDirectory.InstructorDashboardActivity
import com.dr.predulive.studentDirectory.StudentDashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {


    private lateinit var loginTextView: TextView
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: androidx.appcompat.widget.AppCompatButton
    private lateinit var signUpTextView: TextView
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var who: String

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Firebase Auth
        auth = Firebase.auth

        loginTextView = findViewById<View>(R.id.loginTextView) as TextView
        loginEmail = findViewById<View>(R.id.loginEmail) as EditText
        loginPassword = findViewById<View>(R.id.loginPassword) as EditText
        loginButton = findViewById<View>(R.id.loginButton) as androidx.appcompat.widget.AppCompatButton
        signUpTextView = findViewById<View>(R.id.signupTextView) as TextView
        forgotPasswordTextView = findViewById<View>(R.id.forgotPasswordTextView) as TextView

    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.

        var oldIntent: Intent = intent
        who = oldIntent.getStringExtra("who").toString()
        loginTextView.text = "$who login"


        FirebaseDatabase.getInstance().reference.child(auth.uid.toString()).child("WHO").get().addOnSuccessListener {
            val currentUser = auth.currentUser
            if(currentUser != null) {
                loadDashboard(who, 1, it.value.toString())
            }
        }
    }

    fun loadDashboard(who: String, alreadyLoggedIn: Int, userWho: String) {

        if(alreadyLoggedIn == 1 && who != userWho) {
            auth.signOut()
        } else {
            var intent: Intent
            if (who == "STUDENT") {
                intent = Intent(this, StudentDashboardActivity::class.java)
                Log.i("student", "login")
            } else {
                intent = Intent(this, InstructorDashboardActivity::class.java)
                Log.i("instructor", "login")
            }
            startActivity(intent)
        }
    }

    fun signUp(view: View) {
        var intentSignUp = Intent(this, SignUpActivity::class.java)
        intentSignUp.putExtra("who", who)
        startActivity(intentSignUp)
    }

    fun login(view: View) {

        auth.signInWithEmailAndPassword(loginEmail.text.toString(), loginPassword.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    loadDashboard(who, 0, who)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun forgotStudentPassword(view: View) {

    }
}