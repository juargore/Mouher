package com.glass.mouher.ui.common.binder

import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.forEach
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.SwipeRevealLayout
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.glass.mouher.ui.common.DiffItem

interface ClickHandler<Model> {
    fun onClick(viewModel: Model)
}

/**
 * An Adapter, using ViewModel, Bindable View for items,
 * and providing click listener and propertychange callback for the items.
 * If the ViewModel of the items extends [DiffItem], then list updates use DiffUtils to minimize
 * changes.
 */
class BindingRecyclerViewAdapter<ViewModel: Observable>(
    private val itemBinder: ItemBinder<ViewModel>,
    _items: Collection<ViewModel>? = null
): RecyclerView.Adapter<BindingRecyclerViewAdapter.ViewHolder>(), View.OnClickListener {
    private val itemModel = -124
    private var items: List<ViewModel> = emptyList()
    private var oldItems: List<ViewModel> = emptyList()
    private var inflater: LayoutInflater? = null
    private var propertyChangedCallback: Observable.OnPropertyChangedCallback? = null
    private var clickHandler: ClickHandler<ViewModel>? = null
    private var isItemsRecyclable: Boolean = true
    private var swipeHelper: ViewBinderHelper? = null

    private var parentHeight = 0
    private val itemsHeight = SparseIntArray()

    init {
        setItems(_items)
    }

    /**
     * ViewHolder relying on a databinding layout.
     */
    class ViewHolder internal constructor(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun setPropertyChangedCallback(propertyChangedCallback: Observable.OnPropertyChangedCallback) {
        this.propertyChangedCallback = propertyChangedCallback
    }

    fun setClickHandler(clickHandler: ClickHandler<ViewModel>) {
        this.clickHandler = clickHandler
    }

    fun setItemsRecyclable(itemsRecyclable: Boolean) {
        isItemsRecyclable = itemsRecyclable
    }

    fun setSwipeHelper(helper: ViewBinderHelper) {
        swipeHelper = helper
    }

    override fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
            parentHeight = parent.height
        }

        val binding =
            DataBindingUtil.inflate(inflater!!, layoutId, parent, false) as ViewDataBinding
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = items[position]
        propertyChangedCallback?.let {
            item.addOnPropertyChangedCallback(it)
        }
        viewHolder.binding.setVariable(itemBinder.getBindingVariable(item), item)
        viewHolder.binding.root.setTag(itemModel, item)
        viewHolder.binding.root.setOnClickListener(this)
        viewHolder.binding.executePendingBindings()

        if (!isItemsRecyclable) {
            viewHolder.setIsRecyclable(isItemsRecyclable)
        }

        itemsHeight.put(position, viewHolder.binding.root.height)
        if (item is PlaceholderFilledViewModel) {
            var totalHeight = 0
            itemsHeight.forEach { key, value ->
                if (key < position)
                    totalHeight = value
            }
            viewHolder.binding.root.layoutParams = ViewGroup.LayoutParams(
                viewHolder.binding.root.layoutParams.width,
                parentHeight - totalHeight
            )
        }

        swipeHelper?.let { viewBinderHelper ->
            (viewHolder.binding.root as? SwipeRevealLayout)?.let { swipeRevealLayout ->
                viewBinderHelper.bind(swipeRevealLayout, System.identityHashCode(item).toString())
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return itemBinder.getLayoutRes(items[position])
    }

    @Suppress("UNCHECKED_CAST")
    override fun onClick(v: View?) {
        (v?.getTag(itemModel) as ViewModel?)?.let {
            clickHandler?.onClick(it)
        }
    }

    /**
     * The [DiffUtil.Callback] that translate the list update to a sequence of modified, inserted,
     * deleted ...
     */
    private val diff = object : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            try {
                @Suppress("UNCHECKED_CAST")
                return (oldItems[oldItemPosition] as DiffItem<ViewModel>).isTheSame(items[newItemPosition])
            } catch(e: Exception) {
                return false
            }
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            try {
                @Suppress("UNCHECKED_CAST")
                return (oldItems[oldItemPosition] as DiffItem<ViewModel>).isSameContent(items[newItemPosition])
            } catch(e: Exception) {
                return false
            }
        }

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = items.size
    }

    /**
     * Entry point to update items.
     * If the items implements [DiffItem], the list is updated through DiffUtil.
     * Otherwise, the whole list is invalidate.
     * @param newItems new list of the items.
     */
    fun setItems(newItems: Collection<ViewModel>?) {
        val newList = newItems?.toList() ?: emptyList()
        if (newList.isNotEmpty() && newList[0] is DiffItem<*>) {
            oldItems = items
            items = ArrayList(newList)

            //val operations = DiffUtil.calculateDiff(diff)
            //operations.dispatchUpdatesTo(this)
        } else {
            oldItems = emptyList()
            items = newList
            notifyDataSetChanged()
        }
    }
}