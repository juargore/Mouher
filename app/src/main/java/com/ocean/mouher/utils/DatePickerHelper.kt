package com.ocean.mouher.utils

import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Color
import com.ocean.mouher.R
import java.util.*

class DatePickerHelper(context: Context, shouldRemoveEighteen: Boolean = true, spinnerMode: Boolean = false) {

    private var dialog: DatePickerDialog
    private var callback: Callback? = null
    private var isSpinner = false
    private lateinit var c: Context

    private val listener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            callback?.onDateSelected(dayOfMonth, monthOfYear, year)
        }

    init {
        val cal = Calendar.getInstance()
        val style = if(spinnerMode) R.style.MySpinnerDatePickerStyle else 0

        c = context
        isSpinner = spinnerMode
        dialog = DatePickerDialog(context, style, listener,
            cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH))

        if(shouldRemoveEighteen){
            // Validate +18 years old on DatePicker
            //dialog.datePicker.maxDate = System.currentTimeMillis() - 568025136000L
            dialog.datePicker.maxDate = System.currentTimeMillis()
        }else{
            /* Disable past days since today
               Note: For current day = -1000 and for tomorrow day = + 24*60*60*1000 */
            dialog.datePicker.minDate = System.currentTimeMillis() - 1000
        }
    }

    fun showDialog(dayofMonth: Int, month: Int, year: Int, callback: Callback?) {
        this.callback = callback
        dialog.datePicker.init(year, month, dayofMonth, null)

        if(isSpinner){
            dialog.setTitle(c.resources.getString(R.string.select_date))
        }

        dialog.show()

        if(isSpinner){
            dialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            dialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)
        }
    }

    fun setMinDate(minDate: Long) {
        dialog.datePicker.minDate = minDate
    }

    fun setMaxDate(maxDate: Long) {
        dialog.datePicker.maxDate = maxDate
    }

    interface Callback {
        fun onDateSelected(day: Int, month: Int, year: Int)
    }

}