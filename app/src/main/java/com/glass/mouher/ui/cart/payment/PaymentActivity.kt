package com.glass.mouher.ui.cart.payment

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.PaymentDataToSend
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityPaymentBinding
import com.glass.mouher.shared.General.savePaymentInfo
import com.glass.mouher.ui.common.SnackType
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import org.koin.android.viewmodel.ext.android.viewModel

class PaymentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPaymentBinding
    private val viewModel: PaymentViewModel by viewModel()
    private var dialog: Dialog? = null
    private var storeId = 0

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.onBack -> finish()
            BR.error -> showErrorMsg()
            BR.showDialog -> if(viewModel.showDialog) dialog?.show() else dialog?.dismiss()
            BR.startLoadingWebPage -> loadWebPage()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        window?.statusBarColor = ContextCompat.getColor(this, R.color.onyxBlack)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        binding.viewModel = viewModel

        val data = intent.extras?.getSerializable("paymentData") as PaymentDataToSend
        storeId = data.storeId
        viewModel.initialize(data)
    }

    override fun onStart() {
        super.onStart()

        dialog = Dialog(this, R.style.FullDialogTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.pop_processing_payment)
            setCancelable(false)
        }

        viewModel.startCreatingPayment()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun loadWebPage(){

        val url = "https://mouhermarket.com/venta-pago.php?IdTienda=$storeId&IdVenta=${viewModel.saleId}"

        with(binding.webView){
            settings.javaScriptEnabled = true
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true

            webViewClient = CustomWebViewClient()
            loadUrl(url)

            // save the payment data in format comesFromPayment-storeId-saleId (true-01-01)
            savePaymentInfo("true-$storeId-${viewModel.saleId}")
        }
    }

    private fun showErrorMsg(){
        val type = if(viewModel.hasErrors) SnackType.ERROR else SnackType.SUCCESS
        showSnackbar(binding.root, viewModel.error, type)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }
}

class CustomWebViewClient: WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        // do not open external browser to load url -> load it in this webview
        return false
    }
}