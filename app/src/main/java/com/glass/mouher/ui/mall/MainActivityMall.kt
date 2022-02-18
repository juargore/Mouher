package com.glass.mouher.ui.mall

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.mouher.R
import com.glass.mouher.extensions.startActivityNoAnimation
import com.glass.mouher.shared.General
import com.glass.mouher.shared.General.getMustRefreshMenuMall
import com.glass.mouher.shared.General.saveMustRefreshMenuMall
import com.glass.mouher.ui.base.BaseActivity
import com.glass.mouher.ui.mall.home.stores.StoresFragment
import com.glass.mouher.ui.menu.MenuFragment
import com.glass.mouher.ui.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home_mall.*

class MainActivityMall : BaseActivity() {

    private lateinit var drawerFragment: MenuFragment

    companion object{
        @SuppressLint("StaticFieldLeak")
        var toolbar: Toolbar? = null

        fun setLogoOnToolbar(urlImage: String){
            val image = toolbar?.findViewById<ImageView>(R.id.imageLogo)

            image?.let{
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

        if(getMustRefreshMenuMall()){
            // open first screen on framelayout
            drawerFragment.setUpDrawer(
                R.id.fragment_navigation_drawer_mall,
                findViewById(R.id.drawer_layout_mall),
                toolbar,
                "MALL"
            )

            saveMustRefreshMenuMall(false)
        }
    }

    override fun onStart() {
        super.onStart()

        // Clean the backstack of fragments when clicking on middle logo -Mouher Market-
        toolbar?.findViewById<ImageView>(R.id.imageLogo)?.setOnClickListener {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        // Go to 'zonas comerciales' at bottom screen on fragment
        toolbar?.findViewById<ImageView>(R.id.imgCartToolbar)?.setOnClickListener {
            with(nestedScrollView) {
                post { fullScroll(View.FOCUS_DOWN) }
            }
        }

        // Go to 'search screen'
        toolbar?.findViewById<ImageView>(R.id.imgSearchToolbar)?.setOnClickListener {
            startActivityNoAnimation(intent = Intent(this, SearchActivity::class.java))
        }
    }

    fun refreshActivityFromFragment(){
        finish()
        startActivity(intent)
    }

    override fun onBackPressed() {
        val lastFragment = supportFragmentManager.fragments.lastOrNull()

        if(lastFragment is StoresFragment){
            General.saveComesFromStores(true)
        }

        super.onBackPressed()
    }
}