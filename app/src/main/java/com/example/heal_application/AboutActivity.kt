package com.example.heal_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.webkit.WebView
import android.webkit.WebViewClient

private lateinit var homeButton: Button
class AboutActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_about, findViewById(R.id.content_frame))

        val webView: WebView = findViewById(R.id.mapView)
        homeButton = findViewById(R.id.homeButton)


        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        // Load the OSM map with the desired coordinates
        val latitude = -29.8291774  // Replace with your NGO's latitude
        val longitude = 30.9982982 // Replace with your NGO's longitude
        val zoomLevel = 20  // Adjust as needed for more/less zoom


        val mapUrl = "https://www.openstreetmap.org/?mlat=$latitude&mlon=$longitude#map=$zoomLevel/$latitude/$longitude"
        webView.loadUrl(mapUrl)

        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close EventsActivity
        }
    }


}