package com.glass.mouher.ui.profile.personal

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.glass.mouher.BR
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentPersonalBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.viewmodel.ext.android.viewModel

class PersonalFragment : Fragment() {

    private val viewModel: PersonalViewModel by viewModel()
    private lateinit var binding: FragmentPersonalBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.genderList -> setGenderSpinner()
                BR.error -> showErrorMsg()
                BR.backClicked -> activity?.onBackPressed()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_personal, container, false)
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun setGenderSpinner(){
        val mAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_simple, viewModel.genderList)
        spinnerGender.adapter = mAdapter
        spinnerGender.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                viewModel.gender = pos
            }
        }
    }

    private fun showErrorMsg(){
        val type = if(viewModel.hasErrors) SnackType.ERROR else SnackType.SUCCESS

        showSnackbar(binding.root, viewModel.error, type)

        if(!viewModel.hasErrors){
            // Registration success -> Go back to login screen
            Handler().postDelayed({
                activity?.onBackPressed()
            }, 1000)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}