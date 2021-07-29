package com.glass.mouher.ui.mall.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.SponsorUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_home_sponsors.view.*

class HomeSponsorsAdapter (
    private var sponsorsList : List<SponsorUI>
) : RecyclerView.Adapter<HomeSponsorsAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_home_sponsors, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = sponsorsList.size

    var onItemClicked: ((SponsorUI) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = sponsorsList[pos]

        with(holder.itemView){
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