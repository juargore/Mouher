package com.glass.mouher.ui.store.home.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentProductsListBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.store.home.products.proudctDetail.ProductDetailFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsFragment : Fragment() {

    private val viewModel: ProductsViewModel by viewModel()
    private lateinit var binding: FragmentProductsListBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.onBack -> activity?.onBackPressed()
                BR.detailScreen -> openDetailedScreen()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_products_list, container, false)
        binding.viewModel = viewModel
        binding.view = this

        arguments?.let{ args->
            val categoryId = args.getInt("categoryId")
            val storeId = args.getInt("storeId")
            val categoryName = args.getString("categoryName")

            viewModel.initialize(requireContext(), categoryId, storeId, categoryName)
        }

        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 1)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun openDetailedScreen(){
        val args = Bundle().apply {
            putInt("productId", viewModel.productId)
            putInt("storeId", viewModel.storeId)
        }

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_body, ProductDetailFragment().apply {
                arguments = args })

            addToBackStack("Detail"); commit()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AProductsViewModel> {
        return CompositeItemBinder(ProductsItemBinder(BR.viewModel, R.layout.recycler_item_products))
    }
}