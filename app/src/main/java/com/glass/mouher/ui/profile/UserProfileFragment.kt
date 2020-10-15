package com.glass.mouher.ui.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.GridLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentUserProfileBinding
import com.glass.mouher.ui.checkout.address.AddressFragment
import com.glass.mouher.ui.checkout.payment.PaymentFragment
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.home.stores.StoresFragment
import com.glass.mouher.ui.menu.AMenuViewModel
import com.glass.mouher.ui.menu.MenuItemBinder
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
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container_body_mall, PaymentFragment())
                        addToBackStack("Payment")
                        commit()
                    }
                }
                BR.openAddress -> {
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container_body_mall, AddressFragment())
                        addToBackStack("Address")
                        commit()
                    }
                }
                BR.onDiscard -> showPopConfirmation()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        binding.viewModel = viewModel
        binding.view = this

        return binding.root
    }

    private fun showPopConfirmation(){
        alert(title = "", message = "¿Está seguro que desea descartar este elemento?"){
            yesButton {

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