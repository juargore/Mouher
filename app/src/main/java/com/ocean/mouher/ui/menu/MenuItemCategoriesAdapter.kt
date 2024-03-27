package com.ocean.mouher.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ocean.domain.entities.CategoryUI
import com.ocean.mouher.R

class MenuItemCategoriesAdapter (private var categoryItemsList : List<CategoryUI>)
    : RecyclerView.Adapter<MenuItemCategoriesAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtNameDrawer: TextView = itemView.findViewById(R.id.txtNameDrawer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_menu, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = categoryItemsList.size

    var onItemClicked: ((CategoryUI) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = categoryItemsList[pos]

        with(holder.itemView){
            holder.txtNameDrawer.text = item.name
            setOnClickListener { onItemClicked?.invoke(item) }
        }
    }
}
