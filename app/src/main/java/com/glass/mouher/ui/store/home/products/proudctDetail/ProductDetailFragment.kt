package com.glass.mouher.ui.store.home.products.proudctDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentProductDetailBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import org.koin.android.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var binding: FragmentProductDetailBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
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