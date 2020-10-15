package com.glass.mouher.ui.cart

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
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
import org.koin.android.viewmodel.ext.android.viewModel

class CartActivity : AppCompatActivity() {

    private val viewModel: CartViewModel by viewModel()
    private lateinit var binding: ActivityCartBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.onBackClicked -> finish()
                BR.onPopClicked -> showPopUpNotes()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<ACartListViewModel> {
        return CompositeItemBinder(CartItemBinder(BR.viewModel, R.layout.recycler_item_cart))
    }
}