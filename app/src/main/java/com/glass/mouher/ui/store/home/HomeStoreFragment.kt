package com.glass.mouher.ui.store.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.ProductUI
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentHomeStoreBinding
import com.glass.mouher.extensions.startFadeInAnimation
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.ui.mall.home.adapters.HomeSponsorsAdapter
import com.glass.mouher.ui.store.MainStoreActivity
import com.glass.mouher.ui.store.home.products.ProductsFragment
import com.glass.mouher.ui.store.home.products.proudctDetail.ProductDetailFragment
import com.glass.mouher.utils.WebBrowserUtils
import com.synnapps.carouselview.ImageListener
import org.koin.android.viewmodel.ext.android.viewModel

class HomeStoreFragment : Fragment() {

    private val viewModel: HomeStoreViewModel by viewModel()
    private lateinit var binding: FragmentHomeStoreBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.urlVideo -> setUpVideo(viewModel.urlVideo)
                BR.bannerList -> setImagesInBanner()
                BR.itemsNewProducts -> setNewProducts(viewModel.itemsNewProducts)
                BR.sponsorStoresList -> setLinkedStores()
                BR.error -> showErrorMsg()
                BR.onClick -> openCategoryScreen()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_store, container, false)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvCategories.layoutManager = GridLayoutManager(context, 2)

        viewModel.initialize(requireContext(), MainStoreActivity.storeId)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setImagesInBanner(){
        with(binding.carouselView){
            setImageListener(imageListener)
            pageCount = viewModel.bannerList.size

            binding.carouselView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
                override fun onPageScrollStateChanged(state: Int) {}
                override fun onPageSelected(position: Int) {}

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
            })

            setImageClickListener { position->
                val item = viewModel.bannerList[position]
                if(!item.linkToOpen.isNullOrBlank()){
                    WebBrowserUtils.openUrlInExternalWebBrowser(item.linkToOpen!!)
                }
            }
        }
    }

    private var imageListener: ImageListener = ImageListener { position, imageView ->
        Glide.with(requireContext())
            .load(viewModel.bannerList[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }

    private fun setNewProducts(itemsNewProducts: List<ProductUI>) {
        with(binding.rvNewProducts){
            val mAdapter = HomeStoreNewProductsAdapter(requireContext(), itemsNewProducts)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter

            mAdapter.onItemClicked={ id->
                val args = Bundle().apply {
                    putInt("productId", id)
                    putInt("storeId", viewModel.storeId)
                }

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container_body, ProductDetailFragment().apply {
                        arguments = args
                    })

                    addToBackStack("ProductDetail")
                    commit()
                }
            }
        }
    }

    private fun openCategoryScreen(){
        val args = Bundle().apply {
            putInt("categoryId", viewModel.categoryId)
            putInt("storeId", viewModel.storeId)
            putString("categoryName", viewModel.categoryName)
        }

        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.container_body, ProductsFragment().apply {
                arguments = args
            })

            addToBackStack("Products")
            commit()
        }
    }

    private fun setLinkedStores() {
        with(binding.rvLinkedStores){
            val mAdapter = HomeSponsorsAdapter(viewModel.sponsorStoresList)

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)

            adapter = mAdapter

            mAdapter.onItemClicked={ sponsor->
                val intent = Intent(requireActivity(), MainStoreActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    putExtra("storeId", sponsor.id)
                }

                activity?.let{
                    it.overridePendingTransition(0,0)
                    startActivity(intent)
                    it.overridePendingTransition(0,0)
                    it.finish()
                }

            }
        }
    }

    private fun setUpVideo(urlVideo: String?) {
        if(urlVideo.isNullOrBlank()){
            binding.layVideo.visibility = View.GONE
        }else{
            with(binding.videoStore){
                setMediaController(MediaController(requireContext()))
                setVideoURI(Uri.parse(viewModel.urlVideo))
            }
        }
    }

    private fun showErrorMsg(){
        with(viewModel.error){
            val errorType = if(hasErrors) SnackType.ERROR else SnackType.INFO
            showSnackbar(binding.root, viewModel.error.message, errorType)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AStoreCategoryViewModel> {
        return CompositeItemBinder(StoreCategoryItemBinder(BR.viewModel, R.layout.recycler_item_store_categories))
    }
}