package com.glass.mouher.ui.store

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityMainStoreBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.menu.MenuFragment
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.ui.cart.CartActivity

class MainStoreActivity : AppCompatActivity() {

    private val viewModel: MainStoreViewModel by viewModel()
    private lateinit var binding: ActivityMainStoreBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.openCart -> {
                    startActivity(Intent(this, CartActivity::class.java))
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main_store)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_store)

        binding.viewModel = viewModel
        binding.view = this

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

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}