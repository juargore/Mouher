package com.glass.mouher.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentMenuBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.home.HomeFragment
import com.glass.mouher.ui.store.home.HomeStoreFragment
import org.koin.android.viewmodel.ext.android.viewModel

class MenuFragment: Fragment() {

    private val viewModel: MenuViewModel by viewModel()
    private lateinit var binding: FragmentMenuBinding

    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var containerView: View? = null
    private var source = ""

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvMenu.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun openFragment(position: Int) {
        if(source == "MALL"){
            when (position) {
                0 -> removeAllFragment(HomeFragment())
            }
        } else{
            when (position) {
                0 -> removeAllFragment(HomeStoreFragment())
            }
        }
    }

    private fun removeAllFragment(replaceFragment: Fragment) {
        val manager = requireActivity().supportFragmentManager
        val ft = manager.beginTransaction()
        manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        when(source){
            "MALL" -> ft.replace(R.id.container_body_mall, replaceFragment)
            else -> ft.replace(R.id.container_body, replaceFragment)
        }

        ft.commitAllowingStateLoss()
    }

    fun setUpDrawer(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar, source: String) {
        this.source = source
        viewModel.initialize(source)

        containerView = requireActivity().findViewById(fragmentId)
        mDrawerLayout = drawerLayout

        mDrawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }

        mDrawerLayout?.let{
            it.addDrawerListener(mDrawerToggle as ActionBarDrawerToggle)
            it.post { mDrawerToggle!!.syncState() }
        }

        // hack to disable slide Menu for the first stage of project
        // mDrawerLayout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        /* code to set custom icon on toolbar
        if we want the default hamburger icon, just remove it */

        mDrawerToggle?.let{
            it.isDrawerIndicatorEnabled = false
            it.setToolbarNavigationClickListener {
                mDrawerLayout?.openDrawer(GravityCompat.START)
            }

            it.setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        openFragment(0)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    /** Declares layout and tag to observe of the list item. */
    fun itemViewBinder(): ItemBinder<AMenuViewModel> {
        return CompositeItemBinder(MenuItemBinder(BR.viewModel, R.layout.recycler_item_menu))
    }
}