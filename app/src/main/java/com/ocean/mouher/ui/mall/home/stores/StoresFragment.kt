package com.ocean.mouher.ui.mall.home.stores

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.ocean.mouher.R
import com.ocean.mouher.databinding.FragmentStoresBinding
import com.ocean.mouher.extensions.startActivityNoAnimation
import com.ocean.mouher.shared.General.saveComesFromStores
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.binder.CompositeItemBinder
import com.ocean.mouher.ui.common.binder.ItemBinder
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.common.showSnackbar
import com.ocean.mouher.ui.store.MainStoreActivity
import org.koin.android.viewmodel.ext.android.viewModel

class StoresFragment : Fragment() {

    private val viewModel: StoresViewModel by viewModel()
    private lateinit var binding: FragmentStoresBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.onClose -> onClose()
            BR.openStoreWithId -> openStore()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStoresBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        viewModel.initialize(
            arguments?.getString("zoneName"),
            arguments?.getString("zoneId")
        )

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun openStore() {
        startActivityNoAnimation(Intent(activity, MainStoreActivity::class.java)
            .putExtra("storeId", viewModel.openStoreWithId))
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    private fun showErrorMsg() {
        showSnackbar(binding.root, viewModel.error, SnackType.ERROR)
    }

    private fun onClose() {
        saveComesFromStores(true)
        activity?.onBackPressed()
    }

    fun itemViewBinder(): ItemBinder<AStoresViewModel> {
        return CompositeItemBinder(StoresItemBinder(BR.viewModel, R.layout.recycler_item_stores))
    }
}