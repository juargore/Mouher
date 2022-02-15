@file:Suppress("DEPRECATION")
package com.glass.mouher.ui.mall.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat.setBackground
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.ZoneUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_home_zones.view.*
import org.jetbrains.anko.backgroundResource

class HomeZonesAdapter (
    private var zonesList : List<ZoneUI>
): RecyclerView.Adapter<HomeZonesAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_zones, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = zonesList.size

    var onItemClicked: ((ZoneUI) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = zonesList[pos]

        with(holder.itemView) {
            title.text = item.name

            if(item.description.isNullOrBlank()){
                subtitle.visibility = View.GONE
            } else{
                subtitle.text = item.description
            }

            if(item.urlImage.isNullOrEmpty()){
                cardHomeZones.layoutParams.height = WRAP_CONTENT
            }

            setOnClickListener {
                onItemClicked?.invoke(item)
            }

            val sdk = android.os.Build.VERSION.SDK_INT
            val background2 = R.drawable.background_gradient_zones
            val background1 = R.drawable.background_gradient_zones_two

            if(pos % 2 == 0) {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    layoutP.setBackgroundDrawable(ContextCompat.getDrawable(context, background1))
                } else {
                    layoutP.background = ContextCompat.getDrawable(context, background1)
                }
            } else {
                if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    layoutP.setBackgroundDrawable(ContextCompat.getDrawable(context, background2))
                } else {
                    layoutP.background = ContextCompat.getDrawable(context, background2)
                }
            }
        }
    }
}