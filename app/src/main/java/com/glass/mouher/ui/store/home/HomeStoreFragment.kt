package com.glass.mouher.ui.store.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.domain.entities.Item
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentHomeStoreBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.store.home.products.ProductsFragment
import org.koin.android.viewmodel.ext.android.viewModel

class HomeStoreFragment : Fragment() {

    private val viewModel: HomeStoreViewModel by viewModel()
    private lateinit var binding: FragmentHomeStoreBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.itemsNewProducts -> setNewProducts(viewModel.itemsNewProducts)
                BR.urlVideo -> setUpVideo(viewModel.urlVideo)
                BR.onClick ->{
                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container_body, ProductsFragment())
                        addToBackStack("Products")
                        commit()
                    }
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_store, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvCategories.layoutManager = GridLayoutManager(context, 2)
        binding.rvNewProducts.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    private fun setNewProducts(itemsNewProducts: MutableList<Item>) {
        val adapter = HomeStoreNewProductsAdapter(requireContext(), itemsNewProducts, object : HomeStoreNewProductsAdapter.InterfaceOnClick{
            override fun onItemClick(pos: Int) {
                //TODO: Open detailed product
            }
        })

        binding.rvNewProducts.adapter = adapter
    }

    private fun setUpVideo(urlVideo: String) {
        with(binding.videoStore){
            setVideoPath(urlVideo)

            setOnClickListener {
                if(isPlaying){
                    stopPlayback(); resume()
                } else{
                    start()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AStoreCategoryViewModel> {
        return CompositeItemBinder(StoreCategoryItemBinder(BR.viewModel, R.layout.recycler_item_store_categories))
    }
}