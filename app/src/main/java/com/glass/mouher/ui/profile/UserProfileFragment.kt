package com.glass.mouher.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentUserProfileBinding
import com.glass.mouher.shared.General.saveUserEmail
import com.glass.mouher.shared.General.saveUserId
import com.glass.mouher.shared.General.saveUserName
import com.glass.mouher.shared.General.saveUserSignedIn
import com.glass.mouher.ui.checkout.address.AddressFragment
import com.glass.mouher.ui.checkout.payment.PaymentFragment
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.MainActivityMall
import com.glass.mouher.ui.store.MainStoreActivity
import com.glass.mouher.utils.openOrRefreshFragment
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import org.koin.android.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModel()
    private lateinit var binding: FragmentUserProfileBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.openPayment -> {
                    requireActivity().openOrRefreshFragment(
                        forStore = false,
                        destination = PaymentFragment(),
                        args = null,
                        name = "Payment"
                    )
                }
                BR.openAddress -> {
                    requireActivity().openOrRefreshFragment(
                        forStore = false,
                        destination = AddressFragment(),
                        args = null,
                        name = "Address"
                    )
                }
                BR.onDiscard -> showPopConfirmation()
                BR.signOut -> showPopSignOut()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        binding.viewModel = viewModel
        binding.view = this

        return binding.root
    }

    private fun showPopConfirmation(){
        alert(title = "", message = "¿Está seguro que desea eliminar este registro?"){
            yesButton {

            }
        }.show()
    }

    private fun showPopSignOut(){
        alert(title = "", message = "¿Seguro que deseas cerrar sesión en Mouher Market?"){
            yesButton {
                saveUserSignedIn(false)
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
            noButton {
                it.dismiss()
            }
        }.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}