package com.glass.mouher.ui.store.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentHomeBinding
import com.glass.mouher.databinding.FragmentHomeStoreBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.home.HomeViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class HomeStoreFragment : Fragment() {

    private val viewModel: HomeStoreViewModel by viewModel()
    private lateinit var binding: FragmentHomeStoreBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_store, container, false)
        binding.viewModel = viewModel
        binding.view = this

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
}