package com.dr.predulive.authenticationDirectory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.dr.predulive.R
import com.dr.predulive.instructorDirectory.InstructorDashboardActivity
import com.dr.predulive.studentDirectory.StudentDashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var name: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize Firebase Auth
        auth = Firebase.auth

        name = findViewById<View>(R.id.StudentSignUpName) as EditText
        email = findViewById<View>(R.id.StudentSignUpEmail) as EditText
        password = findViewById<View>(R.id.StudentSignUpPassword) as EditText
        confirmPassword = findViewById<View>(R.id.StudentSignUpPasswordConfirm_password) as EditText

    }


    fun studentSignUp(view: View) {
        Log.i("->", "studentSignUp Clicked")

        if(TextUtils.isEmpty(email.text.trim().toString())) {
            email.setError("Email is Required")
        } else if(TextUtils.isEmpty(name.text.trim().toString())) {
            name.setError("Name is Required")
        } else if(TextUtils.isEmpty(password.text.trim().toString())) {
            password.setError("Password is Required")
        } else if(TextUtils.isEmpty(confirmPassword.text.trim().toString())) {
            confirmPassword.setError("Confirm password is Required")
        } else if(!confirmPassword.text.toString().equals(password.text.toString())) {
            password.setError("Passwords Must Match")
            confirmPassword.setError("Passwords Must Match")
        } else {
            // sign up new user
            signUpNewUsers()
        }

    }

    private fun signUpNewUsers() {
        auth.createUserWithEmailAndPassword(email.text.trim().toString(), password.text.trim().toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser

                    task.result?.user?.let {
                        FirebaseDatabase.getInstance().reference
                            .child(it.uid).child("EMAIL").setValue(email.text.trim().toString())
                        FirebaseDatabase.getInstance().reference
                            .child(it.uid).child("NAME").setValue(name.text.trim().toString())
                        FirebaseDatabase.getInstance().reference
                            .child(it.uid).child("ABOUT").setValue("Nothing")
                        FirebaseDatabase.getInstance().reference
                            .child(it.uid).child("DOB").setValue("Nothing")

                        var intent = intent
                        var who: String? = intent.getStringExtra("who")

                        FirebaseDatabase.getInstance().reference
                            .child(it.uid).child("WHO").setValue(who)

                        email.setText("")
                        name.setText("")
                        password.setText("")
                        confirmPassword.setText("")

                        if (who != null) {
                            loadDashboard(who)
                        }

                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
    fun loadDashboard(who: String) {


        var intent: Intent
        if(who == "STUDENT") {
            intent = Intent(this, StudentDashboardActivity::class.java)
            Log.i("student", "login")
        } else {
            intent = Intent(this, InstructorDashboardActivity::class.java)
            Log.i("instructor", "login")
        }
        startActivity(intent)
    }

}