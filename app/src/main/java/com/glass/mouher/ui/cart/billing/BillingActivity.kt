package com.glass.mouher.ui.cart.billing

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.PaymentDataToSend
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityBillingBinding
import com.glass.mouher.extensions.startActivityNoAnimation
import com.glass.mouher.ui.base.BaseActivity
import com.glass.mouher.ui.cart.payment.PaymentActivity
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

class BillingActivity : BaseActivity() {

    private val viewModel: BillingViewModel by viewModel()
    private lateinit var binding: ActivityBillingBinding
    private lateinit var paymentData: PaymentDataToSend

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.onBack -> continueWithoutBilling()
                BR.error -> showErrorMsg()
                BR.continueBilling -> continueWithBilling()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        window?.statusBarColor = ContextCompat.getColor(this, R.color.onyxBlack)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_billing)
        binding.viewModel = viewModel

        paymentData = intent.extras?.getSerializable("paymentData") as PaymentDataToSend

    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun continueWithoutBilling(){
        val intent = Intent(this, PaymentActivity::class.java)
            .putExtra("paymentData", paymentData)

        startActivityNoAnimation(intent, true)
    }

    private fun continueWithBilling(){
        // redirect to payment Screen
        with(paymentData){
            requiresBilling = 1
            rfc = viewModel.rfc.trim()
            socialReason = viewModel.social.trim()
            email = viewModel.email.trim()
        }

        val intent = Intent(this, PaymentActivity::class.java)
            .putExtra("paymentData", paymentData)

        startActivityNoAnimation(intent, true)
    }

    private fun showErrorMsg(){
        showSnackbar(binding.root, viewModel.error, SnackType.INFO)
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