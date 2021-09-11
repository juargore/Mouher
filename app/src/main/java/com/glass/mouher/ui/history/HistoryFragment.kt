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
import com.glass.mouher.extensions.openOrRefreshFragment
import com.glass.mouher.ui.common.binder.CompositeItemBinder
import com.glass.mouher.ui.common.binder.ItemBinder
import com.glass.mouher.ui.common.propertyChangedCallback
import com.glass.mouher.ui.history.details.HistoryDetailsFragment
import com.glass.mouher.ui.menu.MenuViewModel
import com.whiteelephant.monthpicker.MonthPickerDialog
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModel()
    private lateinit var binding: FragmentHistoryBinding

    private var month = 0
    private var year = 0

    private val onPropertyChangedCallback =
        propertyChangedCallback { _, propertyId ->
            when (propertyId) {
                BR.showDatePickerPopUp -> showMonthPickerPopUp()
                BR.openWebViewFragment -> redirectToWebScreen()
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        binding.viewModel = viewModel
        binding.view = this
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume(onPropertyChangedCallback)
    }

    private fun showMonthPickerPopUp(){
        val builder = MonthPickerDialog.Builder(requireContext(), { selectedMonth, selectedYear ->
            month = selectedMonth + 1
            year = selectedYear
            viewModel.onSelectedMonthFromPopupClicked(selectedMonth + 1, selectedYear)
        }, if (year > 0) year else 2021, if (month > 0) month - 1 else 2)

        builder.setTitle("Seleccione un mes")
            .setMinYear(2020).setMaxYear(2050)
            .setActivatedMonth(Calendar.getInstance().get(Calendar.MONTH))
            .setMonthRange(Calendar.JANUARY, Calendar.DECEMBER)
            .build().show()
    }

    private fun redirectToWebScreen(){
        requireActivity().openOrRefreshFragment(
            forStore = MenuViewModel.source != "MALL",
            destination = HistoryDetailsFragment(),
            name = "HISTORY_DETAILS",
            args = Bundle().apply {
                putString("url", viewModel.urlToDetails)
            }
        )
    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause(onPropertyChangedCallback)
    }

    fun itemViewBinder(): ItemBinder<AHistoryListViewModel> {
        return CompositeItemBinder(HistoryItemBinder(BR.viewModel, R.layout.recycler_item_history))
    }
}