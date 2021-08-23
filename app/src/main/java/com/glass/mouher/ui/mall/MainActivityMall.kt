package com.glass.mouher.ui.mall

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.mouher.R
import com.glass.mouher.ui.menu.MenuFragment

class MainActivityMall : AppCompatActivity() {

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
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_mall)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set custom toolbar with menu | logo | no-cart
        toolbar = findViewById(R.id.toolbar)
        toolbar?.findViewById<RelativeLayout>(R.id.layoutCart)?.visibility = View.INVISIBLE

        setSupportActionBar(toolbar)

        // set navigation drawer on left side
        val drawerFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_navigation_drawer_mall) as MenuFragment

        // open first screen on framelayout
        drawerFragment.setUpDrawer(
            R.id.fragment_navigation_drawer_mall,
            findViewById(R.id.drawer_layout_mall),
            toolbar,
            "MALL"
        )
    }

    override fun onStart() {
        super.onStart()

        // Clean the backstack of fragments when clicking on middle logo -Mouher Market-
        toolbar?.findViewById<ImageView>(R.id.imageLogo)?.setOnClickListener {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}