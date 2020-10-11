package com.glass.mouher.ui.registration.signin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.glass.mouher.R
import com.glass.mouher.BR
import com.glass.mouher.databinding.ActivitySignInBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.MainActivityMall
import com.glass.mouher.ui.registration.forgot.ForgotPasswordActivity
import com.glass.mouher.ui.registration.signup.SignUpActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: ActivitySignInBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.mainMallScreen -> startActivity(Intent(this, MainActivityMall::class.java))
                BR.passwordScreen -> startActivity(Intent(this, SignUpActivity::class.java))
                BR.signupScreen -> startActivity(Intent(this, ForgotPasswordActivity::class.java))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)
        binding.viewModel = viewModel
        binding.view = this

        viewModel.onResume(onPropertyChangedCallback)
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