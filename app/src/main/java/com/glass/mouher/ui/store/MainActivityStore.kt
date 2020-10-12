package com.glass.mouher.ui.store

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.glass.mouher.R
import com.glass.mouher.ui.menu.MenuFragment

class MainActivityStore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_store)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set custom toolbar with menu | logo | cart
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // set navigation drawer on left side
        val drawerFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_navigation_drawer_store) as MenuFragment

        // open first screen on framelayout
        drawerFragment.setUpDrawer(
            R.id.fragment_navigation_drawer_store,
            findViewById(R.id.drawer_layout_store),
            toolbar,
            "STORE"
        )
    }
}