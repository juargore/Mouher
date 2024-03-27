@file:Suppress("DEPRECATION")

package com.ocean.mouher.ui.registration.forgot

import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.ocean.mouher.BR
import com.ocean.mouher.R
import com.ocean.mouher.databinding.ActivityForgotPasswordBinding
import com.ocean.mouher.extensions.makeStatusBarTransparent
import com.ocean.mouher.ui.base.BaseActivity
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.common.showSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : BaseActivity() {

    private val viewModel: ForgotPasswordViewModel by viewModel()
    private lateinit var binding: ActivityForgotPasswordBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.backClicked -> finish()
            BR.error -> showErrorMsg()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        binding.viewModel = viewModel
        binding.view = this

        makeStatusBarTransparent()
    }

    private fun showErrorMsg(){
        val type = if(viewModel.hasError) SnackType.ERROR else SnackType.SUCCESS
        showSnackbar(binding.root, viewModel.error, type)

        /** Recover success -> Go back to login screen */
        if(!viewModel.hasError){
            Handler().postDelayed({
                this@ForgotPasswordActivity.finish()
            }, 1500)
        }
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