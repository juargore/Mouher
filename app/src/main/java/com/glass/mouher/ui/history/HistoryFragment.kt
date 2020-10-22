package com.glass.mouher.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.glass.mouher.R
import com.glass.mouher.databinding.FragmentHistoryBinding
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import org.jetbrains.anko.support.v4.alert
import org.jetbrains.anko.yesButton
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModel()
    private lateinit var binding: FragmentHistoryBinding

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.deleteItem -> showPopupDeleteConfirmation()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        binding.viewModel = viewModel
        binding.view = this
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    private fun showPopupDeleteConfirmation(){
        alert(title = "", message = "¿Está seguro que desea eliminar este registro?"){
            yesButton {

            }
        }.show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AHistoryListViewModel> {
        return CompositeItemBinder(HistoryItemBinder(BR.viewModel, R.layout.recycler_item_history))
    }
}