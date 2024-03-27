package com.ocean.mouher.ui.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.domain.entities.AboutPersonUI
import com.ocean.mouher.R

class AboutPeopleAdapter (
    private var peopleList : List<AboutPersonUI>
) : RecyclerView.Adapter<AboutPeopleAdapter.ItemViewHolder>(){

    //class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val position: TextView = itemView.findViewById(R.id.position)
        val photo: ImageView = itemView.findViewById(R.id.photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_about_person, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = peopleList.size

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = peopleList[pos]

        with(holder.itemView) {
            holder.name.text = item.name
            holder.position.text = item.jobDescription

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(holder.photo)
        }
    }
}