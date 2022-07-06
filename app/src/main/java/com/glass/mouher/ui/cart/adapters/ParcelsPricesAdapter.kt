package com.glass.mouher.ui.cart.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glass.domain.entities.ParcelData
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_parcel_price.view.*

class ParcelsPricesAdapter(
    private var parcelsList : List<ParcelData>
) : RecyclerView.Adapter<ParcelsPricesAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_parcel_price, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = parcelsList.size

    var onItemSelected: ((ParcelData) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = parcelsList[pos]

        with(holder.itemView) {
            txtdescription.text = item.Descripcion
            txtPrice.text = item.Importe.toString()
            txtEstimation.text = item.Estimacion
            radio.setOnClickListener {
                onItemSelected?.invoke(item)
            }
        }
    }
}