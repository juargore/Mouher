package com.glass.mouher.ui.mall.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.Item
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_home_lobby.view.*
import kotlinx.android.synthetic.main.recycler_item_home_lobby.view.image

class HomeLobbyAdapter (
    private var lobbyItemsList : MutableList<Item>
) : RecyclerView.Adapter<HomeLobbyAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_lobby, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = lobbyItemsList.size

    var onItemClicked: ((Item) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = lobbyItemsList[pos]

        with(holder.itemView){
            titleLobby.text = item.name
            subtitleLobby.text = item.description

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(image)

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}