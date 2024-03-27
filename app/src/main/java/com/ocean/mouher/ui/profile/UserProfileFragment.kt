package com.ocean.mouher.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.ocean.mouher.R
import com.ocean.mouher.databinding.FragmentUserProfileBinding
import com.ocean.mouher.extensions.openOrRefreshFragment
import com.ocean.mouher.shared.General.getCurrentStoreName
import com.ocean.mouher.shared.General.saveUserCreationDate
import com.ocean.mouher.shared.General.saveUserEmail
import com.ocean.mouher.shared.General.saveUserId
import com.ocean.mouher.shared.General.saveUserName
import com.ocean.mouher.shared.General.saveUserSignedIn
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.mall.MainActivityMall
import com.ocean.mouher.ui.menu.MenuViewModel
import com.ocean.mouher.ui.profile.address.AddressFragment
import com.ocean.mouher.ui.profile.payment.PaymentFragment
import com.ocean.mouher.ui.store.MainStoreActivity
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import org.koin.android.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModel()
    private lateinit var binding: FragmentUserProfileBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.signOut -> showPopSignOut()
            BR.backClicked -> activity?.onBackPressed()
            BR.openProfileScreen -> openSubProfileScreen()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun openSubProfileScreen(){
        requireActivity().openOrRefreshFragment(
            forStore = MenuViewModel.source != "MALL",
            destination = viewModel.openProfileScreen,
            args = null,
            name = when(viewModel.openProfileScreen){
                PaymentFragment() -> "Payment"
                AddressFragment() -> "Address"
                else -> "Personal"
            }
        )
    }

    private fun showPopSignOut(){
        val msg = if(viewModel.totalProductsOnDb > 0){
            resources.getString(R.string.cart_confirm_sign_out, getCurrentStoreName())
        }else{
            resources.getString(R.string.app_confirm_sign_out)
        }

        alert(title = "", message = msg){
            yesButton {
                if(viewModel.totalProductsOnDb > 0){
                    viewModel.clearProductsFromCart()
                }

                updateValuesOnSignOut()
            }
            noButton { it.dismiss() }
        }.show()
    }

    private fun updateValuesOnSignOut(){
        saveUserSignedIn(false)
        saveUserCreationDate("")
        saveUserName("")
        saveUserEmail("")
        saveUserId(0)

        // Refresh activity to update side menu
        if(activity is MainActivityMall){
            val parentActivity = activity as MainActivityMall
            parentActivity.refreshActivityFromFragment()
        }else{
            val parentActivity = activity as MainStoreActivity
            parentActivity.refreshActivityFromFragment()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}