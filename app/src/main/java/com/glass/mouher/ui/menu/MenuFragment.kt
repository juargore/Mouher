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
import androidx.databinding.library.baseAdapters.BR
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import com.glass.domain.entities.CategoryUI
import com.glass.domain.entities.SocialMediaUI
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentMenuBinding
import com.glass.mouher.extensions.openOrRefreshFragment
import com.glass.mouher.shared.General
import com.glass.mouher.shared.General.getUserName
import com.glass.mouher.ui.about.AboutFragment
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.history.HistoryFragment
import com.glass.mouher.ui.mall.MainActivityMall
import com.glass.mouher.ui.mall.home.HomeMallFragment
import com.glass.mouher.ui.mall.home.stores.StoresFragment
import com.glass.mouher.ui.profile.UserProfileFragment
import com.glass.mouher.ui.registration.signin.SignInActivity
import com.glass.mouher.ui.store.MainStoreActivity
import com.glass.mouher.ui.store.home.HomeStoreFragment
import com.glass.mouher.ui.store.home.products.ProductsFragment
import com.glass.mouher.utils.WebBrowserUtils.openUrlInExternalWebBrowser
import kotlinx.android.synthetic.main.pop_contact.*
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
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
                BR.itemsSocial -> setUpMallSocialMediaItems(viewModel.itemsSocial)
                BR.screen -> openFromMenuSubscreen(viewModel.screen)
                BR.openZoneSelected -> openMallStoresByZoneId()
                BR.signOut -> showPopSignOut()
                BR.categories -> setUpStoreCategoriesItems(viewModel.categories)
            }
        }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvMenu.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun showPopSignOut() {
        val msg = if (viewModel.totalProductsOnDb > 0) {
            resources.getString(R.string.cart_confirm_sign_out, General.getCurrentStoreName())
        } else {
            resources.getString(R.string.app_confirm_sign_out)
        }

        alert(title = "", message = msg) {
            yesButton {
                if (viewModel.totalProductsOnDb > 0) {
                    viewModel.clearProductsFromCart()
                }

                updateValuesOnSignOut()
            }
            noButton { it.dismiss() }
        }.show()
    }

    private fun updateValuesOnSignOut(){
        General.saveUserSignedIn(false)
        General.saveUserCreationDate("")
        General.saveUserName("")
        General.saveUserEmail("")
        General.saveUserId(0)

        // Refresh activity to update side menu
        if(activity is MainActivityMall){
            val parentActivity = activity as MainActivityMall
            parentActivity.refreshActivityFromFragment()
        }else{
            val parentActivity = activity as MainStoreActivity
            parentActivity.refreshActivityFromFragment()
        }
    }

    private fun setUpMallSocialMediaItems(itemsSocial: List<SocialMediaUI>) {
        with(binding.rvSocialMedia){
            val socialAdapter = MenuItemSocialMediaAdapter(itemsSocial)

            layoutManager = LinearLayoutManager(context, HORIZONTAL, false)
            adapter = socialAdapter

            socialAdapter.onItemClicked ={
                if(!it.linkToOpen.isNullOrBlank()){
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.linkToOpen)))
                }
            }
        }
    }

    private fun setUpStoreCategoriesItems(itemsCategories: List<CategoryUI>){
        with(binding.rvMenu){
            val categoryAdapter = MenuItemCategoriesAdapter(itemsCategories)

            layoutManager = LinearLayoutManager(requireContext())
            adapter = categoryAdapter

            categoryAdapter.onItemClicked={
                val args = Bundle().apply {
                    putInt("categoryId", it.categoryId ?: 1)
                    putInt("storeId", viewModel.storeIdSelectedOnMenu)
                    putString("categoryName", it.name)
                }

                requireActivity().openOrRefreshFragment(
                    forStore = true,
                    destination = ProductsFragment(),
                    args = args,
                    name = "Products"
                )

                closeDrawer()
            }
        }
    }

    private fun openMallStoresByZoneId(){
        val args = Bundle().apply {
            putString("zoneName", viewModel.openZoneSelected?.name)
            putString("zoneId", viewModel.openZoneSelected?.id.toString())
        }

        requireActivity().openOrRefreshFragment(
            forStore = false,
            destination = StoresFragment(),
            args = args,
            name = "Stores"
        )

        closeDrawer()
    }

    private fun openFromMenuSubscreen(from: MENU?){
        if(from == MENU.LOGIN){
            val intent = Intent(activity, SignInActivity::class.java)
            intent.putExtra("source", source)
            startActivity(Intent(intent))

            closeDrawer()
            return
        }

        if(from == MENU.CONTACT){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.urlContactUs)))
            closeDrawer()
            return
        }

        if(from == MENU.EXTRA_SERVICES){
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.urlExtraServices)))
            closeDrawer()
            return
        }

        if(from == MENU.MORE_INFO){
            showPopUpContact()
            closeDrawer()
            return
        }

        val fragment: Fragment? = when(from){
            MENU.PROFILE -> UserProfileFragment()
            MENU.HISTORY -> HistoryFragment()
            MENU.ABOUT -> AboutFragment()
            else -> null
        }

        if(fragment is AboutFragment){
            val args = Bundle().apply {
                putInt("storeId", viewModel.storeIdSelectedOnMenu)
            }

            fragment.arguments = args
        }

        fragment?.let{
            val container = if(source == "MALL") R.id.container_body_mall else R.id.container_body

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(container, it)
                addToBackStack(from?.name)
                commit()
            }

            closeDrawer()
        }
    }

    private fun showPopUpContact(){
        Dialog(requireContext(), R.style.FullDialogTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.pop_contact); show()

            txtAddress.text = viewModel.address
            txtPhone.text = viewModel.phone
            txtEmail.text = viewModel.email
            txtWorkHours.text = viewModel.workHours

            txtTerms.setOnClickListener {
                openUrlInExternalWebBrowser(viewModel.urlTermsAndConditions)
            }

            txtPrivacyPolicy.setOnClickListener {
                openUrlInExternalWebBrowser(viewModel.urlNoticeOfPrivacy)
            }

            txtPoliciesBuyers.setOnClickListener {
                openUrlInExternalWebBrowser(viewModel.urlPoliciesUsersBuyers)
            }

            txtPoliciesSellers.setOnClickListener {
                openUrlInExternalWebBrowser(viewModel.urlPoliciesUsersSellers)
            }
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

    fun setUpDrawer(fragmentId: Int, drawerLayout: DrawerLayout, toolbar: Toolbar?, source: String) {
        this.source = source
        viewModel.initialize(requireContext(), source)

        containerView = requireActivity().findViewById(fragmentId)
        mDrawerLayout = drawerLayout

        mDrawerToggle = object : ActionBarDrawerToggle(
            activity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                viewModel.userName = getUserName()
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerClosed(drawerView: View) {
                super.onDrawerClosed(drawerView)
                activity?.invalidateOptionsMenu()
            }

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                toolbar?.alpha = 1 - slideOffset / 2
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

    private fun closeDrawer(){
        view?.let{
            mDrawerLayout?.closeDrawer(it)
        }
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