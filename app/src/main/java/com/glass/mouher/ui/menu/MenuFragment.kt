package com.glass.mouher.ui.menu

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glass.domain.entities.Item
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentMenuBinding
import com.glass.mouher.ui.about.AboutFragment
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.history.HistoryFragment
import com.glass.mouher.ui.mall.home.HomeMallFragment
import com.glass.mouher.ui.mall.home.stores.StoresFragment
import com.glass.mouher.ui.profile.UserProfileFragment
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
                BR.itemsSocial -> setUpSocialMediaItems(viewModel.itemsSocial)
                BR.screen -> openFromMenuSubscreen(viewModel.screen)
                BR.openZoneSelected -> openStoresByZoneId()
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
        binding.rvSocialMedia.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setUpSocialMediaItems(itemsSocial: MutableList<Item>) {
        val adapter = MenuItemSocialMediaAdapter(requireContext(), itemsSocial,
            object : MenuItemSocialMediaAdapter.InterfaceOnClick{
                override fun onItemClick(pos: Int) {

                }
            })

        binding.rvSocialMedia.adapter = adapter
    }

    private fun openStoresByZoneId(){
        val args = Bundle().apply {
            putString("zoneName", viewModel.openZoneSelected?.name)
            putString("zoneId", viewModel.openZoneSelected?.id.toString())
        }

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_body_mall, StoresFragment().apply {
                arguments = args
            })

            addToBackStack("Stores")
            commit()
        }

        view?.let{
            mDrawerLayout?.closeDrawer(it)
        }
    }

    private fun openFromMenuSubscreen(from: MENU?){

        if(from == MENU.CONTACT){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")))
            return
        }

        if(from == MENU.EXTRA_SERVICES){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com")))
            return
        }

        if(from == MENU.MORE_INFO){
            showPopUpContact()
            return
        }

        val fragment: Fragment? = when(from){
            MENU.PROFILE -> UserProfileFragment()
            MENU.HISTORY -> HistoryFragment()
            MENU.ABOUT -> AboutFragment()
            else -> null
        }

        fragment?.let{
            val container = if(source == "MALL") R.id.container_body_mall else R.id.container_body

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(container, it)
                addToBackStack(from?.name)
                commit()
            }

            view?.let{
                mDrawerLayout?.closeDrawer(it)
            }
        }

    }

    private fun showPopUpContact(){
        Dialog(requireContext(), R.style.FullDialogTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.pop_contact)
            show()
        }
    }

    private fun openMallorStoreScreen() {
        val fragment = if(source == "MALL") HomeMallFragment() else HomeStoreFragment()
        removeAllFragment(fragment)
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

        openMallorStoreScreen()
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