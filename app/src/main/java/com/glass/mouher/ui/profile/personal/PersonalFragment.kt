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
import com.glass.mouher.utils.DatePickerHelper
import com.glass.mouher.utils.Validations
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class PersonalFragment : Fragment() {

    private val viewModel: PersonalViewModel by viewModel()
    private lateinit var binding: FragmentPersonalBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.genderList -> setGenderSpinner()
                BR.error -> showErrorMsg()
                BR.backClicked -> activity?.onBackPressed()
                BR.birthDateClicked -> showDatePickerDialog()
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
        with(binding.spinnerGender){
            val mAdapter = ArrayAdapter(requireContext(), R.layout.spinner_item_simple, viewModel.genderList)
            adapter = mAdapter

            if(viewModel.gender > 0){
                setSelection(viewModel.gender)
            }

            onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    viewModel.gender = pos
                }
            }
        }
    }

    private fun showDatePickerDialog() {
        DatePickerHelper(requireContext(), spinnerMode = true, shouldRemoveEighteen = true)
            .showDialog(viewModel.day, viewModel.month-1, viewModel.year,
                object : DatePickerHelper.Callback {
                    override fun onDateSelected(day: Int, month: Int, year: Int) {
                        val monInt = month + 1
                        val dayStr = if(day < 10) "0$day" else "$day"
                        val monthStr = if(monInt < 10) "0$monInt" else "$monInt"

                        val date = "${day}-${monInt}-${year}"
                        val dateStr = "${year}-${monthStr}-${dayStr}"

                        viewModel.birthDate = dateStr
                        viewModel.birthDateStr = Validations.toPrettyDate(requireContext(), date, Locale("es"))
                    }
                })
    }

    private fun showErrorMsg(){
        val type = if(viewModel.hasErrors) SnackType.ERROR else SnackType.SUCCESS

        showSnackbar(binding.root, viewModel.error, type)

        if(!viewModel.hasErrors){
            // Registration success -> Go back to login screen
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