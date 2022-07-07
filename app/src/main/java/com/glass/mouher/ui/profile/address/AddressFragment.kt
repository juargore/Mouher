@file:Suppress("DEPRECATION")
package com.glass.mouher.ui.profile.address

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.glass.domain.entities.Country
import com.glass.domain.entities.State
import com.glass.mouher.databinding.FragmentAddressBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

class AddressFragment : Fragment() {

    private val viewModel: AddressViewModel by viewModel()
    private lateinit var binding: FragmentAddressBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.error -> showErrorMsg()
            BR.statesList -> setStatesSpinner()
            BR.countriesList -> setCountriesSpinner()
            BR.backClicked -> activity?.onBackPressed()
        }
    }

    override fun onCreateView(infl: LayoutInflater, cont: ViewGroup?, state: Bundle?): View {
        binding = FragmentAddressBinding.inflate(infl, cont, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setCountriesSpinner() {
        with(binding.spinnerCountries) {
            adapter = CountryStateAdapter(requireContext(), viewModel.countriesList)
            if (viewModel.selectedCountry != null) setSelection(1)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    val item = getItemAtPosition(pos) as Country
                    viewModel.selectedCountry = item
                    if (viewModel.selectedCountry!!.IdPais!! > 0) {
                        viewModel.getStatesByCountryId(item.IdPais ?: 117) // Mexico's Id
                    }
                }
            }
        }
    }

    private fun setStatesSpinner() {
        with (binding.spinnerStates) {
            adapter = CountryStateAdapter(requireContext(), viewModel.statesList)
            if (viewModel.selectedState != null) setSelection(viewModel.selectedState!!.IdEstado!!)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    viewModel.selectedState = getItemAtPosition(pos) as State
                }
            }
        }
    }

    private fun showErrorMsg(){
        val type = if(viewModel.hasErrors) SnackType.ERROR else SnackType.SUCCESS
        showSnackbar(binding.root, viewModel.error, type)
        if (!viewModel.hasErrors) {
            // address registration success -> Go back to previous screen
            Handler().postDelayed({ activity?.onBackPressed() }, 1500)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}