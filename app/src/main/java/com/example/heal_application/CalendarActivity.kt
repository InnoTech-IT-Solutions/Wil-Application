package com.example.heal_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class CalendarActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_calendar, findViewById(R.id.content_frame))
    }

}