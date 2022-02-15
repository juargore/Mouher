package com.glass.mouher.ui.store

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.FragmentManager
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityMainStoreBinding
import com.glass.mouher.extensions.startActivityNoAnimation
import com.glass.mouher.shared.General
import com.glass.mouher.shared.General.getCurrentStoreName
import com.glass.mouher.shared.General.saveMustRefreshMenuMall
import com.glass.mouher.shared.General.saveMustRefreshStore
import com.glass.mouher.ui.base.BaseActivity
import com.glass.mouher.ui.cart.CartActivity
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.MainActivityMall
import com.glass.mouher.ui.menu.MenuFragment
import com.glass.mouher.ui.menu.MenuViewModel
import com.glass.mouher.ui.store.home.HomeStoreFragment
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import org.koin.android.viewmodel.ext.android.viewModel


class MainStoreActivity : BaseActivity() {

    private val viewModel: MainStoreViewModel by viewModel()
    private lateinit var binding: ActivityMainStoreBinding

    private lateinit var drawerFragment: MenuFragment
    private lateinit var toolbar: Toolbar

    companion object{
        var storeId: Int = 1 // 1 is default value
    }

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.openCart -> {
                val intent = Intent(this, CartActivity::class.java)
                startActivityNoAnimation(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_store)
        binding.viewModel = viewModel
        binding.view = this

        supportActionBar?.setDisplayShowTitleEnabled(false)

        // set custom toolbar with menu | logo | cart
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // set navigation drawer on left side
        drawerFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_navigation_drawer_store) as MenuFragment

        // open first screen on framelayout
        drawerFragment.setUpDrawer(
            R.id.fragment_navigation_drawer_store,
            findViewById(R.id.drawer_layout_store),
            toolbar,
            "STORE"
        )

        intent?.extras?.let{
            storeId = it.getInt("storeId")
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)

        if(General.getMustRefreshMenuStore()){
            // open first screen on framelayout
            drawerFragment.setUpDrawer(
                R.id.fragment_navigation_drawer_store,
                findViewById(R.id.drawer_layout_store),
                toolbar,
                "STORE"
            )

            General.saveMustRefreshMenuStore(false)
        }

        if(General.getMusthRefreshStore()){
            saveMustRefreshStore(false)
            finish()
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        // Clean the backstack of fragments when clicking on store logo
        binding.imgLogoStore.setOnClickListener {
            supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        // Restart whole App when clicking on mall logo
        binding.imgLogoMall.setOnClickListener {
            if(viewModel.totalProducts.toInt() > 0) {
                askForExitWhenCartNotEmpty(restartApp = true)
            } else {
                restartWholeApp()
            }
        }
    }

    fun refreshActivityFromFragment(){
        saveMustRefreshMenuMall(true)

        finish()
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    override fun onBackPressed() {
        val lastFragment = supportFragmentManager.fragments.lastOrNull()
        MenuViewModel.source = "MALL"

        if(lastFragment is HomeStoreFragment){
            if(viewModel.totalProducts.toInt() > 0) {
                askForExitWhenCartNotEmpty(restartApp = false)
            } else {
                super.onBackPressed()
            }
        }else{
            super.onBackPressed()
        }
    }

    @Suppress("DEPRECATION")
    private fun askForExitWhenCartNotEmpty(restartApp: Boolean) {
        alert(title = "", message = resources.getString(R.string.cart_confirm_exit_store, getCurrentStoreName())){
            yesButton {
                viewModel.clearProductsFromCart()
                it.dismiss()

                Handler().postDelayed({
                    if(restartApp) {
                        restartWholeApp()
                    } else {
                        super.onBackPressed()
                    }
                }, 500)
            }
            noButton {
                it.dismiss()
            }
        }.show()
    }

    private fun restartWholeApp() {
        val intent = Intent(this, MainActivityMall::class.java)
        startActivity(intent)
        finishAffinity()
    }
}