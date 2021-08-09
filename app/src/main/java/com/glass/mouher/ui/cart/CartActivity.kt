package com.glass.mouher.ui.cart

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.ActivityCartBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import org.koin.android.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {

    private val viewModel: CartViewModel by viewModel()
    private lateinit var binding: ActivityCartBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.onBackClicked -> finish()
                BR.onPopClicked -> showPopUpNotes()
                BR.deleteItem -> showPopupDeleteConfirmation()
                BR.onRefreshScreen -> {
                    finish()
                    overridePendingTransition( 0, 0)
                    startActivity(intent)
                    overridePendingTransition( 0, 0)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        // change satus bar color only for Cart screen
        window?.statusBarColor = ContextCompat.getColor(this, R.color.onyxBlack)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        binding.viewModel = viewModel
        binding.view = this

        binding.rvCart.layoutManager = LinearLayoutManager(this)

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
            show()
        }
    }

    private fun showPopupDeleteConfirmation(){
        alert(title = "", message = "¿Está seguro que desea eliminar este registro?"){
            yesButton {
                viewModel.onDeleteItemClicked(viewModel.deleteItem ?: 0)
            }
        }.show()
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
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