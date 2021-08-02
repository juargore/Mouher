package com.glass.mouher.ui.mall.home.stores

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentStoresBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.ui.store.MainStoreActivity
import org.koin.android.viewmodel.ext.android.viewModel

class StoresFragment : Fragment() {

    private val viewModel: StoresViewModel by viewModel()
    private lateinit var binding: FragmentStoresBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.openStoreWithId -> openStore()
                BR.onClose -> activity?.onBackPressed()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentStoresBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvStores.layoutManager = LinearLayoutManager(requireContext())

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

    private fun openStore(){
        val intent = Intent(requireActivity(), MainStoreActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            putExtra("storeId", viewModel.openStoreWithId)
        }

        activity?.overridePendingTransition(0,0)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    private fun showErrorMsg(){
        showSnackbar(binding.root, viewModel.error, SnackType.ERROR)
    }

    fun itemViewBinder(): ItemBinder<AStoresViewModel> {
        return CompositeItemBinder(StoresItemBinder(BR.viewModel, R.layout.recycler_item_stores))
    }
}