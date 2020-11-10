@file:Suppress("DEPRECATION")

package com.glass.mouher.ui.store.home

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.ShortProductUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_products.view.*


class HomeStoreNewProductsAdapter (private val context: Context,
                                   private var productsList : List<ShortProductUI>,
                                   private val eventClick: InterfaceOnClick)
    : androidx.recyclerview.widget.RecyclerView.Adapter<HomeStoreNewProductsAdapter.ItemViewHolder>(){

    interface InterfaceOnClick {
        fun onItemClick(productId: String?)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_products, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(pos, productsList[pos], eventClick)
    }

    @Suppress("DEPRECATION")
    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        fun setData(pos: Int, item: ShortProductUI, eventItemClick: InterfaceOnClick){

            itemView.productName.text = item.name
            itemView.txtPrice.text = item.price
            itemView.txtOldPrice.text = item.oldPrice

            // middle line on the old price
            itemView.txtOldPrice.let{
                it.paintFlags = it.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(itemView.productImage)

            itemView.setOnClickListener {
                eventItemClick.onItemClick(item.id)
            }
        }
    }
}