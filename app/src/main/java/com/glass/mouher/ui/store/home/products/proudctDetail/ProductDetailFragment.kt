package com.glass.mouher.ui.store.home.products.proudctDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentProductDetailBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import org.koin.android.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var binding: FragmentProductDetailBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.miniSelected -> loadMiniImage(viewModel.miniSelected)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvMiniList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun loadMiniImage(imageUrl: String?){
        Glide.with(requireContext())
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(binding.photoView)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AProductDetailViewModel> {
        return CompositeItemBinder(ProductsDetailItemMiniBinder(BR.viewModel, R.layout.recycler_item_mini_detail))
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}