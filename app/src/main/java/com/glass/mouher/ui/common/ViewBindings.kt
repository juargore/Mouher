@file:Suppress("UNCHECKED_CAST")

package com.glass.mouher.ui.common

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable
import android.text.util.Linkify
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.util.LinkifyCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.glass.mouher.R
import com.glass.mouher.ui.common.binder.BindingRecyclerViewAdapter
import com.glass.mouher.ui.common.binder.ClickHandler
import com.glass.mouher.ui.common.binder.ItemBinder
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern


private const val KEY_ITEMS = -123
private const val KEY_CLICK_HANDLER = -124
private const val KEY_PROPERTY_CHANGE_CALLBACK = -125
private const val KEY_RECYCLABLE_ITEMS = -126
private const val KEY_SWIPE_HELPER = -127


@BindingAdapter("scrollTo")
fun scrollTo(recyclerView: RecyclerView, position: Int) {
    (recyclerView.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(position, 0)
}

@BindingAdapter("itemsRecyclable")
fun setItemsRecyclable(recyclerView: RecyclerView, itemsRecyclable: Boolean) {
    val adapter = recyclerView.adapter as BindingRecyclerViewAdapter<*>?
    if (adapter != null) {
        adapter.setItemsRecyclable(itemsRecyclable)
    } else {
        recyclerView.setTag(KEY_RECYCLABLE_ITEMS, itemsRecyclable)
    }
}

@BindingAdapter("items")
fun <T: Observable> setItems(recyclerView: RecyclerView, items: Collection<T>?) {
    if (items == null) {
        return
    }

    val adapter = recyclerView.adapter as BindingRecyclerViewAdapter<T>?
    if (adapter != null) {
        adapter.setItems(items)
    } else {
        recyclerView.setTag(KEY_ITEMS, items)
    }
}

@BindingAdapter("clickHandler")
fun <T: Observable> setHandler(recyclerView: RecyclerView, handler: ClickHandler<T>) {
    val adapter = recyclerView.adapter as BindingRecyclerViewAdapter<T>?
    if (adapter != null) {
        adapter.setClickHandler(handler)
    } else {
        recyclerView.setTag(KEY_CLICK_HANDLER, handler)
    }
}

@BindingAdapter("itemPropertyChangeCallback")
fun setPropertyChangeCallback(recyclerView: RecyclerView, callback: Observable.OnPropertyChangedCallback) {
    val adapter = recyclerView.adapter as BindingRecyclerViewAdapter<*>?
    if (adapter != null) {
        adapter.setPropertyChangedCallback(callback)
    } else {
        recyclerView.setTag(KEY_PROPERTY_CHANGE_CALLBACK, callback)
    }
}

@BindingAdapter("itemSwipeHelper")
fun setItemSwipeHelper(recyclerView: RecyclerView, helper: ViewBinderHelper) {
    val adapter = recyclerView.adapter as BindingRecyclerViewAdapter<*>?
    if (adapter != null) {
        adapter.setSwipeHelper(helper)
    } else {
        recyclerView.setTag(KEY_SWIPE_HELPER, helper)
    }
}


@BindingAdapter("itemViewBinder")
fun <T: Observable> setItemViewBinder(recyclerView: RecyclerView, itemViewMapper: ItemBinder<T>?) {
    if (itemViewMapper == null) {
        Log.e("ERROR",("itemViewBinder: itemViewMapperIsNull, does the list display ?"))
        return
    }

    val items = recyclerView.getTag(KEY_ITEMS) as Collection<T>
    val clickHandler = recyclerView.getTag(KEY_CLICK_HANDLER) as ClickHandler<T>?
    val propertyChangedCallback = recyclerView.getTag(KEY_PROPERTY_CHANGE_CALLBACK) as Observable.OnPropertyChangedCallback?
    val itemsRecyclable = recyclerView.getTag(KEY_RECYCLABLE_ITEMS) as Boolean?
    val swipeHelper = recyclerView.getTag(KEY_SWIPE_HELPER) as ViewBinderHelper?
    val adapter = BindingRecyclerViewAdapter(itemViewMapper, items)
    if (clickHandler != null) {
        adapter.setClickHandler(clickHandler)
    }
    if (propertyChangedCallback != null) {
        adapter.setPropertyChangedCallback(propertyChangedCallback)
    }
    if (itemsRecyclable != null) {
        adapter.setItemsRecyclable(itemsRecyclable)
    }
    if (swipeHelper != null) {
        adapter.setSwipeHelper(swipeHelper)
    }
    recyclerView.adapter = adapter
}

@BindingAdapter("backgroundRes")
fun setBackgroundRes(view: View, resource: Int) {
    view.setBackgroundResource(resource)
}

@BindingAdapter("android:src")
fun setImageResource(imageView: ImageView, resource: Int) {
    imageView.setImageResource(resource)
}

@BindingAdapter("android:tint")
fun setImageDrawableTint(imageView: ImageView, tintColor: Int) {
    imageView.colorFilter = PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_ATOP)
}

/**
 * Set image src from pictures bytes (jpg, png, ...), using Glide.
 * @param imageView the image view involved.
 * @param bytes the [ByteArray] of the file to be set.
 */
@BindingAdapter("android:src")
fun setImageBytes(imageView: ImageView, bytes: ByteArray?) {
    if (bytes == null) {
        imageView.setImageDrawable(null)
    } else {
        Glide.with(imageView.context)
            .load(bytes)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(imageView)
    }
}

@BindingAdapter("fromUrl")
fun setImageFromUrl(imageView: ImageView, url: String?) {
    if (url != null) {
        Glide.with(imageView.context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }
}

@BindingAdapter("circleFromUrl")
fun setImageCircleFromUrl(imageView: ImageView, url: String?) {
    if (url != null) {
        Glide.with(imageView.context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }
}

@BindingAdapter("circleFromResource")
fun setImageCircleFromResource(imageView: ImageView, resource: Int?) {
    if (resource != null) {
        Glide.with(imageView.context).load(resource)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions.circleCropTransform())
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }
}

@BindingAdapter("textWithLinks")
fun setTextWithLinks(textView: TextView, message: String) {
    textView.text = message
    LinkifyCompat.addLinks(textView, Linkify.ALL)
    val pattern = Pattern.compile("([\\d|(][\\s|(\\d{3})|.|\\-|\\d]{4,}\\d{4,})", Pattern.CASE_INSENSITIVE);
    LinkifyCompat.addLinks(textView, pattern, "tel://")
}