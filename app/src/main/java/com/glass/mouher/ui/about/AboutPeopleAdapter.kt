package com.glass.mouher.ui.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.AboutPersonUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_about_person.view.*

class AboutPeopleAdapter (
    private var peopleList : List<AboutPersonUI>
) : RecyclerView.Adapter<AboutPeopleAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_about_person, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = peopleList.size

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = peopleList[pos]

        with(holder.itemView){
            name.text = item.name
            position.text = item.position
            partner.text = item.partner

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(photo)
        }
    }
}