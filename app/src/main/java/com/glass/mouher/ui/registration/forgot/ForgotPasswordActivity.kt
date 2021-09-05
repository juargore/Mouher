package com.glass.mouher.ui.registration.forgot

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.glass.mouher.R
import com.glass.mouher.BR
import com.glass.mouher.databinding.ActivityForgotPasswordBinding
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.utils.makeStatusBarTransparent
import org.koin.android.viewmodel.ext.android.viewModel

class ForgotPasswordActivity : AppCompatActivity() {

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

        if(!viewModel.hasError){
            // Recover success -> Go back to login screen
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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}