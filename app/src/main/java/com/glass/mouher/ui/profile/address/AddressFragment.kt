package com.glass.mouher.ui.profile.address

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import com.glass.domain.entities.Country
import com.glass.domain.entities.State
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentAddressBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

class AddressFragment : Fragment() {

    private val viewModel: AddressViewModel by viewModel()
    private lateinit var binding: FragmentAddressBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.countriesList -> setCountriesSpinner()
                BR.statesList -> setStatesSpinner()
                BR.error -> showErrorMsg()
                BR.backClicked -> activity?.onBackPressed()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setCountriesSpinner(){
        with(binding.spinnerCountries){
            val mAdapter = CountryStateAdapter(requireContext(), viewModel.countriesList)
            adapter = mAdapter

            if(viewModel.selectedCountry != null){
                setSelection(viewModel.selectedCountry!!.IdPais!!)
            }

            onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    val item = getItemAtPosition(pos) as Country
                    viewModel.selectedCountry = item

                    if(viewModel.selectedCountry!!.IdPais!! > 0){
                        viewModel.getStatesByCountryId(item.IdPais ?: 117) // Id of Mexico
                    }
                }
            }
        }
    }

    private fun setStatesSpinner(){
        with(binding.spinnerStates){
            val mAdapter = CountryStateAdapter(requireContext(), viewModel.statesList)
            adapter = mAdapter

            if(viewModel.selectedState != null){
                setSelection(viewModel.selectedState!!.IdEstado!!)
            }

            onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    val item = getItemAtPosition(pos) as State
                    viewModel.selectedState = item
                    Log.e("--", "Id state: ${item.IdEstado}")
                }
            }
        }
    }

    private fun showErrorMsg(){
        val type = if(viewModel.hasErrors) SnackType.ERROR else SnackType.SUCCESS

        showSnackbar(binding.root, viewModel.error, type)

        if(!viewModel.hasErrors){
            // Address registration success -> Go back to previous screen
            Handler().postDelayed({
                activity?.onBackPressed()
            }, 1500)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}