package com.ocean.mouher.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import com.ocean.mouher.R
import com.ocean.mouher.databinding.ActivitySearchBinding
import com.ocean.mouher.extensions.startActivityNoAnimation
import com.ocean.mouher.ui.base.BaseActivity
import com.ocean.mouher.ui.common.propertyChangedCallback
import com.ocean.mouher.ui.store.MainStoreActivity
import org.koin.android.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity() {

    private val viewModel: SearchViewModel by viewModel()
    private lateinit var binding: ActivitySearchBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.results -> setupRecyclerList()
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.BlackTheme)
        super.onCreate(savedInstanceState)

        window?.statusBarColor = ContextCompat.getColor(this, R.color.onyxBlack)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search)
        binding.viewModel = viewModel
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)

        //with(binding.searchInput) {
            //this.requestFocus()
            //val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            //imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)
        //}
        addListeners()
    }

    private fun addListeners() {
        binding.searchInputEt.setOnEditorActionListener(object : TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    viewModel.searchOnServer()
                    return true
                }
                return false
            }
        })
    }

    // TODO: open detailed product screen here
    private fun setupRecyclerList() {
        with(binding.rvResults) {
            SearchResultsAdapter(viewModel.results).let { ad ->
                adapter = ad
                ad.onItemClicked = { product ->
                    startActivityNoAnimation(
                        Intent(this@SearchActivity, MainStoreActivity::class.java).also {
                            it.putExtra("storeId", product.idStore)
                            it.putExtra("productIdFromSearch", product.idProduct)
                        })
                }
            }
        }
    }
}