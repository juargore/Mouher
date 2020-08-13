package com.glass.mouher.ui.mall.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentHomeBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.home.adapters.HomeSponsorsAdapter
import com.glass.mouher.ui.mall.home.adapters.HomeZonesAdapter
import com.synnapps.carouselview.ImageListener
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()
    private lateinit var binding: FragmentHomeBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.bannerList -> setImagesInBanner()
                BR.sponsorsList -> setUpSponsorsRecycler()
                BR.zonesList -> setUpZonesRecycler()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.view = this

        activity?.findViewById<ImageView>(R.id.icBackHome)?.visibility = View.GONE

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
                findNavController().navigate(HomeFragmentDirections.actionHomeToZones(viewModel.zonesList[pos].name!!))
            }
        })

        binding.rvHomeZones.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        binding.rvHomeZones.adapter = adapter
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    private fun setImagesInBanner(){
        binding.carouselView.setImageListener(imageListener)
        binding.carouselView.pageCount = viewModel.bannerList.size
    }

    private var imageListener: ImageListener = ImageListener { position, imageView ->
        Glide.with(requireContext())
            .load(viewModel.bannerList[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }
}