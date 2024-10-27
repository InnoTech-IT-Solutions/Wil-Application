package com.example.heal_application

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import android.util.Log

open class BaseActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var isAdmin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        drawerLayout = findViewById(R.id.drawerLayout)
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.darkgreen))

        // Set up the toolbar
        setSupportActionBar(toolbar)

        // Create a toggle and tie it to the drawer layout
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        // Attach the toggle to the DrawerLayout
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Setup navigation item selection
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_calendar -> {
                    startActivity(Intent(this, CalendarActivity::class.java))
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, SettingsActivity::class.java))
                    true
                }
                R.id.nav_admin -> {
                    if (isAdmin) {
                        startActivity(Intent(this, AdminDashboard::class.java))
                    } else {
                        Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                    }
                    true
                }
                R.id.nav_logout -> {
                    // Handle logout logic here
                    auth.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }

        // By default, hide the Admin Dashboard item
        val adminMenuItem = navigationView.menu.findItem(R.id.nav_admin)
        adminMenuItem.isVisible = false

        // Check user role if logged in
        val currentUser = auth.currentUser
        if (currentUser != null) {
            checkUserRole(currentUser)
        } else {
            // Hide admin menu if no user is logged in
            adminMenuItem.isVisible = false
        }
    }

    private fun checkUserRole(user: FirebaseUser) {
        val emailKey = user.email?.replace(".", "_")?.replace(",", "_") ?: return

        // Listen for the user role in Firebase
        database.child("users").child(emailKey).child("role")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val role = snapshot.getValue(String::class.java)
                    isAdmin = (role == "admin") // Check if the user is an admin
                    Log.d("BaseActivity", "Role for $emailKey: $role")
                    updateNavigationMenu() // Always update the menu after role check
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("BaseActivity", "Error retrieving role: ${error.message}")
                    isAdmin = false
                    updateNavigationMenu()
                }
            })
    }

    private fun updateNavigationMenu() {
        val navigationView: NavigationView = findViewById(R.id.navigationView)
        val adminMenuItem = navigationView.menu.findItem(R.id.nav_admin)

        // Only make Admin Dashboard item visible for admins
        if (isAdmin) {
            adminMenuItem.isVisible = true
            adminMenuItem.title = "Admin Dashboard"
        } else {
            adminMenuItem.isVisible = false // Hide for non-admin users
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle the hamburger icon clicks
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

