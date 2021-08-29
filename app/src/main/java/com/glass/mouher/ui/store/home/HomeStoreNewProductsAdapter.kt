@file:Suppress("DEPRECATION")

package com.glass.mouher.ui.store.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.glass.domain.entities.ProductUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_products.view.*


class HomeStoreNewProductsAdapter (private val context: Context,
                                   private var productsList : List<ProductUI>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<HomeStoreNewProductsAdapter.ItemViewHolder>(){

    var onItemClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_products, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(productsList[pos])
    }

    @Suppress("DEPRECATION")
    inner class ItemViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){
        @SuppressLint("SetTextI18n")
        fun setData(item: ProductUI){

            with(itemView){
                productName.text = item.name
                txtPrice.text = "$${item.currentPrice}"
                txtDescriptionTop.text = item.description

                // set rating on stars
                val rating = item.rating?.toFloat() ?: 0f
                ratingBar.rating = rating

                // if oldPrice does not exists -> hide view
                if(item.oldPrice.isNullOrBlank()){
                    txtOldPrice.visibility = View.GONE
                }else{
                    txtOldPrice.text = "$${item.oldPrice}"
                    txtOldPrice.let{
                        // middle line on the old price
                        it.paintFlags = it.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }

                // if discount on product does not exists -> hide view
                if(item.discount.isNullOrBlank()){
                    txtDiscount.visibility = View.GONE
                }else{
                    txtDiscount.text = item.discount
                }

                Glide.with(context)
                    .load(item.sidePhoto1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_blur)
                    .into(itemView.productImage)

                setOnClickListener {
                    item.id?.let{
                        onItemClicked?.invoke(it)
                    }
                }
            }
        }
    }
}