package com.ocean.mouher.ui.mall

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.mouher.R
import com.ocean.mouher.extensions.startActivityNoAnimation
import com.ocean.mouher.shared.General
import com.ocean.mouher.shared.General.getMustRefreshMenuMall
import com.ocean.mouher.shared.General.saveMustRefreshMenuMall
import com.ocean.mouher.ui.base.BaseActivity
import com.ocean.mouher.ui.mall.home.HomeMallFragment
import com.ocean.mouher.ui.mall.home.stores.StoresFragment
import com.ocean.mouher.ui.menu.MenuFragment
import com.ocean.mouher.ui.search.SearchActivity

class MainActivityMall : BaseActivity() {

    private lateinit var drawerFragment: MenuFragment

    companion object{
        @SuppressLint("StaticFieldLeak")
        var toolbar: Toolbar? = null

        fun setLogoOnToolbar(urlImage: String){
            val image = toolbar?.findViewById<ImageView>(R.id.imageLogo)

            image?.let {
                Glide.with(it.context)
                        .load(urlImage)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_blur)
                        .into(it)
            }
        }

        fun showHideCartIcon(show: Boolean) {
            toolbar?.findViewById<ImageView>(R.id.imgCartToolbar)?.let {
                if(show) it.visibility = View.VISIBLE
                else it.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_mall)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set custom toolbar with menu | logo | search | cart
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)

        // set navigation drawer on left side
        drawerFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_navigation_drawer_mall) as MenuFragment

        // open first screen on framelayout
        drawerFragment.setUpDrawer(
            R.id.fragment_navigation_drawer_mall,
            findViewById(R.id.drawer_layout_mall),
            toolbar,
            "MALL"
        )
    }


    override fun onResume() {
        super.onResume()

        if (getMustRefreshMenuMall()) {
            // open first screen on framelayout
            drawerFragment.setUpDrawer(
                R.id.fragment_navigation_drawer_mall,
                findViewById(R.id.drawer_layout_mall),
                toolbar,
                "MALL"
            )

            saveMustRefreshMenuMall(false)
        }

        // Go to 'zonas comerciales' at bottom screen on fragment
        toolbar?.findViewById<ImageView>(R.id.imgCartToolbar)!!.setOnClickListener {
            val homeMallFragment = supportFragmentManager.findFragmentByTag("MALL_FRAGMENT") as HomeMallFragment?
            homeMallFragment?.scrollToBottomScreen()
        }
    }

    override fun onStart() {
        super.onStart()

        // Clean the backstack of fragments when clicking on middle logo -Mouher Market-
        toolbar?.findViewById<ImageView>(R.id.imageLogo)?.setOnClickListener {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        // Go to 'search screen'
        toolbar?.findViewById<ImageView>(R.id.imgSearchToolbar)?.setOnClickListener {
            startActivityNoAnimation(intent = Intent(this, SearchActivity::class.java))
        }
    }

    fun refreshActivityFromFragment() {
        finish()
        startActivity(intent)
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        val lastFragment = supportFragmentManager.fragments.lastOrNull()

        if (lastFragment is StoresFragment) {
            General.saveComesFromStores(true)
        }

        super.onBackPressed()
    }
}
