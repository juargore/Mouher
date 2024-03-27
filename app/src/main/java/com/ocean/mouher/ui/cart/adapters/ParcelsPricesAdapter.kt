package com.ocean.mouher.ui.cart.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ocean.domain.entities.ParcelData
import com.ocean.mouher.R
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class ParcelsPricesAdapter(
    private var parcelsList : List<ParcelData>
) : RecyclerView.Adapter<ParcelsPricesAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtdescription: TextView = itemView.findViewById(R.id.txtdescription)
        val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        val txtEstimation: TextView = itemView.findViewById(R.id.txtEstimation)
        val radio: RadioButton = itemView.findViewById(R.id.radio)
        val imgLogo: ImageView = itemView.findViewById(R.id.imgLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_parcel_price, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = parcelsList.size

    var onItemSelected: ((ParcelData) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = parcelsList[pos]

        with(holder.itemView) {
            Glide.with(context)
                .load(item.LinkImagen)
                .placeholder(R.drawable.ic_blur)
                .into(holder.imgLogo)

            holder.radio.isChecked = item.Seleccionado == true
            holder.txtdescription.text = item.Descripcion
            holder.txtPrice.text = "$${getTwoDecimalsAndCommas(item.Importe.toString())}"
            holder.txtEstimation.text = item.Estimacion

            holder.radio.setOnClickListener {
                onItemSelected?.invoke(item)
            }
        }
    }
}

fun getTwoDecimalsAndCommas(value: String): String {
    val amount: Double = value.toDouble()
    val symbols = DecimalFormatSymbols(Locale("es", "MX"))
    val formatter = DecimalFormat("##,##0.00", symbols)
    return formatter.format(amount)
}
