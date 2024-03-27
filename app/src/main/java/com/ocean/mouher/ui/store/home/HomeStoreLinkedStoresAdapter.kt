package com.ocean.mouher.ui.store.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.domain.entities.Item
import com.ocean.mouher.R


class HomeStoreLinkedStoresAdapter (private val context: Context,
                                    private var zonesList : MutableList<Item>,
                                    private val eventClick: InterfaceOnClick)
    : androidx.recyclerview.widget.RecyclerView.Adapter<HomeStoreLinkedStoresAdapter.ItemViewHolder>(){

    interface InterfaceOnClick {
        fun onItemClick(pos: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_linked_stores, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return zonesList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(pos, zonesList[pos], eventClick)
    }

    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        fun setData(pos: Int, item: Item, eventItemClick: InterfaceOnClick){

            val text: TextView = itemView.findViewById(R.id.text)
            val image: ImageView = itemView.findViewById(R.id.image)

            text.text = item.name

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(image)

            itemView.setOnClickListener {
                eventItemClick.onItemClick(pos)
            }
        }
    }
}
