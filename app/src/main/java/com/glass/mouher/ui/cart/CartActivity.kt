package com.glass.mouher.ui.cart

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.glass.domain.entities.PaymentDataToSend
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityCartBinding
import com.glass.mouher.extensions.startActivityNoAnimation
import com.glass.mouher.shared.General.getCartNotes
import com.glass.mouher.shared.General.getUserId
import com.glass.mouher.shared.General.saveCartNotes
import com.glass.mouher.shared.General.saveMustRefreshStore
import com.glass.mouher.ui.base.BaseActivity
import com.glass.mouher.ui.cart.billing.BillingActivity
import com.glass.mouher.ui.cart.payment.PaymentActivity
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.common.showSnackbar
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import org.koin.android.viewmodel.ext.android.viewModel

class CartActivity : BaseActivity() {

    private val viewModel: CartViewModel by viewModel()
    private lateinit var binding: ActivityCartBinding

    private val onPropertyChangedCallback = propertyChangedCallback { _, propertyId ->
        when (propertyId) {
            BR.error -> showErrorMsg()
            BR.onBackClicked -> finish()
            BR.onPopClicked -> showPopUpNotes()
            BR.askForBilling -> showPopUpConfirmContinue()
            BR.deleteItem -> showPopupDeleteConfirmation()
            BR.onRefreshScreen -> {
                finish()
                overridePendingTransition( 0, 0)
                startActivity(intent)
            }
            BR.onFinishScreen -> {
                saveMustRefreshStore(true)
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        /** change satus bar color only for Cart screen */
        window?.statusBarColor = ContextCompat.getColor(this, R.color.onyxBlack)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.viewModel = viewModel
        binding.view = this

        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun showPopUpNotes(){
        Dialog(this, R.style.FullDialogTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.pop_add_notes)

            val etRemarks = findViewById<EditText>(R.id.etRemarks)
            val savedNotes = getCartNotes()

            if(!savedNotes.isNullOrBlank()){
                etRemarks.setText(savedNotes)
            }

            findViewById<ImageView>(R.id.imgCloseRemarks).setOnClickListener {
                this.dismiss()
            }

            findViewById<AppCompatButton>(R.id.btnSaveRemark).setOnClickListener {
                saveCartNotes(etRemarks.text.toString())
                this.dismiss()
            }

            show()
        }
    }

    private fun showPopUpConfirmContinue(){
        alert(title = "", message = resources.getString(R.string.cart_confirm_continue)) {
            yesButton { it.dismiss(); showPopUpBilling() }
            noButton { it.dismiss() }
        }
            .show()
            .setCancelable(false)
    }

    private fun showPopUpBilling(){
        Dialog(this, R.style.FullDialogTheme).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setContentView(R.layout.pop_need_billing)

            val productList = viewModel.itemsComplete
            val idStore = productList[0].storeId ?: 0
            val notes = getCartNotes() ?: ""
            val userId = getUserId()
            val subTotal = viewModel.subTotalAmount
            val shipping = viewModel.shippingAmount
            val total = viewModel.totalAmount

            val data = PaymentDataToSend(
                storeId = idStore,
                remarks = notes,
                clientId = userId,
                subTotalCost = subTotal,
                shippingCost = shipping,
                totalCost = total,
                requiresBilling = 0,
                rfc = null,
                socialReason = null,
                email = null,
                products = productList
            )

            /** redirect to billing screen */
            findViewById<AppCompatButton>(R.id.btnAddBilling).setOnClickListener {
                val intent = Intent(this@CartActivity, BillingActivity::class.java)
                    .putExtra("paymentData", data)

                startActivityNoAnimation(intent)
                this.dismiss()
            }

            /** redirect to payment Screen */
            findViewById<AppCompatButton>(R.id.btnDiscardBilling).setOnClickListener {
                val intent = Intent(this@CartActivity, PaymentActivity::class.java)
                    .putExtra("paymentData", data)

                startActivityNoAnimation(intent)
                this.dismiss()
            }

            setCancelable(false)
            show()
        }
    }

    private fun showPopupDeleteConfirmation(){
        alert(title = "", message = "¿Está seguro que desea eliminar este registro?"){
            yesButton { viewModel.onDeleteItemClicked(viewModel.deleteItem ?: 0) }
        }.show()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    private fun showErrorMsg(){
        showSnackbar(binding.root, viewModel.error, viewModel.snackType)
    }

    fun itemViewBinder(): ItemBinder<ACartListViewModel> {
        return CompositeItemBinder(CartItemBinder(BR.viewModel, R.layout.recycler_item_cart))
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