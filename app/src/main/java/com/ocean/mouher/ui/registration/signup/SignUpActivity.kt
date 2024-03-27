@file:Suppress("DEPRECATION")

package com.ocean.mouher.ui.registration.signup

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.ocean.mouher.BR
import com.ocean.mouher.R
import com.ocean.mouher.databinding.ActivitySignUpBinding
import com.ocean.mouher.extensions.makeStatusBarTransparent
import com.ocean.mouher.ui.base.BaseActivity
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.common.showSnackbar
import com.ocean.mouher.utils.DatePickerHelper
import com.ocean.mouher.utils.Validations
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class SignUpActivity : BaseActivity() {

    private val viewModel: SignUpViewModel by viewModel()
    private lateinit var binding: ActivitySignUpBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.backClicked -> finish()
                BR.error -> showErrorMessage()
                BR.genderList -> setGenderSpinner()
                BR.birthDateClicked -> showDatePickerDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.viewModel = viewModel
        binding.view = this

        makeStatusBarTransparent()
    }

    private fun setGenderSpinner() {
        with(binding.spinnerGender) {
            adapter = ArrayAdapter(this@SignUpActivity, R.layout.spinner_item_simple, viewModel.genderList)
            onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>?, v: View?, pos: Int, id: Long) {
                    viewModel.gender = pos
                }
            }
        }
    }

    private fun showErrorMessage() {
        val type = if(viewModel.hasErrors) SnackType.ERROR else SnackType.SUCCESS
        showSnackbar(binding.root, viewModel.error, type)

        /** Registration success -> Go back to login screen */
        if (!viewModel.hasErrors) {
            Handler().postDelayed( {
                this@SignUpActivity.finish()
            }, 1500)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun showDatePickerDialog() {
        DatePickerHelper(this, spinnerMode = true, shouldRemoveEighteen = true)
            .showDialog(viewModel.day, viewModel.month, viewModel.year,
                object : DatePickerHelper.Callback {
                    override fun onDateSelected(day: Int, month: Int, year: Int) {
                        val monInt = month + 1
                        val dayStr = if(day < 10) "0$day" else "$day"
                        val monthStr = if(monInt < 10) "0$monInt" else "$monInt"

                        val date = "${day}-${monInt}-${year}"
                        val dateStr = "${year}-${monthStr}-${dayStr}"

                        viewModel.birthDate = dateStr
                        viewModel.birthDateStr = Validations
                            .toPrettyDate(this@SignUpActivity, date, Locale("es"))
                    }
                })
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}