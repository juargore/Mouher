package com.glass.mouher.ui.mall

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.glass.mouher.R

class MainActivityMall : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_mall)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.findViewById<RelativeLayout>(R.id.layoutCart).visibility = View.INVISIBLE
        setSupportActionBar(toolbar)

        val drawerFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_navigation_drawer_mall) as MainFragmentSideMall

        drawerFragment.setUpDrawer(
            R.id.fragment_navigation_drawer_mall,
            findViewById(R.id.drawer_layout_mall),
            toolbar
        )
    }
}