package com.dr.predulive.authenticationDirectory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.dr.predulive.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
    }

    fun StudentResetPassword(view: View) {
        Log.i("->", "StudentResetPassword Clicked")
//        var intent: Intent = Intent(this, InstructorSignUpActivity::class.java)
//        startActivity(intent)
    }
}