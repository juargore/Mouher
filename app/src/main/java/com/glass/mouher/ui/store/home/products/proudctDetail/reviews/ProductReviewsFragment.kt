package com.glass.mouher.ui.store.home.products.proudctDetail.reviews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentProductDetailBinding
import com.glass.mouher.databinding.FragmentProductReviewsBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.ui.store.home.products.proudctDetail.AProductDetailViewModel
import com.glass.mouher.ui.store.home.products.proudctDetail.ProductDetailViewModel
import com.glass.mouher.ui.store.home.products.proudctDetail.ProductsDetailItemMiniBinder
import org.koin.android.viewmodel.ext.android.viewModel

class ProductReviewsFragment : Fragment() {

    private val viewModel: ProductReviewsViewModel by viewModel()
    private lateinit var binding: FragmentProductReviewsBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.backClicked -> activity?.onBackPressed()
                BR.error -> showErrorMsg()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_reviews, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvReviews.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun showErrorMsg(){
        showSnackbar(binding.root, viewModel.error, SnackType.ERROR)
    }

    fun itemViewBinder(): ItemBinder<AProductReviewViewModel> {
        return CompositeItemBinder(ProductsReviewsItemBinder(BR.viewModel, R.layout.recycler_item_review))
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}