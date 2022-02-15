package com.glass.mouher.ui.registration.signin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.glass.mouher.BR
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivitySignInBinding
import com.glass.mouher.extensions.makeStatusBarTransparent
import com.glass.mouher.shared.General.saveMustRefreshMenuMall
import com.glass.mouher.shared.General.saveMustRefreshMenuStore
import com.glass.mouher.ui.base.BaseActivity
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.ui.registration.forgot.ForgotPasswordActivity
import com.glass.mouher.ui.registration.signup.SignUpActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {

    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: ActivitySignInBinding
    private lateinit var source: String

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.onBack -> finish()
            BR.error -> showErrorMsg()
            BR.mainMallScreen -> finish()
            BR.signupScreen -> startActivity(SignUpActivity())
            BR.passwordScreen -> startActivity(ForgotPasswordActivity())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.viewModel = viewModel
        binding.view = this

        makeStatusBarTransparent()

        source = intent?.extras?.getString("source") ?: "MALL"
    }

    private fun showErrorMsg(){
        val type = if(viewModel.hasErrors) SnackType.ERROR else SnackType.SUCCESS
        showSnackbar(binding.root, viewModel.error, type)

        /** Registration success -> Go back to login screen */
        if (!viewModel.hasErrors) {
            Handler().postDelayed( {
                saveMustRefreshMenuMall(true)

                if (source != "MALL")
                    saveMustRefreshMenuStore(true)

                this@SignInActivity.finish()
            }, 2000)
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

    private fun<T> startActivity(activity: T){
        activity?.let{
            startActivity(Intent(this, it::class.java))
        }
    }
}