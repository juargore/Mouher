package com.glass.mouher.ui.profile.address

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.glass.domain.entities.Country
import com.glass.domain.entities.State
import com.glass.mouher.R
import kotlinx.android.synthetic.main.spinner_item_simple.view.*

class CountryStateAdapter(context: Context, list: List<Any>): ArrayAdapter<Any>( context, 0, list){

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

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
        if(countryState is Country){
            v.txtMain.text = countryState.Nombre
        }

        if(countryState is State){
            v.txtMain.text = countryState.Nombre
        }
    }
}