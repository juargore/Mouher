@file:Suppress("DEPRECATION")
package com.glass.mouher.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.Item
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_social_media.view.*

class MenuItemSocialMediaAdapter (private var socialMediaItemsList : List<Item>)
    : RecyclerView.Adapter<MenuItemSocialMediaAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_social_media, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = socialMediaItemsList.size

    var onItemClicked: ((Item) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = socialMediaItemsList[pos]

        with(holder.itemView){
            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(imgMedia)

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}