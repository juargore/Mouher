package com.glass.mouher.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.BR
import com.glass.mouher.databinding.FragmentAboutBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import org.koin.android.viewmodel.ext.android.viewModel

class AboutFragment : Fragment() {

    private val viewModel: AboutViewModel by viewModel()
    private lateinit var binding: FragmentAboutBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.peopleList -> setUpRecyclerPeople()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel

        arguments?.let{
            val storeId = it.getInt("storeId")
            viewModel.initialize(storeId)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setUpRecyclerPeople(){
        with(binding.rvPeople){
            layoutManager = LinearLayoutManager(requireContext())
            val adapter1 = AboutPeopleAdapter(viewModel.peopleList)
            adapter = adapter1
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}