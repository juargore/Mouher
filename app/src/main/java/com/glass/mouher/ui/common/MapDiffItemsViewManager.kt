package com.glass.mouher.ui.common

/*abstract class MapDiffItemsViewManager<T: DiffItem<T>, V>(mapInterface: MapInterface): MapBaseViewManager<T, V>(mapInterface), ListUpdateCallback {
    private val _tag = "MapBaseViewManager"

    /**
     * Add the view corresponding to the specified model.
     */
    abstract fun add(model: T)

    /**
     * Modify (update) view for the specified model.
     */
    abstract fun modify(model: T)

    /**
     * Delete the view for the specified model.
     */
    abstract fun remove(model: T)

    /**
     * @property items the current items to display
     */
    private var items: List<T> = emptyList()

    /**
     * @property oldItems the items list, to replace with the new items list.
     */
    private var oldItems: List<T> = emptyList()

    /**
     * @property operations the succession of operation to play to transforme the oldItems in new items.
     */
    private var operations: DiffUtil.DiffResult? = null

    /**
     * Generation of views's tag
     *
     * @param model The [Order] entity
     *
     * @return Tag of the view
     */
    override fun getViewTag(model: T): String {
        return "$baseViewTag${model.uid}"
    }

    /**
     * Store created view, to manage object deletion.
     */
    var referencedViews = HashMap<String, V>()

    /**
     * Implements [ListUpdateCallback.onChanged].
     * @param position position of the modified element, expected on the new list.
     * @param count number of item changed consecutives to position index
     * @param payload unused.
     */
    override fun onChanged(position: Int, count: Int, payload: Any?) {
        for (index in position until position + count ) {
            val newPosition = operations?.convertOldPositionToNew(index)
            if (newPosition != null && newPosition < items.size && newPosition >= 0) {
                modify(items[newPosition])
            } else {
                Logger.instance.logError(_tag, "Try to change item out of bound: $index > items.size: ${items.size}")
            }
        }
    }

    /**
     * Implements [ListUpdateCallback.onMoved]. Unused, as order doesn't matter on map.
     */

    override fun onMoved(fromPosition: Int, toPosition: Int) { // nothing, no positions.
    }

    /**
     * Implements [ListUpdateCallback.onInserted].
     * @param position  position of the modified element, expected on the new list.
     * @param count number of item changed consecutives to position index
     */
    override fun onInserted(oldPosition: Int, count: Int) {
        // We expect that the element we need are just after the previous old element.
        // so, we take the elements just after the new position, of the previous elements.
        val newPosition = if (oldPosition == 0) oldPosition else ((operations?.convertOldPositionToNew(oldPosition - 1)?: -2) + 1)
        for (index in newPosition until newPosition + count ) {
            if (index < items.size  && index >= 0) {
                add(items[index])
            } else {
                Logger.instance.logError(_tag, "Try to insert item out of bound: $index > items.size: ${items.size}")
            }
        }
    }

    /**
     * Implements [ListUpdateCallback.onRemoved].
     * @param position position of the modified element, expected on the **old** list.
     * @param count number of item changed consecutives to position index.
     */
    override fun onRemoved(position: Int, count: Int) {
        for (index in position until position + count ) {
            if (index < oldItems.size) {
                remove(oldItems[index])
            } else {
                Logger.instance.logError(_tag,"Try to remove item out of bound: $index > items.size: ${oldItems.size}")
            }
        }
    }

    /**
     * The [DiffUtil.Callback] that translate the list update to a sequence of modified, inserted,
     * deleted ...
     */
    private val diff = object: DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].isTheSame(items[newItemPosition])
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItems[oldItemPosition].isSameContent(items[newItemPosition])
        }

        override fun getOldListSize(): Int = oldItems.size

        override fun getNewListSize(): Int = items.size
    }

    /**
     * Entry point to update items.
     * @param newItems new list of the items.
     */
    fun setItems(newItems: List<T>) {
        oldItems = items
        // Sort by uid to have a coherent order of the lists between diffs. It allows diffutils to
        // work correctly.
        items = newItems.sortedBy { it.uid }

        operations = DiffUtil.calculateDiff(diff)
        operations?.dispatchUpdatesTo(this)
    }

}*/

/**
 * Interface that items (ViewModels) should implement to use with Diffutils.
 */
interface DiffItem<T> {
    /**
     * Uid of the item.
     */
    val uid: String

    /**
     * Does [this] and an [other] item stands for the same thing.
     */
    fun isTheSame(other: T): Boolean

    /**
     * Does [this] and an [other] item have the same content.
     */
    fun isSameContent(other: T): Boolean
}
