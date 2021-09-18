package com.glass.mouher.ui.registration.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.glass.mouher.BR
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivitySignInBinding
import com.glass.mouher.shared.General.saveMustRefreshMenuMall
import com.glass.mouher.shared.General.saveMustRefreshMenuStore
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import com.glass.mouher.ui.registration.forgot.ForgotPasswordActivity
import com.glass.mouher.ui.registration.signup.SignUpActivity
import com.glass.mouher.extensions.makeStatusBarTransparent
import org.koin.android.viewmodel.ext.android.viewModel

class SignInActivity : AppCompatActivity() {

    private val viewModel: SignInViewModel by viewModel()
    private lateinit var binding: ActivitySignInBinding
    private lateinit var source: String

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.mainMallScreen -> finish()
            BR.error -> showErrorMsg()
            BR.onBack -> finish()
            BR.passwordScreen -> startActivity(ForgotPasswordActivity())
            BR.signupScreen -> startActivity(SignUpActivity())
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

        if(!viewModel.hasErrors){
            // Registration success -> Go back to login screen
            Handler().postDelayed({
                if(source == "MALL"){
                    saveMustRefreshMenuMall(true)
                }else{
                    saveMustRefreshMenuStore(true)
                    saveMustRefreshMenuMall(true)
                }

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

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}