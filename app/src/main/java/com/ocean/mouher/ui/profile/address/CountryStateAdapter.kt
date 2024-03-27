package com.ocean.mouher.ui.profile.address

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ocean.domain.entities.Country
import com.ocean.domain.entities.State
import com.ocean.mouher.R

class CountryStateAdapter(context: Context, list: List<Any>): ArrayAdapter<Any>( context, 0, list){

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = convertView ?: layoutInflater.inflate(R.layout.spinner_item_simple, parent, false)

        getItem(position)?.let { setItem(view, it) }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = layoutInflater.inflate(R.layout.spinner_item_simple, parent, false)
        getItem(position)?.let { setItem(view, it) }

        return view
    }

    private fun<T> setItem(v: View, countryState: T) {
        val txtMain: TextView = v.findViewById(R.id.txtMain)

        if (countryState is Country) {
            txtMain.text = countryState.Nombre
        }

        if (countryState is State) {
            txtMain.text = countryState.Nombre
        }
    }
}
