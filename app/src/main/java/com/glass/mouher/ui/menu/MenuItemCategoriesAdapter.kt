@file:Suppress("DEPRECATION")
package com.glass.mouher.ui.menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glass.domain.entities.CategoryUI
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_menu.view.*

class MenuItemCategoriesAdapter (private var CategoryItemsList : List<CategoryUI>)
    : RecyclerView.Adapter<MenuItemCategoriesAdapter.ItemViewHolder>(){

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item_menu, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = CategoryItemsList.size

    var onItemClicked: ((CategoryUI) -> Unit)? = null

    override fun onBindViewHolder(holder: ItemViewHolder, pos: Int) {
        val item = CategoryItemsList[pos]

        with(holder.itemView){
            txtNameDrawer.text = item.name

            setOnClickListener {
                onItemClicked?.invoke(item)
            }
        }
    }
}