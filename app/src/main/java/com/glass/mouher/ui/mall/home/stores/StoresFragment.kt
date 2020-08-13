package com.glass.mouher.ui.mall.home.stores

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.glass.mouher.R
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.GridLayoutManager
import com.glass.mouher.databinding.FragmentStoresBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.store.MainActivityStore
import org.koin.android.viewmodel.ext.android.viewModel

class StoresFragment : Fragment() {

    private val args: StoresFragmentArgs by navArgs()
    private val viewModel: StoresViewModel by viewModel()
    private lateinit var binding: FragmentStoresBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.openStore -> {
                    val intent = Intent(requireActivity(), MainActivityStore::class.java)
                    activity?.overridePendingTransition(0,0)
                    intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    startActivity(intent)
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_stores, container, false)
        binding.viewModel = viewModel
        binding.view = this

        activity?.findViewById<ImageView>(R.id.icBackHome).apply {
            this?.visibility = View.VISIBLE
            this?.setOnClickListener {

            }
        }

        viewModel.initialize(args.zoneId)
        binding.rvStores.layoutManager = GridLayoutManager(requireContext(), 2)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AStoresViewModel> {
        return CompositeItemBinder(StoresItemBinder(BR.viewModel, R.layout.recycler_item_stores))
    }
}