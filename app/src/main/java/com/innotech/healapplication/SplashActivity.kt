package com.innotech.healapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper

class SplashActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            // Start the next activity
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish() // Finish SplashActivity so it's not in the back stack

        }, splashTimeOut)
    }
}
