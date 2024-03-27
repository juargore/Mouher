package com.ocean.mouher.ui.store.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.ocean.domain.entities.ProductUI
import com.ocean.mouher.R

class HomeStoreNewProductsAdapter (
    private val context: Context,
    private var productsList : List<ProductUI>
): RecyclerView.Adapter<HomeStoreNewProductsAdapter.ItemViewHolder>() {

    var onItemClicked: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycler_item_products, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = productsList.size

    override fun onBindViewHolder(p0: ItemViewHolder, pos: Int) {
        p0.setData(productsList[pos])
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val productName: TextView = itemView.findViewById(R.id.productName)
        private val txtPrice: TextView = itemView.findViewById(R.id.txtPrice)
        private val txtDescriptionTop: TextView = itemView.findViewById(R.id.txtDescriptionTop)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        private val txtOldPrice: TextView = itemView.findViewById(R.id.txtOldPrice)
        private val txtDiscount: TextView = itemView.findViewById(R.id.txtDiscount)
        private val productImage: ImageView = itemView.findViewById(R.id.productImage)

        @SuppressLint("SetTextI18n")
        fun setData(item: ProductUI) {
            with(itemView) {
                productName.text = item.name
                txtPrice.text = "$${item.currentPrice}"
                txtDescriptionTop.text = item.description

                // set rating on stars
                val rating = item.rating?.toFloat() ?: 0f
                ratingBar.rating = rating

                // if oldPrice does not exists -> hide view
                if(item.oldPrice.isNullOrBlank()) {
                    txtOldPrice.visibility = View.GONE
                } else {
                    txtOldPrice.text = "$${item.oldPrice}"
                    txtOldPrice.let{
                        // middle line on the old price
                        it.paintFlags = it.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    }
                }

                // if discount on product does not exists -> hide view
                if(item.discount.isNullOrBlank()) {
                    txtDiscount.visibility = View.GONE
                } else {
                    txtDiscount.text = item.discount
                }

                Glide.with(context)
                    .load(item.sidePhoto1)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.ic_blur)
                    .into(productImage)

                setOnClickListener {
                    item.id?.let {
                        onItemClicked?.invoke(it)
                    }
                }
            }
        }
    }
}
