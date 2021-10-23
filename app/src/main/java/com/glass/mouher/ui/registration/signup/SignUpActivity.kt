package com.glass.mouher.ui.registration.signup

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import com.glass.mouher.R
import com.glass.mouher.BR
import com.glass.mouher.databinding.ActivitySignUpBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.utils.DatePickerHelper
import com.glass.mouher.utils.Validations
import com.glass.mouher.extensions.makeStatusBarTransparent
import com.glass.mouher.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class SignUpActivity : BaseActivity() {

    private val viewModel: SignUpViewModel by viewModel()
    private lateinit var binding: ActivitySignUpBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.backClicked -> finish()
                BR.error -> showErrorMsg()
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

    private fun setGenderSpinner(){
        val mAdapter = ArrayAdapter(this, R.layout.spinner_item_simple, viewModel.genderList)
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

        /** Registration success -> Go back to login screen */
        if(!viewModel.hasErrors){
            Handler().postDelayed({
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}