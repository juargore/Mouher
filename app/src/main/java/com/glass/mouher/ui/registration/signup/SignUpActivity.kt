package com.glass.mouher.ui.registration.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.glass.mouher.R
import com.glass.mouher.BR
import com.glass.mouher.databinding.ActivitySignUpBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpActivity : AppCompatActivity() {

    private val viewModel: SignUpViewModel by viewModel()
    private lateinit var binding: ActivitySignUpBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.backClicked -> finish()
                BR.error -> showErrorMsg()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        binding.viewModel = viewModel
        binding.view = this

        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun showErrorMsg(){
        showSnackbar(binding.root, viewModel.error, SnackType.ERROR)
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