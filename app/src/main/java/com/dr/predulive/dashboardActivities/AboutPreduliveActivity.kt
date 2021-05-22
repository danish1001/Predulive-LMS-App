package com.dr.predulive.dashboardActivities

import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.dr.predulive.R


class AboutPreduliveActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_predulive)


        var webView  = findViewById<View>(R.id.webViewAbout) as WebView

        webView.loadUrl("https://predulive.com/about.php")


    }


}