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
import kotlinx.android.synthetic.main.recycler_item_home_lobby.view.*
import kotlinx.android.synthetic.main.recycler_item_home_lobby.view.image
import java.util.*


class HomeLobbyAdapter (private val context: Context,
                        private var lobbyItemsList : MutableList<Item>,
                        private val eventClick: InterfaceOnClick)
    : androidx.recyclerview.widget.RecyclerView.Adapter<HomeLobbyAdapter.ItemViewHolder>(){

    interface InterfaceOnClick {
        fun onItemClick(pos: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_home_lobby, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return lobbyItemsList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(pos, lobbyItemsList[pos], eventClick)
    }

    @Suppress("DEPRECATION")
    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        private val mRandom = Random()

        fun setData(pos: Int, item: Item, eventItemClick: InterfaceOnClick){

            itemView.titleLobby.text = item.name
            itemView.subtitleLobby.text = item.description

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