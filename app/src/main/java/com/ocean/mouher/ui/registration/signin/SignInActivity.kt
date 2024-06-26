package com.ocean.mouher.ui.registration.signin

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.ocean.mouher.BR
import com.ocean.mouher.R
import com.ocean.mouher.databinding.ActivitySignInBinding
import com.ocean.mouher.extensions.makeStatusBarTransparent
import com.ocean.mouher.shared.General.saveComesFromLogin
import com.ocean.mouher.shared.General.saveMustRefreshMenuMall
import com.ocean.mouher.shared.General.saveMustRefreshMenuStore
import com.ocean.mouher.ui.base.BaseActivity
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.common.showSnackbar
import com.ocean.mouher.ui.registration.forgot.ForgotPasswordActivity
import com.ocean.mouher.ui.registration.signup.SignUpActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : BaseActivity() {

    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: ActivitySignInBinding
    private lateinit var source: String
    private var comesFromCart = false

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.onBack -> closeScreen()
            BR.error -> showErrorMsg()
            BR.mainMallScreen -> closeScreen()
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

        comesFromCart = intent?.extras?.getBoolean("comesFromCart") ?: false
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

                closeScreen()
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

    private fun closeScreen() {
        if (comesFromCart) saveComesFromLogin(true)
        finish()
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        closeScreen()
    }
}