package com.glass.mouher.ui.store

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.domain.entities.MainSlideMenuItem
import com.glass.mouher.R
import com.glass.mouher.ui.store.home.HomeStoreFragment
import kotlinx.android.synthetic.main.fragment_main_side_store.view.*

class MainFragmentSideStore : Fragment() {
    private var mDrawerToggle: ActionBarDrawerToggle? = null
    private var mDrawerLayout: DrawerLayout? = null
    private var containerView: View? = null
    private lateinit var rootView : View

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        rootView = inflater.inflate(R.layout.fragment_main_side_store, container, false)

        requireContext().packageManager.getPackageInfo(requireContext().packageName, PackageManager.GET_ACTIVITIES).apply {
            rootView.findViewById<TextView>(R.id.txtVersionApp).text = "App version: $versionName"
        }

        setUpUserImage()
        setRecyclerView()
        openFragment(0)

        return rootView
    }

    private fun setRecyclerView(){
        val layoutManager = LinearLayoutManager(context)
        rootView.recylerViewSide.layoutManager = layoutManager

        val drawerAdapter = AdapterSlideMainMenu(
            requireContext(),
            Items.options,
            object :
                AdapterSlideMainMenu.InterfaceOnClick {
                override fun onItemClick(drawerItem: MainSlideMenuItem, pos: Int) {
                    openFragment(pos)
                    mDrawerLayout!!.closeDrawer(containerView!!)
                }
            })

        rootView.recylerViewSide.adapter = drawerAdapter
    }

    private fun setUpUserImage(){
        rootView.imgUserSlide.setImageResource(R.drawable.ic_info)
        rootView.imgUserSlide.invalidate()
        /*val bitmap = (rootView.imgUserSlide.drawable as BitmapDrawable).bitmap

        Glide.with(requireContext())
            .load(bitmap)
            .apply(RequestOptions().circleCrop()).into(rootView.imgUserSlide)*/
    }

    private fun openFragment(position: Int) {
        when (position) {
            0 -> removeAllFragment(HomeStoreFragment())
        }
    }

    private fun removeAllFragment(replaceFragment: Fragment) {
        val manager = requireActivity().supportFragmentManager
        val ft = manager.beginTransaction()
        manager.popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)

        ft.replace(R.id.container_body, replaceFragment)
        ft.commitAllowingStateLoss()
    }

    fun setUpDrawer(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar) {
        containerView = requireActivity().findViewById(fragmentId)
        mDrawerLayout = drawerLayout

        mDrawerToggle = object : ActionBarDrawerToggle(activity, drawerLayout, toolbar,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                activity!!.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                activity!!.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar.alpha = 1 - slideOffset / 2
            }
        }

        mDrawerLayout!!.addDrawerListener(mDrawerToggle as ActionBarDrawerToggle)
        mDrawerLayout!!.post { mDrawerToggle!!.syncState() }


        // hack to disable slide Menu for the first stage of project
        // mDrawerLayout!!.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        /* code to set custom icon on toolbar
        if we want the default hamburger icon, just remove it */

        mDrawerToggle!!.isDrawerIndicatorEnabled = false

        mDrawerToggle!!.setToolbarNavigationClickListener {
            mDrawerLayout!!.openDrawer(GravityCompat.START)
        }

        mDrawerToggle!!.setHomeAsUpIndicator(R.drawable.ic_blur)
    }

    /*private fun showPopUpExit(){
        alert("¿Confirma que desea cerrar su sesión actual?", "Cerrar sesión") {
            yesButton {
                setRememberCredentials(requireContext(), false)

                val intent = Intent(context, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context?.startActivity(intent)
            }
            noButton { /* close dialog */}
        }.show()
    }*/

    object Items{
        val options = listOf(
            MainSlideMenuItem("Checar Entrada / Salida", R.drawable.ic_work),
            MainSlideMenuItem("Historial de Registros", R.drawable.ic_gift),
            MainSlideMenuItem("Cerrar sesión", R.drawable.ic_home)
        )
    }
}