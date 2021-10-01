package com.glass.mouher.ui.cart.billing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityBillingBinding
import com.glass.mouher.ui.common.propertyChangedCallback
import org.koin.android.viewmodel.ext.android.viewModel

class BillingActivity : AppCompatActivity() {

    private val viewModel: BillingViewModel by viewModel()
    private lateinit var binding: ActivityBillingBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.onBack -> finish()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        window?.statusBarColor = ContextCompat.getColor(this, R.color.onyxBlack)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_billing)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }
}