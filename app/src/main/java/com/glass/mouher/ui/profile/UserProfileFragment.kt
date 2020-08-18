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
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.menu.AMenuViewModel
import com.glass.mouher.ui.menu.MenuItemBinder
import org.koin.android.viewmodel.ext.android.viewModel

class UserProfileFragment : Fragment() {

    private val viewModel: UserProfileViewModel by viewModel()
    private lateinit var binding: FragmentUserProfileBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvProfile.layoutManager = GridLayoutManager(context, 2)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.findViewById<ImageView>(R.id.icBackHome)?.visibility = View.GONE
        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AUserProfileViewModel> {
        return CompositeItemBinder(UserProfileItemBinder(BR.viewModel, R.layout.recycler_item_profile))
    }
}