package com.innotech.healapplication

import ImagePagerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class HomeActivity : BaseActivity() {

    private lateinit var loginButton: Button
    private lateinit var profileTextView: TextView
    private lateinit var userImageView: ImageView
    private lateinit var eventsButton: Button
    private lateinit var settingsButton: Button
    private lateinit var calendarButton: Button
    private lateinit var aboutButton: Button
    private lateinit var logoutButton: Button
    private lateinit var donateBtn: Button
    private lateinit var viewPager: ViewPager2
    private lateinit var auth: FirebaseAuth

    private val images = listOf(R.drawable.director1, R.drawable.director2, R.drawable.director3)
    private val descriptions = listOf(
        "Divesh at the helm of this nonprofit organization, steering it, managing its operations, and carrying out its mission according with the goal of doing good in the world. Unfortunately, he doesn't like photos. ",
        "Akshay, the visionary Deputy Director, brings innovative solutions and strategic insights to HEAL, ensuring our initiatives are impactful and sustainable. His dedication to positive change drives the organization forward.",
        "Kamal, our dedicated Secretary, oversees essential operations and maintains the heartbeat of our mission. With a meticulous eye and a compassionate approach, Kamal supports HEAL’s objectives with precision and heart."

    )
    private val titles = listOf(
        "Divesh - Director",
        "Akshay - Deputy Director",
        "Kamal - Secretary"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_home, findViewById(R.id.content_frame))

        auth = FirebaseAuth.getInstance()

        loginButton = findViewById(R.id.loginButton)
        profileTextView = findViewById(R.id.profileTextView)
        userImageView = findViewById(R.id.userImageView)
        eventsButton = findViewById(R.id.eventsButton)
        settingsButton = findViewById(R.id.settingsButton)
        calendarButton = findViewById(R.id.calendarButton)
        aboutButton = findViewById(R.id.aboutButton)
        logoutButton = findViewById(R.id.logoutButton)
        donateBtn = findViewById(R.id.donateButton)
        viewPager = findViewById(R.id.viewPager)

        // Set up ViewPager2 with ImagePagerAdapter
        val adapter = ImagePagerAdapter(images, titles, descriptions)
        viewPager.adapter = adapter

        // Checking if user is logged in
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            loginButton.visibility = View.GONE
            profileTextView.visibility = View.VISIBLE
            userImageView.setImageResource(R.drawable.placeholder)
            userImageView.visibility = View.VISIBLE
            logoutButton.visibility = View.VISIBLE

            profileTextView.text = "My Profile"
        } else {
            loginButton.visibility = View.VISIBLE
            profileTextView.visibility = View.GONE
            userImageView.visibility = View.GONE
            logoutButton.visibility = View.GONE
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        logoutButton.setOnClickListener {
            auth.signOut()
            loginButton.visibility = View.VISIBLE
            profileTextView.visibility = View.GONE
            userImageView.visibility = View.GONE
            logoutButton.visibility = View.GONE

            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        eventsButton.setOnClickListener {
            startActivity(Intent(this, EventsActivity::class.java))
        }

        settingsButton.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        calendarButton.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
        }

        aboutButton.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        donateBtn.setOnClickListener {
            startActivity(Intent(this, DonationActivity::class.java))
        }

        profileTextView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        userImageView.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
