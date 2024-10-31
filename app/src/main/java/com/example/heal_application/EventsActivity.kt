package com.example.heal_application

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

private lateinit var homeButton: Button
private lateinit var calendarButton: Button
class EventsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_events, findViewById(R.id.content_frame))


        homeButton = findViewById(R.id.homeButton)
        calendarButton = findViewById(R.id.calendarButton)

        homeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()  // Close EventsActivity
        }
        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
            finish()  // Close EventsActivity
        }
    }
}