package com.glass.mouher.ui.registration.splash

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.glass.mouher.BR
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivitySplashBinding
import com.glass.mouher.ui.base.BaseActivity
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.mall.MainActivityMall
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModel()

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.nextScreen -> {
                startActivity(Intent(this, MainActivityMall::class.java))
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.SplashScreenTheme)
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.viewModel = viewModel

        // Set the sys ui flags to enable us to lay out in the window insets
        binding.root.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
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