package com.ocean.mouher.ui.common

import android.graphics.Color
import android.view.View
import android.widget.TextView
import com.ocean.mouher.R
import com.google.android.material.snackbar.Snackbar
import org.jetbrains.anko.backgroundColor

@Suppress("DEPRECATION")
fun showSnackbar(view: View, message: String?, type: SnackType, dur: Int? = null, lines: Int? = null){

    var duration = if (type == SnackType.LONG_SUCCESS) 6000 else Snackbar.LENGTH_LONG
    dur?.let { duration = it }

    Snackbar
        .make(view, message ?: "", Snackbar.LENGTH_INDEFINITE)
        .setDuration(duration).apply {
            val sview = this.view

            lines?.let {
                sview.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                    maxLines = lines
                }
            }

            with (view.context) {
                when (type) {
                    SnackType.ERROR -> sview.backgroundColor = resources.getColor(R.color.mainPink)
                    SnackType.SUCCESS ->  sview.backgroundColor = resources.getColor(R.color.mainGreen)
                    SnackType.INFO -> sview.backgroundColor = resources.getColor(R.color.mainDarkBlue)
                    SnackType.WARNING -> { sview.backgroundColor = resources.getColor(R.color.mainYellow) }
                    SnackType.LONG_SUCCESS -> {
                        sview.backgroundColor = resources.getColor(R.color.mainGreen)
                        sview.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).apply {
                            maxLines = 4
                        }
                    }
                }
            }

            sview.findViewById<TextView>(com.google.android.material.R.id.snackbar_text).setTextColor(Color.BLACK)
            show()
        }
}

enum class SnackType{
    ERROR,
    SUCCESS,
    INFO,
    WARNING,
    LONG_SUCCESS
}
