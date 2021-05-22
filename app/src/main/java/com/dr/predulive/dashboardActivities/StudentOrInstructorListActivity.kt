package com.dr.predulive.dashboardActivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dr.apigallery2.Recycler.RecyclerAdapter
import com.dr.predulive.R
import com.dr.predulive.dashboardActivities.Recycler.IndividualData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class StudentOrInstructorListActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    private lateinit var listView: ListView
    private lateinit var list: ArrayList<IndividualData>

    lateinit var recyclerView: RecyclerView
    var recyclerAdapter: RecyclerView.Adapter<*>? = null
    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var heading: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_or_instructor_list)

        mAuth = FirebaseAuth.getInstance()
        list = mutableListOf<String>() as ArrayList<IndividualData>

        recyclerView = findViewById<View>(R.id.searchRecyclerView) as RecyclerView
        recyclerView.setHasFixedSize(false)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerAdapter = RecyclerAdapter(this, list)
        recyclerView.adapter = recyclerAdapter

        heading = findViewById<View>(R.id.heading) as TextView


        var intent: Intent = intent
        var who: String? = intent.getStringExtra("who")

        if(who.equals("STUDENT")) {
            heading.text = "Instructors"
            who = "INSTRUCTOR"
        } else {
            heading.text = "Students"
            who = "STUDENT"
        }

        if (who != null) {
            FirebaseDatabase.getInstance().reference.addChildEventListener(object:
                ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    if(snapshot.child("WHO").value.toString() == who) {
                        var name:String =snapshot.child("NAME").value.toString()
                        var email:String =snapshot.child("EMAIL").value.toString()
                        var about:String =snapshot.child("ABOUT").value.toString()
                        var imageUrl:String = snapshot.child("PROFILE_URL").value.toString()

                        list.add(IndividualData(name, email, about, imageUrl))
                        (recyclerAdapter as RecyclerAdapter).notifyDataSetChanged()
                    }



                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    TODO("Not yet implemented")
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }



    }
}