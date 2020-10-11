package com.glass.mouher.ui.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.glass.domain.entities.MainSlideMenuItem
import com.glass.mouher.R
import kotlinx.android.synthetic.main.recycler_item_menu.view.*


class AdapterSlideMainMenu(val context: Context, private val itemList: List<MainSlideMenuItem>, private val objInterface : InterfaceOnClick) : androidx.recyclerview.widget.RecyclerView.Adapter<AdapterSlideMainMenu.ItemViewHolder>() {

    var rowIndex = -0

    interface InterfaceOnClick{
        fun onItemClick(drawerItem: MainSlideMenuItem, pos : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view : View = LayoutInflater.from(context).inflate(R.layout.recycler_item_menu, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.setData(itemList[position], position, objInterface)
    }

    @Suppress("DEPRECATION")
    inner class ItemViewHolder (itemView : View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView){

        fun setData(drawerItem: MainSlideMenuItem, pos : Int, objInterface : InterfaceOnClick){

            itemView.ivIconDrawer.setImageResource(drawerItem.image)
            itemView.txtNameDrawer.text = drawerItem.name

            itemView.setOnClickListener {
                rowIndex = pos
                notifyDataSetChanged()
                objInterface.onItemClick(drawerItem, pos)
            }

            if(rowIndex == pos){
                itemView.txtNameDrawer.setTextColor(context.resources.getColor(R.color.colorPrimary))
                itemView.ivIconDrawer.setColorFilter(context.resources.getColor(R.color.colorPrimary))
            } else {
                itemView.txtNameDrawer.setTextColor(context.resources.getColor(R.color.bgGrey))
                itemView.ivIconDrawer.setColorFilter(context.resources.getColor(R.color.bgGrey))
            }
        }
    }

}