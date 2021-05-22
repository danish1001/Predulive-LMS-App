package com.dr.predulive.instructorDirectory

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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

class InstructorDashboardActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var instructorAssignment: androidx.cardview.widget.CardView
    private lateinit var homework: ImageView
    private lateinit var instructorStudents: androidx.cardview.widget.CardView
    private lateinit var instructorStudentImage: ImageView
    private lateinit var instructorChatPanel: androidx.cardview.widget.CardView
    private lateinit var instructorChatPanelImage: ImageView
    private lateinit var instructorFAQ: androidx.cardview.widget.CardView
    private lateinit var instructorFAQImage: ImageView
    private lateinit var instructorEditProfile: androidx.cardview.widget.CardView
    private lateinit var instructorEditProfileImage: ImageView
    private lateinit var calendar: androidx.cardview.widget.CardView
    private lateinit var calendarImage: ImageView
    private lateinit var instructorAllStudents: androidx.cardview.widget.CardView
    private lateinit var instructorAllStudentsImage: ImageView
    private lateinit var instructorAbout: androidx.cardview.widget.CardView
    private lateinit var instructorAboutImage: ImageView
    private lateinit var instructorLogout: androidx.cardview.widget.CardView
    private lateinit var instructorLogoutImage: ImageView

    private lateinit var instructorDashboardProfileImage: ImageView

    private lateinit var instructorDashboardName: TextView
    private lateinit var instructorDashboardScrollView: ScrollView

    private var who: String = "INSTRUCTOR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instructor_dashboard)

        // Initialize Firebase Auth
        auth = Firebase.auth

        instructorAssignment = findViewById<View>(R.id.instructorAssignment) as androidx.cardview.widget.CardView
        homework = findViewById<View>(R.id.homework) as ImageView
        instructorStudents = findViewById<View>(R.id.instructorStudents) as androidx.cardview.widget.CardView
        instructorStudentImage = findViewById<View>(R.id.instructorStudentImage) as ImageView
        instructorChatPanel = findViewById<View>(R.id.instructorChatPanel) as androidx.cardview.widget.CardView
        instructorChatPanelImage = findViewById<View>(R.id.instructorChatPanelImage) as ImageView
        instructorFAQ = findViewById<View>(R.id.instructorFAQ) as androidx.cardview.widget.CardView
        instructorFAQImage = findViewById<View>(R.id.instructorFAQImage) as ImageView
        instructorEditProfile = findViewById<View>(R.id.instructorEditProfile) as androidx.cardview.widget.CardView
        instructorEditProfileImage = findViewById<View>(R.id.instructorEditProfileImage) as ImageView
        calendar = findViewById<View>(R.id.calendar) as androidx.cardview.widget.CardView
        calendarImage = findViewById<View>(R.id.calendarImage) as ImageView
        instructorAllStudents = findViewById<View>(R.id.instructorAllStudents) as androidx.cardview.widget.CardView
        instructorAllStudentsImage = findViewById<View>(R.id.instructorAllStudentsImage) as ImageView
        instructorAbout = findViewById<View>(R.id.instructorAbout) as androidx.cardview.widget.CardView
        instructorAboutImage = findViewById<View>(R.id.instructorAboutImage) as ImageView
        instructorLogout = findViewById<View>(R.id.instructorLogout) as androidx.cardview.widget.CardView
        instructorLogoutImage = findViewById<View>(R.id.instructorLogoutImage) as ImageView

        instructorDashboardProfileImage = findViewById<View>(R.id.instructorDashboardProfileImage) as ImageView
        instructorDashboardName = findViewById<TextView>(R.id.instructorDashboardName) as TextView
        instructorDashboardScrollView = findViewById<View>(R.id.instructorDashboardScrollView) as ScrollView

        instructorDashboardScrollView.setOnScrollChangeListener(object : View.OnScrollChangeListener{
            override fun onScrollChange(
                    v: View?,
                    scrollX: Int,
                    scrollY: Int,
                    oldScrollX: Int,
                    oldScrollY: Int
            ) {
                if(oldScrollY > 150) {
                    instructorDashboardScrollView.setBackgroundColor(Color.WHITE)
                } else {
                    instructorDashboardScrollView.setBackgroundResource(R.drawable.background_custom)
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
                            .into(instructorDashboardProfileImage)
                }
        FirebaseDatabase.getInstance().reference.child(auth.uid.toString()).get().addOnSuccessListener {
            instructorDashboardName.text = it.child("NAME").value.toString()
        }
    }

    fun instructorTodaysAssignment(view: View) {
        Toast.makeText(this, "No Assignment!", Toast.LENGTH_SHORT).show()
        animate(instructorAssignment, homework)
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

    fun showInstructorStudents(view: View) {
        animate(instructorStudents, instructorStudentImage)
        var intent: Intent = Intent(this, StudentOrInstructorListActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun showInstructorChatPanel(view: View) {
        animate(instructorChatPanel, instructorChatPanelImage)
    }
    fun showInstructorFAQ(view: View) {
        animate(instructorFAQ, instructorFAQImage)
        var intent: Intent = Intent(this, FAQActivity::class.java)
        startActivity(intent)
    }
    fun showInstructorProfile(view: View) {
        animate(instructorEditProfile, instructorEditProfileImage)
        var intent: Intent = Intent(this, EditProfileActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun showCalendar(view: View) {
        animate(calendar, calendarImage)
        var intent: Intent = Intent(this, CalendarActivity::class.java)
        startActivity(intent)
    }
    fun showInstructorAllStudents(view: View) {
        animate(instructorAllStudents, instructorAllStudentsImage)
        var intent: Intent = Intent(this, StudentOrInstructorListActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun showinstructorAbout(view: View) {
        animate(instructorAbout, instructorAboutImage)
        var intent: Intent = Intent(this, AboutPreduliveActivity::class.java)
        intent.putExtra("who", who)
        startActivity(intent)
    }
    fun instructorLogout(view: View) {
        animate(instructorLogout, instructorLogoutImage)
        auth.signOut()
        finish()
    }
}