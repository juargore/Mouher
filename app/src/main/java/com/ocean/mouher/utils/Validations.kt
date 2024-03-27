package com.ocean.mouher.utils

import android.content.Context
import com.ocean.mouher.R
import java.util.*

object Validations {

    fun toPrettyDate(context: Context?, date: String, locale: Locale): String{
        val day = date.substringBefore("-") // 13
        val month = date.substringAfter("-").substringBefore("-")
        val year = date.substringAfter("-").substringAfter("-")

        return if(locale.language.contains("es")){
            // 13 de Mayo de 2007
            "$day de ${getMonthName(context, month)} de $year"
        } else{
            //May 13, 2007
            "${getMonthName(context, month)} $day, $year"
        }
    }

    fun getMonthName(context: Context?, month: String): String{
        context?.let{
            return when(month){
                "1", "01" -> context.resources.getString(R.string.gral_january)
                "2", "02" -> context.resources.getString(R.string.gral_february)
                "3", "03" -> context.resources.getString(R.string.gral_march)
                "4", "04" -> context.resources.getString(R.string.gral_april)
                "5", "05" -> context.resources.getString(R.string.gral_may)
                "6", "06" -> context.resources.getString(R.string.gral_june)
                "7", "07" -> context.resources.getString(R.string.gral_july)
                "8", "08" -> context.resources.getString(R.string.gral_august)
                "9", "09" -> context.resources.getString(R.string.gral_september)
                "10"-> context.resources.getString(R.string.gral_october)
                "11"-> context.resources.getString(R.string.gral_november)
                else -> context.resources.getString(R.string.gral_december)
            }
        }

        return ""
    }

}