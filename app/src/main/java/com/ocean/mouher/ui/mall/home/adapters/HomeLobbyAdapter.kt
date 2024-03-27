package com.ocean.mouher.ui.mall.home.adapters

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.domain.entities.ItemLobby
import com.ocean.mouher.R
import org.jetbrains.anko.windowManager


class HomeLobbyAdapter (
    private var lobbyItemsList : List<ItemLobby>
) : RecyclerView.Adapter<HomeLobbyAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleLobby: TextView = itemView.findViewById(R.id.titleLobby)
        val subtitleLobby: TextView = itemView.findViewById(R.id.subtitleLobby)
        val image: ImageView = itemView.findViewById(R.id.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_lobby, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = lobbyItemsList.size

    var onItemClicked: ((ItemLobby) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = lobbyItemsList[pos]

        with(holder.itemView){
            holder.titleLobby.text = item.title
            holder.subtitleLobby.text = item.subtitle

            // Get width screen to apply correct size for image
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            context.windowManager.defaultDisplay.getMetrics(displayMetrics)
            val width = displayMetrics.widthPixels

            if(width < 950) {
                when(pos){
                    0 -> holder.image.layoutParams.height = 1000
                    1 -> holder.image.layoutParams.height = 500
                    else-> holder.image.layoutParams.height = 300
                }
            }else{
                when(pos){
                    0 -> holder.image.layoutParams.height = 1500
                    1 -> holder.image.layoutParams.height = 700
                    else-> holder.image.layoutParams.height = 500
                }
            }

            Glide.with(context)
                .load(item.urlImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(holder.image)

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}
