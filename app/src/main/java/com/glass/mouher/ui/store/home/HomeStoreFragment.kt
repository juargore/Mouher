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
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.Item
import com.glass.domain.entities.ShortProductUI
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentHomeStoreBinding
import com.glass.mouher.extensions.startFadeInAnimation
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.store.home.products.ProductsFragment
import com.glass.mouher.ui.store.home.products.proudctDetail.ProductDetailFragment
import com.synnapps.carouselview.ImageListener
import org.koin.android.viewmodel.ext.android.viewModel

class HomeStoreFragment : Fragment() {

    private val viewModel: HomeStoreViewModel by viewModel()
    private lateinit var binding: FragmentHomeStoreBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.bannerList -> setImagesInBanner()
                BR.itemsNewProducts -> setNewProducts(viewModel.itemsNewProducts)
                BR.itemsLinkedStores -> setLinkedStores(viewModel.itemsLinkedStores)
                BR.urlVideo -> setUpVideo(viewModel.urlVideo)
                BR.onClick ->{
                    val args = Bundle().apply {
                        putString("categoryId", viewModel.categoryId)
                        putString("storeId", viewModel.storeId)
                    }

                    requireActivity().supportFragmentManager.beginTransaction().apply {
                        replace(R.id.container_body, ProductsFragment().apply {
                            arguments = args
                        })

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
        binding.rvLinkedStores.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        return binding.root
    }

    private fun setImagesInBanner(){
        binding.carouselView.setImageListener(imageListener)
        binding.carouselView.pageCount = viewModel.bannerList.size

        binding.carouselView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixels: Int) {
                binding.carouselTitle.apply {
                    this.startFadeInAnimation()
                    this.text = viewModel.bannerList[pos].title
                }
                binding.carouselSubtitle.apply {
                    this.startFadeInAnimation()
                    this.text = viewModel.bannerList[pos].subtitle
                }
            }
            override fun onPageSelected(position: Int) {}
        })
    }

    private var imageListener: ImageListener = ImageListener { position, imageView ->
        Glide.with(requireContext())
            .load(viewModel.bannerList[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }

    private fun setNewProducts(itemsNewProducts: List<ShortProductUI>) {
        val adapter = HomeStoreNewProductsAdapter(requireContext(), itemsNewProducts, object : HomeStoreNewProductsAdapter.InterfaceOnClick{
            override fun onItemClick(productId: String?) {
                val args = Bundle().apply {
                    putString("productId", productId)
                    putString("storeId", viewModel.storeId)
                }

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container_body, ProductDetailFragment().apply {
                        arguments = args
                    })

                    addToBackStack("ProductDetail")
                    commit()
                }
            }
        })

        binding.rvNewProducts.adapter = adapter
    }

    private fun setLinkedStores(itemsLinkedStores: MutableList<Item>) {
        val adapter = HomeStoreLinkedStoresAdapter(requireContext(), itemsLinkedStores, object : HomeStoreLinkedStoresAdapter.InterfaceOnClick{
            override fun onItemClick(pos: Int) {

            }
        })

        binding.rvLinkedStores.adapter = adapter
    }

    private fun setUpVideo(urlVideo: String) {
        /*with(binding.videoStore){
            setVideoPath(urlVideo)

            setOnClickListener {
                if(isPlaying){
                    stopPlayback(); resume()
                } else{
                    start()
                }
            }
        }*/
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