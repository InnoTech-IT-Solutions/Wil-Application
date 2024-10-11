package com.example.heal_application


import android.os.Bundle

class AdminDashboard : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_admin_dashboard, findViewById(R.id.content_frame))
    }
}