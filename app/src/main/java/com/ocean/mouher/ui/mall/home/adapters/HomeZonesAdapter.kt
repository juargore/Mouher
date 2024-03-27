@file:Suppress("DEPRECATION")
package com.ocean.mouher.ui.mall.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ocean.domain.entities.ZoneUI
import com.ocean.mouher.R

class HomeZonesAdapter (
    private var zonesList : List<ZoneUI>
): RecyclerView.Adapter<HomeZonesAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val subtitle: TextView = itemView.findViewById(R.id.subtitle)
        val cardHomeZones: CardView = itemView.findViewById(R.id.cardHomeZones)
        val layoutP: LinearLayout = itemView.findViewById(R.id.layoutP)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_zones, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = zonesList.size

    var onItemClicked: ((ZoneUI) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = zonesList[pos]

        with(holder.itemView) {
            holder.title.text = item.name

            if (item.description.isNullOrBlank()) {
                holder.subtitle.visibility = View.GONE
            } else{
                holder.subtitle.text = item.description
            }

            if(item.urlImage.isNullOrEmpty()){
                holder.cardHomeZones.layoutParams.height = WRAP_CONTENT
            }

            setOnClickListener {
                onItemClicked?.invoke(item)
            }

            val sdk = android.os.Build.VERSION.SDK_INT
            val background2 = R.drawable.background_gradient_zones
            val background1 = R.drawable.background_gradient_zones_two

            if (pos % 2 == 0) {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.layoutP.setBackgroundDrawable(ContextCompat.getDrawable(context, background1))
                } else {
                    holder.layoutP.background = ContextCompat.getDrawable(context, background1)
                }
            } else {
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    holder.layoutP.setBackgroundDrawable(ContextCompat.getDrawable(context, background2))
                } else {
                    holder.layoutP.background = ContextCompat.getDrawable(context, background2)
                }
            }
        }
    }
}
