package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar

class CalendarActivity : BaseActivity() {

    private lateinit var calendarView: MaterialCalendarView
    private lateinit var eventsListView: ListView
    private lateinit var database: DatabaseReference
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private val eventsList = ArrayList<String>()
    private lateinit var eventsAdapter: ArrayAdapter<String>
    private val dateFormatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutInflater.inflate(R.layout.activity_calendar, findViewById(R.id.content_frame))

        // Initialize views
        calendarView = findViewById(R.id.materialCalendarView)
        eventsListView = findViewById(R.id.eventsListView)

        // Setup Firebase reference
        database = FirebaseDatabase.getInstance().reference

        // Setup ListView Adapter
        eventsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, eventsList)
        eventsListView.adapter = eventsAdapter

        // Setup Toolbar and DrawerLayout
        setupDrawerLayout()

        // Load and highlight event dates
        highlightEventDates()

        // Set listener for date selection
        calendarView.setOnDateChangedListener { _, date, _ ->
            val selectedDate = dateFormatter.format(date.date)
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
        toggle.syncState()

        // Navigation view setup
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_calendar -> true
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.nav_logout -> {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    // Highlight dates that have events
    private fun highlightEventDates() {
        val eventDates = mutableListOf<CalendarDay>()

        database.child("events").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (dateSnapshot in snapshot.children) {
                        val date = dateSnapshot.key ?: continue
                        val parsedDate = dateFormatter.parse(date)
                        parsedDate?.let {
                            val calendar = Calendar.getInstance().apply { time = parsedDate }
                            val calendarDay = CalendarDay.from(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),  // Keep zero-indexed
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            eventDates.add(calendarDay)
                        }
                    }
                    // Apply the decorator for event dates
                    calendarView.addDecorator(EventDecorator(Color.BLUE, eventDates))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CalendarActivity", "Error loading event dates: ${error.message}")
                Toast.makeText(this@CalendarActivity, "Error loading event dates", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Load events for the selected date
    private fun loadEventsForDate(date: String) {
        eventsList.clear()

        database.child("events").child(date).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (eventSnapshot in snapshot.children) {
                        val title = eventSnapshot.child("title").getValue(String::class.java) ?: "Untitled"
                        val description = eventSnapshot.child("description").getValue(String::class.java) ?: "No description"
                        val eventText = "$title - $description"
                        eventsList.add(eventText)
                    }
                    eventsAdapter.notifyDataSetChanged()
                } else {
                    // No events for the selected date
                    eventsList.add("No events on this date")
                    eventsAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CalendarActivity", "Error loading events: ${error.message}")
                Toast.makeText(this@CalendarActivity, "Error loading events", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Inner class to decorate event dates
    private inner class EventDecorator(color: Int, dates: Collection<CalendarDay>) : DayViewDecorator {
        private val highlightDrawable = ColorDrawable(color)
        private val datesSet: HashSet<CalendarDay> = HashSet(dates)

        override fun shouldDecorate(day: CalendarDay): Boolean {
            return datesSet.contains(day)
        }

        override fun decorate(view: DayViewFacade) {
            view.setBackgroundDrawable(highlightDrawable)
        }
    }
}
