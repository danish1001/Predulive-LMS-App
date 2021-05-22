package com.dr.predulive.dashboardActivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import com.dr.predulive.R

class FAQActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_f_a_q)

        var webView  = findViewById<View>(R.id.webViewFAQ) as WebView

        webView.loadUrl("https://predulive.com/project.php")

    }
}