@file:Suppress("DEPRECATION")

package com.glass.mouher.ui.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.Item
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_social_media.view.*


class MenuItemSocialMediaAdapter (private val context: Context,
                                  private var socialMediaItemsList : MutableList<Item>,
                                  private val eventClick: InterfaceOnClick)
    : androidx.recyclerview.widget.RecyclerView.Adapter<MenuItemSocialMediaAdapter.ItemViewHolder>(){

    interface InterfaceOnClick {
        fun onItemClick(pos: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_social_media, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return socialMediaItemsList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(socialMediaItemsList[pos], eventClick)
    }

    @Suppress("DEPRECATION")
    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        fun setData(item: Item, eventItemClick: InterfaceOnClick){

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(itemView.imgMedia)

            itemView.setOnClickListener {
                eventItemClick.onItemClick(0)
            }
        }
    }
}