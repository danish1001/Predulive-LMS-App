package com.dr.predulive.dashboardActivities

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.dr.predulive.R


class CalendarActivity : AppCompatActivity() {

    var calender: CalendarView? = null
    var date_view: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        calender = findViewById<View>(R.id.calender) as CalendarView
        date_view = findViewById<View>(R.id.date_view) as TextView

        // Add Listener in calendar

        // Add Listener in calendar
        calender!!
            .setOnDateChangeListener(
                OnDateChangeListener { view, year, month, dayOfMonth ->
                    // In this Listener have one method
                    // and in this method we will
                    // get the value of DAYS, MONTH, YEARS
                    // Store the value of date with
                    // format in String type Variable
                    // Add 1 in month because month
                    // index is start with 0
                    val Date = (dayOfMonth.toString() + "-"
                            + (month + 1) + "-" + year)

                    // set this date in TextView for Display
                    date_view!!.text = Date
                })
    }
}