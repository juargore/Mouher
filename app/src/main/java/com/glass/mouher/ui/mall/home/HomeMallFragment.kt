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
import com.glass.mouher.ui.common.propertyChangedCallback
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
                BR.bannerList -> setImagesInBanner()
                BR.sponsorsList -> setUpSponsorsRecycler()
                BR.lobbyList -> setUpLobbyRecycler()
                BR.zonesList -> setUpZonesRecycler()
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

    private fun setUpSponsorsRecycler(){
        val adapter = HomeSponsorsAdapter(requireContext(), viewModel.sponsorsList,
            object: HomeSponsorsAdapter.InterfaceOnClick{
                override fun onItemClick(pos: Int) {

                }
            })

        binding.rvHomeSponsors.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvHomeSponsors.adapter = adapter
    }

    private fun setUpZonesRecycler() {
        val adapter = HomeZonesAdapter(requireContext(), viewModel.zonesList,
        object: HomeZonesAdapter.InterfaceOnClick{
            override fun onItemClick(pos: Int) {

                val args = Bundle().apply {
                    putString("zoneName", viewModel.zonesList[pos].name)
                }

                requireActivity().supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container_body_mall, StoresFragment().apply {
                        arguments = args
                    })
                    addToBackStack("Stores")
                    commit()
                }
            }
        })

        binding.rvHomeZones.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeZones.adapter = adapter
    }

    private fun setUpLobbyRecycler() {
        val adapter = HomeLobbyAdapter(requireContext(), viewModel.lobbyList,
            object: HomeLobbyAdapter.InterfaceOnClick{
                override fun onItemClick(pos: Int) {

                }
            })

        binding.rvHomeLobby.layoutManager = LinearLayoutManager(requireContext())
        binding.rvHomeLobby.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    private fun setImagesInBanner(){
        binding.carouselView.setImageListener(imageListener)
        binding.carouselView.pageCount = viewModel.bannerList.size

        binding.carouselView.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixels: Int) {
                binding.carouselTitle.apply {
                    this.startFadeInAnimation()
                    this.text = viewModel.bannerList[pos].name
                }
                binding.carouselSubtitle.apply {
                    this.startFadeInAnimation()
                    this.text = viewModel.bannerList[pos].description
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
}