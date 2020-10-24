package com.glass.mouher.ui.mall.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentHomeMallBinding
import com.glass.mouher.extensions.startFadeInAnimation
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.ui.mall.home.adapters.HomeLobbyAdapter
import com.glass.mouher.ui.mall.home.adapters.HomeSponsorsAdapter
import com.glass.mouher.ui.mall.home.adapters.HomeZonesAdapter
import com.glass.mouher.ui.mall.home.stores.StoresFragment
import com.synnapps.carouselview.ImageListener
import org.koin.android.viewmodel.ext.android.viewModel

class HomeMallFragment : Fragment() {

    private val viewModel: HomeMallViewModel by viewModel()
    private lateinit var binding: FragmentHomeMallBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.topBannerList -> setImagesInTopBanner()
                BR.sponsorStoresList -> setUpSponsorStoresRecycler()
                BR.lobbyList -> setUpLobbyRecycler()
                BR.zonesList -> setUpZonesRecycler()
                BR.error -> showErrorMsg()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_mall, container, false)
        binding.viewModel = viewModel
        binding.view = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setUpSponsorStoresRecycler(){
        val adapter = HomeSponsorsAdapter(viewModel.sponsorStoresList)

        binding.rvHomeSponsors.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false)

        binding.rvHomeSponsors.adapter = adapter
    }

    private fun setUpZonesRecycler() {
        val adapter = HomeZonesAdapter(viewModel.zonesList)

        binding.rvHomeZones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeZones.adapter = adapter

        adapter.onItemClicked = { item->
            val args = Bundle().apply {
                putString("zoneName", item.name)
            }

            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(R.id.container_body_mall, StoresFragment().apply {
                    arguments = args
                })

                addToBackStack("Stores")
                commit()
            }
        }
    }

    private fun setUpLobbyRecycler() {
        val adapter = HomeLobbyAdapter(viewModel.lobbyList)

        binding.rvHomeLobby.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeLobby.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    private fun setImagesInTopBanner(){
        with(binding.carouselView){
            setImageListener(imageListener)
            pageCount = viewModel.topBannerList.size

            addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

                override fun onPageSelected(position: Int) {}
                override fun onPageScrollStateChanged(state: Int) {}

                override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixels: Int) {
                    binding.carouselTitle.apply {
                        text = viewModel.topBannerList[pos].title
                        startFadeInAnimation()
                    }
                    binding.carouselSubtitle.apply {
                        text = viewModel.topBannerList[pos].subtitle
                        startFadeInAnimation()
                    }
                }
            })
        }
    }

    private var imageListener: ImageListener = ImageListener { position, imageView ->
        Glide.with(requireContext())
            .load(viewModel.topBannerList[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }

    private fun showErrorMsg(){
        showSnackbar(binding.root, viewModel.error, SnackType.ERROR)
    }
}