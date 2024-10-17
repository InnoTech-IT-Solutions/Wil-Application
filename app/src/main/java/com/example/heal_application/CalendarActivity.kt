package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.CalendarView
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class CalendarActivity : BaseActivity() {

    private lateinit var calendarView: CalendarView
    private lateinit var eventsListView: ListView
    private lateinit var database: DatabaseReference
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private val eventsList = ArrayList<String>()
    private lateinit var eventsAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        // Initialize views
        calendarView = findViewById(R.id.calendarView)
        eventsListView = findViewById(R.id.eventsListView)

        // Setup Firebase reference
        database = FirebaseDatabase.getInstance().reference

        // Setup ListView Adapter
        eventsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventsList)
        eventsListView.adapter = eventsAdapter

        // Setup Toolbar and DrawerLayout
        setupDrawerLayout()

        // Listen for date changes on the calendar
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth-${month + 1}-$year"
            loadEventsForDate(selectedDate)
        }
    }

    // Setup DrawerLayout and Toolbar for navigation
    private fun setupDrawerLayout() {
        drawerLayout = findViewById(R.id.drawerLayout)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Show the hamburger icon
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        // Set up the toggle for the navigation drawer
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState() // Ensure the hamburger icon is synced

        // Navigation view setup
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    // Navigate to Home Activity
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_calendar -> {
                    // Stay on the same page
                    true
                }
                R.id.nav_settings -> {
                    // Navigate to Settings Activity
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.nav_logout -> {
                    // Handle logout
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    // Load events for the selected date from Firebase
    private fun loadEventsForDate(date: String) {
        eventsList.clear()

        database.child("events").child(date).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eventSnapshot in snapshot.children) {
                        val event = eventSnapshot.getValue(String::class.java)
                        event?.let { eventsList.add(it) }
                    }
                    eventsAdapter.notifyDataSetChanged()
                } else {
                    // No events for the selected date
                    eventsList.add("No events on this date")
                    eventsAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle possible errors
                Log.e("CalendarActivity", "Error loading events: ${error.message}")
                Toast.makeText(this@CalendarActivity, "Error loading events", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
