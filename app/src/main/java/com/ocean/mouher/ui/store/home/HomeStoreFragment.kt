@file:Suppress("DEPRECATION")
package com.ocean.mouher.ui.store.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.TextView
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ocean.domain.entities.ProductUI
import com.ocean.domain.entities.SponsorUI
import com.ocean.mouher.R
import com.ocean.mouher.databinding.FragmentHomeStoreBinding
import com.ocean.mouher.extensions.openOrRefreshFragment
import com.ocean.mouher.extensions.startActivityNoAnimation
import com.ocean.mouher.shared.General.getCurrentStoreName
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.binder.CompositeItemBinder
import com.ocean.mouher.ui.common.binder.ItemBinder
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.common.showSnackbar
import com.ocean.mouher.ui.mall.home.adapters.HomeSponsorsAdapter
import com.ocean.mouher.ui.store.MainStoreActivity
import com.ocean.mouher.ui.store.home.products.ProductsFragment
import com.ocean.mouher.ui.store.home.products.proudctDetail.ProductDetailFragment
import com.ocean.mouher.utils.Constants.SPONSOR_DURATION
import com.ocean.mouher.utils.Constants.ScrollPositions
import com.ocean.mouher.utils.CustomVideoView
import com.ocean.mouher.utils.SpeedyLinearLayoutManager
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import org.koin.android.viewmodel.ext.android.viewModel

class HomeStoreFragment : Fragment() {

    private val viewModel: HomeStoreViewModel by viewModel()
    private lateinit var binding: FragmentHomeStoreBinding
    private var currentState = ScrollPositions.Middle

    lateinit var carouselTitle: TextView
    lateinit var carouselSubTitle: TextView

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.urlVideo -> setUpVideo(viewModel.urlVideo)
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
        binding = FragmentHomeStoreBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        carouselTitle = binding.carouselTitle
        carouselSubTitle = binding.carouselSubtitle

        viewModel.initialize(requireContext(), MainStoreActivity.storeId)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)

        if (MainStoreActivity.productIdFromSearch != null) {
            val args = Bundle().apply {
                putInt("productId", MainStoreActivity.productIdFromSearch!!)
                putInt("storeId", viewModel.storeId)
                putBoolean("comesFromSearch", true)
            }

            requireActivity().openOrRefreshFragment(
                forStore = true,
                destination = ProductDetailFragment(),
                args = args,
                name = "ProductDetail"
            )
            MainStoreActivity.productIdFromSearch = null
        }
    }

    private fun setNewProducts(itemsNewProducts: List<ProductUI>) {
        with(binding.rvNewProducts){
            val mAdapter = HomeStoreNewProductsAdapter(requireContext(), itemsNewProducts)
            adapter = mAdapter

            mAdapter.onItemClicked = { id ->
                val args = Bundle().apply {
                    putInt("productId", id)
                    putInt("storeId", viewModel.storeId)
                }

                requireActivity().openOrRefreshFragment(
                    forStore = true,
                    destination = ProductDetailFragment(),
                    args = args,
                    name = "ProductDetail"
                )
            }
        }
    }

    private fun openCategoryScreen(){
        val args = Bundle().apply {
            putInt("categoryId", viewModel.categoryId)
            putInt("storeId", viewModel.storeId)
            putString("categoryName", viewModel.categoryName)
        }

        requireActivity().openOrRefreshFragment(
            forStore = true,
            destination = ProductsFragment(),
            args = args,
            name = "Products"
        )
    }

    private fun setLinkedStores() {
        with(binding.rvLinkedStores) {
            val mAdapter = HomeSponsorsAdapter(viewModel.sponsorStoresList)

            layoutManager = SpeedyLinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)

            adapter = mAdapter

            mAdapter.onItemClicked = { sponsor->
                if (viewModel.totalProductsOnDb > 0) {

                    // there is at least one product on cart -> ask for confirmation
                    alert(title = "", message = resources.getString(R.string.cart_confirm_change_store, getCurrentStoreName())){
                        yesButton {
                            viewModel.clearProductsFromCart()
                            it.dismiss()

                            Handler().postDelayed({
                                loadNewStoreOnActivity(sponsor)
                            }, 500)
                        }
                        noButton {
                            it.dismiss()
                        }
                    }.show()
                } else {
                    // there is no products on cart -> change the store normally
                    loadNewStoreOnActivity(sponsor)
                }
            }

            // Start 'animation' to scroll current horizontal scrollview
            val mainHandler = Handler(Looper.getMainLooper())
            currentState = ScrollPositions.Start

            // repeat the funcion every 'SPONSOR_DURATION' duration
            mainHandler.post(object : Runnable {
                override fun run() {
                    when(currentState){
                        ScrollPositions.Start -> sponsorsScrollToMiddle()
                        ScrollPositions.Middle -> sponsorsScrollToEnd()
                        else -> sponsorsScrollToStart()
                    }
                    mainHandler.postDelayed(this, SPONSOR_DURATION)
                }
            })
        }
    }

    private fun loadNewStoreOnActivity(sponsor: SponsorUI){
        val intent = Intent(requireActivity(), MainStoreActivity::class.java)
            .putExtra("storeId", sponsor.id)

        startActivityNoAnimation(intent, true)
    }

    private fun sponsorsScrollToStart(){
        binding.rvLinkedStores.smoothScrollToPosition(0)
        currentState = ScrollPositions.Start
    }

    private fun sponsorsScrollToMiddle(){
        binding.rvLinkedStores.smoothScrollToPosition(viewModel.sponsorStoresList.size/2)
        currentState = ScrollPositions.Middle
    }

    private fun sponsorsScrollToEnd(){
        try{
            binding.rvLinkedStores.smoothScrollToPosition(viewModel.sponsorStoresList.size - 1)
            currentState = ScrollPositions.End
        } catch (e: Exception){
            Log.e("--", "Exception on scrollToEnd: ${e.message}")
        }
    }

    private fun setUpVideo(urlVideo: String?) {
        if (urlVideo.isNullOrBlank()) {
            binding.layVideo.visibility = View.GONE
        } else {
            with(binding.videoStore) {
                setPlayPauseListener(object : CustomVideoView.PlayPauseListener{
                    override fun onPlay() {
                        binding.imgPhotoVideo.visibility = View.GONE
                    }

                    override fun onPause() {
                        binding.imgPhotoVideo.visibility = View.VISIBLE
                    }
                })

                setMediaController(MediaController(requireContext()))
                setVideoURI(Uri.parse(urlVideo))

                Handler().postDelayed({}, 0)
            }
            binding.imgPhotoVideo.bringToFront()
        }
    }

    private fun showErrorMsg() {
        with(viewModel.error) {
            val errorType = if (hasErrors) SnackType.ERROR else SnackType.SUCCESS
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