@file:Suppress("DEPRECATION")

package com.glass.mouher.ui.store.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.Item
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_linked_stores.view.*


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

    @Suppress("DEPRECATION")
    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        fun setData(pos: Int, item: Item, eventItemClick: InterfaceOnClick){

            itemView.text.text = item.name

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(itemView.image)

            itemView.setOnClickListener {
                eventItemClick.onItemClick(pos)
            }
        }
    }
}