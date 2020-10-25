@file:Suppress("DEPRECATION")
package com.glass.mouher.ui.mall.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.ZoneUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_home_zones.view.*

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

        with(holder.itemView){
            title.text = item.name

            if(item.description.isNullOrBlank()){
                subtitle.visibility = View.GONE
            } else{
                subtitle.text = item.description
            }

            if(item.urlImage.isNullOrEmpty()){
                cardHomeZones.layoutParams.height = WRAP_CONTENT
            }

            Glide.with(context)
                .load(item.urlImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(image)

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}