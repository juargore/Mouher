package com.glass.mouher.ui.mall.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.databinding.FragmentHomeMallBinding
import com.glass.mouher.extensions.openOrRefreshFragment
import com.glass.mouher.extensions.startActivityNoAnimation
import com.glass.mouher.shared.General.getComesFromStores
import com.glass.mouher.shared.General.saveComesFromStores
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.ui.mall.MainActivityMall
import com.glass.mouher.ui.mall.MainActivityMall.Companion.showHideCartIcon
import com.glass.mouher.ui.mall.home.adapters.HomeLobbyAdapter
import com.glass.mouher.ui.mall.home.adapters.HomeSponsorsAdapter
import com.glass.mouher.ui.mall.home.adapters.HomeZonesAdapter
import com.glass.mouher.ui.mall.home.stores.StoresFragment
import com.glass.mouher.ui.store.MainStoreActivity
import com.glass.mouher.utils.Constants.SPONSOR_DURATION
import com.glass.mouher.utils.Constants.ScrollPositions
import com.glass.mouher.utils.SpeedyLinearLayoutManager
import com.glass.mouher.utils.WebBrowserUtils.openUrlInExternalWebBrowser
import org.koin.android.viewmodel.ext.android.viewModel

class HomeMallFragment : Fragment() {

    private val viewModel: HomeMallViewModel by viewModel()
    private lateinit var binding: FragmentHomeMallBinding
    private var currentState = ScrollPositions.Middle

    lateinit var carouselTitle: TextView
    lateinit var carouselSubTitle: TextView

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.sponsorStoresList -> setUpSponsorStoresRecycler()
                BR.lobbyList -> setUpLobbyRecycler()
                BR.zonesList -> setUpZonesRecycler()
                BR.error -> showErrorMsg()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeMallBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.view = this

        carouselTitle = binding.carouselTitle
        carouselSubTitle = binding.carouselSubtitle

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
        showHideCartIcon(true)
    }

    private fun setUpSponsorStoresRecycler() {
        with(binding.rvHomeSponsors){
            val mAdapter = HomeSponsorsAdapter(viewModel.sponsorStoresList)

            layoutManager = SpeedyLinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false)

            adapter = mAdapter

            mAdapter.onItemClicked = { sponsor->
                startActivityNoAnimation(
                    Intent(requireActivity(), MainStoreActivity::class.java)
                        .putExtra("storeId", sponsor.id)
                )
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

    private fun sponsorsScrollToStart() {
        binding.rvHomeSponsors.smoothScrollToPosition(0)
        binding.rvHomeSponsors.isNestedScrollingEnabled = false
        currentState = ScrollPositions.Start
    }

    private fun sponsorsScrollToMiddle() {
        binding.rvHomeSponsors.smoothScrollToPosition(viewModel.sponsorStoresList.size/2)
        binding.rvHomeSponsors.isNestedScrollingEnabled = false
        currentState = ScrollPositions.Middle
    }

    private fun sponsorsScrollToEnd() {
        try {
            binding.rvHomeSponsors.smoothScrollToPosition(viewModel.sponsorStoresList.size - 1)
            binding.rvHomeSponsors.isNestedScrollingEnabled = false
            currentState = ScrollPositions.End
        } catch (e: Exception) {
            Log.e("--", "Exception on scrollToEnd: ${e.message}")
        }
    }

    private fun setUpZonesRecycler() {
        with(binding.rvHomeZones){
            val mAdapter = HomeZonesAdapter(viewModel.zonesList)

            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter

            mAdapter.onItemClicked = { item ->
                val args = Bundle().apply {
                    putString("zoneName", item.name)
                    putString("zoneId", item.id.toString())
                }

                requireActivity().openOrRefreshFragment(
                    forStore = false,
                    destination = StoresFragment(),
                    args = args,
                    name = "Stores"
                )
            }
        }

        if (getComesFromStores()) {
            saveComesFromStores(false)
            binding.nestedScrollView.post {
                binding.nestedScrollView.fullScroll(View.FOCUS_DOWN)
            }
        }
    }

    private fun setUpLobbyRecycler() {
        with(binding.rvHomeLobby) {
            val mAdapter = HomeLobbyAdapter(viewModel.lobbyList)

            layoutManager = LinearLayoutManager(requireContext())
            adapter = mAdapter

            mAdapter.onItemClicked = {
                it.linkToOpen?.let { url->
                    openUrlInExternalWebBrowser(url)
                }
            }
        }
    }

    private fun showErrorMsg() {
        showSnackbar(binding.root, viewModel.error, SnackType.ERROR)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
        showHideCartIcon(false)
    }
}