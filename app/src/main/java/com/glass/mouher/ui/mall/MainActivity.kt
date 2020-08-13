package com.glass.mouher.ui.mall

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.glass.domain.common.or
import com.glass.domain.usecases.IUITabBarUseCase.Tabs
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityMainBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.android.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var navHostFragmentsIds: Map<Tabs, Int> = mapOf(
        Tabs.PROFILE to R.id.profileSection,
        Tabs.HISTORY to R.id.historySection,
        Tabs.CATEGORIES to R.id.categoriesSection,
        Tabs.USER_LIST to R.id.userListSection
    )

    private val menuItemIdToTabs = mapOf(
        R.id.mainMenuProfile to Tabs.PROFILE,
        R.id.mainMenuHistory to Tabs.HISTORY,
        R.id.mainMenuHome to Tabs.CATEGORIES,
        R.id.mainMenuMenu to Tabs.USER_LIST
    )

    /**
     * @property tabsToMenuItemId inverse of menuItemIdToTabs map [Tabs] enum values to menuItemId
     * of bottom navigation view.
     */
    private val tabsToMenuItemId
            = menuItemIdToTabs.entries.associate { Pair(it.value, it.key) }

    /**
     * Get the Tabs value corresponding to menuItemId provided.
     * @param menuItemId the menuItemId of online bottom navigation bar.
     * @return the corresponding Tabs value.
     */
    private fun menuItemIdToTab(menuItemId: Int)
            = menuItemIdToTabs[menuItemId] ?: Tabs.CATEGORIES

    /**
     * Get the menuItemId corresponding to Tabs value provided.
     * @param tab a Tabs value
     * @return the corresponding menuItemId.
     */
    private fun tabToMenuItemId(tab: Tabs): Int = tabsToMenuItemId[tab] ?: R.id.mainMenuHome


    private var currentController: NavController? = null
    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding


    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.currentTab -> onTabChanged()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewModel = viewModel
        binding.view = this

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        binding.mainActivityBottomNavigation.setOnNavigationItemSelectedListener(this)
        viewModel.onResume(onPropertyChangedCallback)
        viewModel.onTabSelected(Tabs.CATEGORIES)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
        onTabChanged()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    /**
     * Apply on tab changed.
     */
    private fun onTabChanged() {
        val itemIdToSelect = tabToMenuItemId(viewModel.currentTab)
        if (binding.mainActivityBottomNavigation.selectedItemId != itemIdToSelect) {
            binding.mainActivityBottomNavigation.selectedItemId = itemIdToSelect
        }

        currentController = findNavController(navHostFragmentsIds[viewModel.currentTab]?: R.id.categoriesSection)

        val transaction = supportFragmentManager.beginTransaction()
        navHostFragmentsIds.entries.forEach { (tab: Tabs, fragmentId: Int) ->
            val navHost = supportFragmentManager.findFragmentById(fragmentId)
            if (navHost != null) {
                if (tab == viewModel.currentTab) {
                    transaction.show(navHost).setMaxLifecycle(navHost, Lifecycle.State.RESUMED)
                } else {
                    transaction.hide(navHost).setMaxLifecycle(navHost, Lifecycle.State.STARTED)
                }
            }
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        currentController?.let {
            if (it.popBackStack()) {
                viewModel.onBackPressed()
            } else {
                finish()
            }
        }.or { finish() }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        viewModel.onTabSelected(menuItemIdToTab(item.itemId))
        return true
    }
}