package com.glass.mouher.ui.checkout.address

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentAddressBinding
import com.glass.mouher.databinding.FragmentHistoryBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.history.HistoryViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AddressFragment : Fragment() {

    private val viewModel: AddressViewModel by viewModel()
    private lateinit var binding: FragmentAddressBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        binding.viewModel = viewModel
        binding.view = this

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}