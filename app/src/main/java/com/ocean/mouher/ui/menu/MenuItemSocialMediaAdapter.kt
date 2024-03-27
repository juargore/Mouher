package com.ocean.mouher.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.domain.entities.SocialMediaUI
import com.ocean.mouher.R

class MenuItemSocialMediaAdapter (private var socialMediaItemsList : List<SocialMediaUI>)
    : RecyclerView.Adapter<MenuItemSocialMediaAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgMedia: ImageView = itemView.findViewById(R.id.imgMedia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_social_media, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = socialMediaItemsList.size

    var onItemClicked: ((SocialMediaUI) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = socialMediaItemsList[pos]

        with(holder.itemView){
            Glide.with(context)
                .load(item.urlImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(holder.imgMedia)

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}
