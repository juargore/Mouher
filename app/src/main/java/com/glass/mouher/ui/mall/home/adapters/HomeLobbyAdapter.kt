package com.glass.mouher.ui.mall.home.adapters

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.ItemLobby
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_home_lobby.view.*
import org.jetbrains.anko.windowManager


class HomeLobbyAdapter (
    private var lobbyItemsList : List<ItemLobby>
) : RecyclerView.Adapter<HomeLobbyAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_lobby, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = lobbyItemsList.size

    var onItemClicked: ((ItemLobby) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = lobbyItemsList[pos]

        with(holder.itemView){
            titleLobby.text = item.title
            subtitleLobby.text = item.subtitle

            // Get width screen to apply correct size for image
            val displayMetrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels

            if(width < 950){
                when(pos){
                    0 -> image.layoutParams.height = 1000
                    1 -> image.layoutParams.height = 500
                    else-> image.layoutParams.height = 300
                }
            }else{
                when(pos){
                    0 -> image.layoutParams.height = 1500
                    1 -> image.layoutParams.height = 700
                    else-> image.layoutParams.height = 500
                }
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