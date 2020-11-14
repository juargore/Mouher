package com.glass.mouher.ui.store.home.products.proudctDetail

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.Item
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentProductDetailBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.store.home.HomeStoreLinkedStoresAdapter
import com.glass.mouher.ui.store.home.products.proudctDetail.reviews.ProductReviewsFragment
import org.koin.android.viewmodel.ext.android.viewModel

class ProductDetailFragment : Fragment() {

    private val viewModel: ProductDetailViewModel by viewModel()
    private lateinit var binding: FragmentProductDetailBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.miniSelected -> loadMiniImage(viewModel.miniSelected)
                BR.itemsRelatedProducts -> setRelatedProducts(viewModel.itemsRelatedProducts)
                BR.showPopRating -> showPopUpRating()
                BR.onBack -> activity?.onBackPressed()
                BR.openScreenReviews -> {
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container_body, ProductReviewsFragment())
                        addToBackStack("Reviews")
                        commit()
                    }
                }
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
        binding.rvRelatedProducts.layoutManager = LinearLayoutManager(context)

        viewModel.initialize(arguments?.getString("productId"), arguments?.getString("storeId"))

        return binding.root
    }

    private fun setRelatedProducts(itemsRelatedProducts: MutableList<Item>) {
        val adapter = ProductDetailRelatedProductsAdapter(requireContext(), itemsRelatedProducts,
            object : ProductDetailRelatedProductsAdapter.InterfaceOnClick{
                override fun onItemClick(pos: Int) {

                }
            })

        binding.rvRelatedProducts.adapter = adapter
    }

    private fun loadMiniImage(imageUrl: String?){
        Glide.with(requireContext())
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(binding.photoView)
    }

    private fun showPopUpRating(){
        Dialog(requireContext(), R.style.FullDialogTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.pop_add_rating)
            show()
        }
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