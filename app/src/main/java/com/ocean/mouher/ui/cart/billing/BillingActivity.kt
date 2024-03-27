package com.ocean.mouher.ui.cart.billing

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.ocean.domain.entities.ParcelData
import com.ocean.domain.entities.PaymentDataToSend
import com.ocean.mouher.R
import com.ocean.mouher.databinding.ActivityBillingBinding
import com.ocean.mouher.extensions.startActivityNoAnimation
import com.ocean.mouher.ui.base.BaseActivity
import com.ocean.mouher.ui.cart.payment.PaymentActivity
import com.ocean.mouher.ui.common.SnackType
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.common.showSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

class BillingActivity : BaseActivity() {

    private val viewModel: BillingViewModel by viewModel()
    private lateinit var binding: ActivityBillingBinding
    private lateinit var paymentData: PaymentDataToSend
    private var parcelSelected: ParcelData? = null

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
        parcelSelected = intent.extras?.getSerializable("parcel") as ParcelData?
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun continueWithoutBilling() {
        val intent = Intent(this, PaymentActivity::class.java)
            .putExtra("paymentData", paymentData)
            .putExtra("parcel", parcelSelected)

        startActivityNoAnimation(intent, null, true)
    }

    private fun continueWithBilling() {
        // redirect to payment Screen
        with(paymentData) {
            requiresBilling = 1
            rfc = viewModel.rfc.trim()
            socialReason = viewModel.social.trim()
            email = viewModel.email.trim()
        }

        val intent = Intent(this, PaymentActivity::class.java)
            .putExtra("paymentData", paymentData)
            .putExtra("parcel", parcelSelected)

        startActivityNoAnimation(intent, null, true)
    }

    private fun showErrorMsg() {
        showSnackbar(binding.root, viewModel.error, SnackType.ERROR)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}