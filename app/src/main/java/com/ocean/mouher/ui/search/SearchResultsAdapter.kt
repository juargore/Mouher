package com.ocean.mouher.ui.search

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ocean.domain.entities.ProductSearchUI
import com.ocean.mouher.R

class SearchResultsAdapter(
    private val results: List<ProductSearchUI>
): RecyclerView.Adapter<SearchResultsAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val resultName: TextView = itemView.findViewById(R.id.resultName)
        val resultDescription: TextView = itemView.findViewById(R.id.resultDescription)
        val resultPrice: TextView = itemView.findViewById(R.id.resultPrice)
        val resultOldPrice: TextView = itemView.findViewById(R.id.resultOldPrice)
        val resultImg: ImageView = itemView.findViewById(R.id.resultImg)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_search_results, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = results.size

    var onItemClicked: ((ProductSearchUI) -> Unit)? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = results[position]

        with(holder.itemView) {
            holder.resultName.text = item.name
            holder.resultDescription.text = item.description
            holder.resultPrice.text = "$${ item.currentPrice }"

            // if oldPrice does not exists -> hide view
            if(item.oldPrice.isNullOrBlank()) {
                holder.resultOldPrice.visibility = View.GONE
            } else {
                holder.resultOldPrice.let{
                    // middle line on the old price
                    it.paintFlags = it.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    it.text = "$${ item.oldPrice }"
                }
            }

            item.urlImage?.let{
                Glide.with(holder.resultImg.context)
                    .load(it)
                    .placeholder(R.drawable.ic_blur)
                    .centerCrop()
                    .into(holder.resultImg)
            }

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}
