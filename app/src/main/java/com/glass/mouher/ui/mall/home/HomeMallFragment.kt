package com.glass.mouher.ui.mall.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.glass.mouher.ui.store.MainStoreActivity
import com.glass.mouher.utils.WebBrowserUtils.openUrlInExternalWebBrowser
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
    ): View {

        binding = FragmentHomeMallBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setUpSponsorStoresRecycler(){
        with(binding.rvHomeSponsors){
            val mAdapter = HomeSponsorsAdapter(viewModel.sponsorStoresList)

            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)

            adapter = mAdapter

            mAdapter.onItemClicked = { sponsor->
                val intent = Intent(requireActivity(), MainStoreActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
                    putExtra("storeId", sponsor.id)
                }

                activity?.overridePendingTransition(0,0)
                startActivity(intent)
            }
        }
    }

    private fun setUpZonesRecycler() {
        with(binding.rvHomeZones){
            val mAdapter = HomeZonesAdapter(viewModel.zonesList)

            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter

            mAdapter.onItemClicked = { item->
                val args = Bundle().apply {
                    putString("zoneName", item.name)
                    putString("zoneId", item.id.toString())
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
    }

    private fun setUpLobbyRecycler() {
        with(binding.rvHomeLobby){
            val mAdapter = HomeLobbyAdapter(viewModel.lobbyList)

            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter

            mAdapter.onItemClicked={
                it.linkToOpen?.let{ url->
                    openUrlInExternalWebBrowser(url)
                }
            }
        }
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

            setImageClickListener { position->
                val item = viewModel.topBannerList[position]
                if(!item.linkToOpen.isNullOrBlank()){
                    openUrlInExternalWebBrowser(item.linkToOpen!!)
                }
            }
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

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}