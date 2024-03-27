package com.ocean.mouher.ui.history.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.ocean.mouher.databinding.FragmentHistoryDetailsBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("SetJavaScriptEnabled")
class HistoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentHistoryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHistoryDetailsBinding.inflate(inflater, container, false)

        with(binding.webView){
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            val url = arguments?.getString("url") ?: ""
            loadUrl(url)

            lifecycleScope.launch {
                delay(3000L)
                binding.progress.visibility = View.GONE
            }
        }

        binding.backWebView.setOnClickListener {
            activity?.onBackPressed()
        }

        return binding.root
    }
}