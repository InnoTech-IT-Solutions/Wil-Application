package com.innotech.healapplication


import android.content.Intent
import android.os.Bundle
import android.widget.Button


class AdminDashboard : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_admin_dashboard, findViewById(R.id.content_frame))

        val addEventButton: Button = findViewById(R.id.addEventButton)
        addEventButton.setOnClickListener {
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }
    }
}
