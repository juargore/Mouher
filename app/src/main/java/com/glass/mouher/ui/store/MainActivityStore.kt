package com.glass.mouher.ui.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.glass.mouher.R

class MainActivityStore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_store)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayShowTitleEnabled(false)

        val drawerFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_navigation_drawer) as MainFragmentSideStore

        drawerFragment.setUpDrawer(
            R.id.fragment_navigation_drawer,
            findViewById(R.id.drawer_layout),
            toolbar
        )
    }
}