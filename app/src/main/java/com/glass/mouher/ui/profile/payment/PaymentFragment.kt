package com.glass.mouher.ui.profile.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.glass.mouher.databinding.FragmentPaymentBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import org.koin.android.viewmodel.ext.android.viewModel

class PaymentFragment : Fragment() {

    private val onPropertyChangedCallback = propertyChangedCallback { _, _ -> }
    private val viewModel: PaymentViewModel by viewModel()
    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

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