package com.ocean.mouher.ui.store.home.products.proudctDetail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.domain.entities.Item
import com.ocean.mouher.R

class ProductDetailRelatedProductsAdapter (private val context: Context,
                                           private var relatedProductsList : MutableList<Item>,
                                           private val eventClick: InterfaceOnClick)
    : androidx.recyclerview.widget.RecyclerView.Adapter<ProductDetailRelatedProductsAdapter.ItemViewHolder>(){

    interface InterfaceOnClick {
        fun onItemClick(pos: Int)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_products, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return relatedProductsList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(pos, relatedProductsList[pos], eventClick)
    }

    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        fun setData(pos: Int, item: Item, eventItemClick: InterfaceOnClick){

            val productName: TextView = itemView.findViewById(R.id.productName)
            val productImage: ImageView = itemView.findViewById(R.id.productImage)

            productName.text = item.name

            Glide.with(context)
                .load(item.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.ic_blur)
                .into(productImage)

            itemView.setOnClickListener {
                eventItemClick.onItemClick(pos)
            }
        }
    }
}
