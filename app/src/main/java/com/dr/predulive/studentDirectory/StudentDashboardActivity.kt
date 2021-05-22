package com.dr.predulive.studentDirectory

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.dr.predulive.R
import com.dr.predulive.dashboardActivities.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class StudentDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var studentAssignment: androidx.cardview.widget.CardView
    private lateinit var homework: ImageView
    private lateinit var studentInstructors: androidx.cardview.widget.CardView
    private lateinit var studentInstructorsImage: ImageView
    private lateinit var studentChatPanel: androidx.cardview.widget.CardView
    private lateinit var studentChatPanelImage: ImageView
    private lateinit var studentFAQ: androidx.cardview.widget.CardView
    private lateinit var studentFAQImage: ImageView
    private lateinit var studentEditProfile: androidx.cardview.widget.CardView
    private lateinit var studentEditProfileImage: ImageView
    private lateinit var calendar: androidx.cardview.widget.CardView
    private lateinit var calendarImage: ImageView
    private lateinit var studentAllInstructor: androidx.cardview.widget.CardView
    private lateinit var studentAllInstructorImage: ImageView
    private lateinit var studentAbout: androidx.cardview.widget.CardView
    private lateinit var studentAboutImage: ImageView
    private lateinit var studentLogout: androidx.cardview.widget.CardView
    private lateinit var studentLogoutImage: ImageView

    private lateinit var studentDashboardProfileImage: ImageView

    private lateinit var studentDashoardName: TextView
    private lateinit var studentDashboardScrollView: ScrollView

    private var who: String = "STUDENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_dashboard)

        // Initialize Firebase Auth
        auth = Firebase.auth

        studentAssignment = findViewById<View>(R.id.studentAssignment) as androidx.cardview.widget.CardView
        homework = findViewById<View>(R.id.homework) as ImageView
        studentInstructors = findViewById<View>(R.id.studentInstructors) as androidx.cardview.widget.CardView
        studentInstructorsImage = findViewById<View>(R.id.studentInstructorsImage) as ImageView
        studentChatPanel = findViewById<View>(R.id.studentChatPanel) as androidx.cardview.widget.CardView
        studentChatPanelImage = findViewById<View>(R.id.studentChatPanelImage) as ImageView
        studentFAQ = findViewById<View>(R.id.studentFAQ) as androidx.cardview.widget.CardView
        studentFAQImage = findViewById<View>(R.id.studentFAQImage) as ImageView
        studentEditProfile = findViewById<View>(R.id.studentEditProfile) as androidx.cardview.widget.CardView
        studentEditProfileImage = findViewById<View>(R.id.studentEditProfileImage) as ImageView
        calendar = findViewById<View>(R.id.calendar) as androidx.cardview.widget.CardView
        calendarImage = findViewById<View>(R.id.calendarImage) as ImageView
        studentAllInstructor = findViewById<View>(R.id.studentAllInstructor) as androidx.cardview.widget.CardView
        studentAllInstructorImage = findViewById<View>(R.id.studentAllInstructorImage) as ImageView
        studentAbout = findViewById<View>(R.id.studentAbout) as androidx.cardview.widget.CardView
        studentAboutImage = findViewById<View>(R.id.studentAboutImage) as ImageView
        studentLogout = findViewById<View>(R.id.studentLogout) as androidx.cardview.widget.CardView
        studentLogoutImage = findViewById<View>(R.id.studentLogoutImage) as ImageView

        studentDashboardProfileImage = findViewById<View>(R.id.studentDashboardProfileImage) as ImageView
        studentDashoardName = findViewById<TextView>(R.id.studentDashboardName) as TextView
        studentDashboardScrollView = findViewById<View>(R.id.studentDashboardScrollView) as ScrollView

        studentDashboardScrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener{
            override fun onScrollChange(
                v: View?,
                scrollX: Int,
                scrollY: Int,
                oldScrollX: Int,
                oldScrollY: Int
            ) {
                if(oldScrollY > 150) {
                    studentDashboardScrollView.setBackgroundColor(Color.WHITE)
                } else {
                    studentDashboardScrollView.setBackgroundResource(R.drawable.background_custom)
                }
            }
        })

        loadProfile()


    }

    fun loadProfile() {
        // loading profile image
        FirebaseDatabase.getInstance().reference
                .child(auth.uid.toString()).child("PROFILE_URL").get().addOnSuccessListener {
                    Glide.with(this)
                            .load(it.value.toString())
                            .centerCrop()
                            .encodeQuality(10)
                            .placeholder(R.drawable.loading_layout)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(studentDashboardProfileImage)
                }
        FirebaseDatabase.getInstance().reference.child(auth.uid.toString()).get().addOnSuccessListener {
            studentDashoardName.text = it.child("NAME").value.toString()
        }
    }

    fun studentTodaysAssignment(view: View) {
        Toast.makeText(this, "No Assignment!", Toast.LENGTH_SHORT).show()
        animate(studentAssignment, homework)
    }

    fun animate(view: androidx.cardview.widget.CardView, image: ImageView) {
        view.setBackgroundTintList(this.getResources().getColorStateList(R.color.lightBlue));
        image.animate().scaleX(2.3F).scaleY(2.3F).duration = 100
        var handler: Handler = Handler()
        handler.postDelayed(Runnable {
            view.setBackgroundTintList(this.getResources().getColorStateList(R.color.white));
            image.animate().scaleX(1F).scaleY(1F).duration = 100
        }, 100)
    }

    fun showStudentInstructor(view: View) {
        animate(studentInstructors, studentInstructorsImage)
        var intent: Intent = Intent(this, StudentOrInstructorListActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun showStudentChatPanel(view: View) {
        animate(studentChatPanel, studentChatPanelImage)
    }
    fun showStudentFAQ(view: View) {
        animate(studentFAQ, studentFAQImage)
        var intent: Intent = Intent(this, FAQActivity::class.java)
        startActivity(intent)
    }
    fun showStudentProfile(view: View) {
        animate(studentEditProfile, studentEditProfileImage)
        var intent: Intent = Intent(this, EditProfileActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun showCalendar(view: View) {
        animate(calendar, calendarImage)
        var intent: Intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }
    fun showStudentAllInstructor(view: View) {
        animate(studentAllInstructor, studentAllInstructorImage)
        var intent: Intent = Intent(this, StudentOrInstructorListActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun showStudentAbout(view: View) {
        animate(studentAbout, studentAboutImage)
        var intent: Intent = Intent(this, AboutPreduliveActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun studentLogout(view: View) {
        animate(studentLogout, studentLogoutImage)
        auth.signOut()
        finish()
    }
}