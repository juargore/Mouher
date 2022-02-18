package com.glass.mouher.ui.search

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glass.domain.entities.ProductSearchUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_search_results.view.*

class SearchResultsAdapter(
    private val results: List<ProductSearchUI>
): RecyclerView.Adapter<SearchResultsAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
            resultName.text = item.name
            resultDescription.text = item.description
            resultPrice.text = "$${ item.currentPrice }"

            // if oldPrice does not exists -> hide view
            if(item.oldPrice.isNullOrBlank()) {
                resultOldPrice.visibility = View.GONE
            } else {
                resultOldPrice.let{
                    // middle line on the old price
                    it.paintFlags = it.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    it.text = "$${ item.oldPrice }"
                }
            }

            item.urlImage?.let{
                Glide.with(resultImg.context)
                    .load(it)
                    .placeholder(R.drawable.ic_blur)
                    .centerCrop()
                    .into(resultImg)
            }

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }

}