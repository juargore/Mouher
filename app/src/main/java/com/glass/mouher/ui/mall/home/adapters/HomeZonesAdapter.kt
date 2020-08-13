@file:Suppress("DEPRECATION")

package com.glass.mouher.ui.mall.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.Item
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_home_zones.view.*
import java.util.*


class HomeZonesAdapter (private val context: Context,
                        private var zonesList : MutableList<Item>,
                        private val eventClick: InterfaceOnClick)
    : androidx.recyclerview.widget.RecyclerView.Adapter<HomeZonesAdapter.ItemViewHolder>(){

    interface InterfaceOnClick {
        fun onItemClick(pos: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_home_zones, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return zonesList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(pos, zonesList[pos], eventClick)
    }

    @Suppress("DEPRECATION")
    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        private val mRandom = Random()

        fun setData(pos: Int, item: Item, eventItemClick: InterfaceOnClick){

            itemView.title.text = item.name
            itemView.subtitle.text = item.description
            itemView.cardHomeZones.layoutParams.height = getRandomIntInRange(850, 450)

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(itemView.image)

            itemView.setOnClickListener {
                eventItemClick.onItemClick(pos)
            }
        }

        private fun getRandomIntInRange(max: Int, min: Int): Int {
            return mRandom.nextInt(max - min + min) + min
        }
    }
}