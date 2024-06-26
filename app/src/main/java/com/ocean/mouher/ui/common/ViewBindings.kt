@file:Suppress("UNCHECKED_CAST")
package com.ocean.mouher.ui.common

import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.Observable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.ocean.domain.entities.TopBannerUI
import com.ocean.mouher.R
import com.ocean.mouher.extensions.startFadeInAnimation
import com.ocean.mouher.ui.common.binder.BindingRecyclerViewAdapter
import com.ocean.mouher.ui.common.binder.ClickHandler
import com.ocean.mouher.ui.common.binder.ItemBinder
import com.ocean.mouher.utils.WebBrowserUtils
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener

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

    try{
        val adapter = recyclerView.adapter as? BindingRecyclerViewAdapter<T>?
        if (adapter != null) {
            adapter.setItems(items)
        } else {
            recyclerView.setTag(KEY_ITEMS, items)
        }
    }catch (e: Exception){
        Log.e("--", "Exception on MenuItemCategoriesAdapter: ${e.message}")
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
    if (!url.isNullOrBlank()) {
        Glide.with(imageView.context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }
}



@BindingAdapter("backgroundLayout")
fun setLayoutBackground(linearLayout: LinearLayout, color: String?) {
    if (!color.isNullOrBlank()) {
        linearLayout.setBackgroundColor(Color.parseColor(color))
    }
}


@BindingAdapter("textColorAndShadow")
fun setTextViewColorAndShadow(textView: TextView, color: String?) {
    if (!color.isNullOrBlank()) {
        textView.setTextColor(Color.parseColor(color))
        textView.setShadowLayer(1f,0f,0f, Color.GRAY)
        textView.includeFontPadding = false
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

@BindingAdapter("rotationFromResource")
fun setRotationForImage(imageView: ImageView, angle: Int?) {
    if (angle != null) {
        imageView.animate().rotation(imageView.rotation + angle).start()
    }
}

@BindingAdapter("strikeThrough")
fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
    if (strikeThrough) {
        textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    } else {
        textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }
}

@BindingAdapter("doubleAsString")
fun setDoubleAsString(textView: TextView, double: Double?) {
    double?.let{
        val rounded = String.format("%.2f", double)
        textView.text = rounded
    }
}

@BindingAdapter("intAsString")
fun setIntAsString(textView: TextView, intNumber: Int?) {
    intNumber?.let{
        textView.text = it.toString()
    }
}

@BindingAdapter(value = ["items", "title", "subtitle"])
fun CarouselView.setImagesOnCarousel(items: List<TopBannerUI>, carouselTitle: TextView, carouselSubTitle: TextView){
    val imageListener = ImageListener { position, imageView ->
        Glide.with(carouselTitle.context)
            .load(items[position].imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.ic_blur)
            .into(imageView)
    }

    setImageListener(imageListener)
    pageCount = items.size

    addOnPageChangeListener(object : ViewPager.OnPageChangeListener{

        override fun onPageSelected(position: Int) {}
        override fun onPageScrollStateChanged(state: Int) {}

        override fun onPageScrolled(pos: Int, posOffset: Float, posOffsetPixels: Int) {
            if (items.isNotEmpty()){
                carouselTitle.apply {
                    text = items[pos].title
                    startFadeInAnimation()
                }
                carouselSubTitle.apply {
                    text = items[pos].subtitle
                    startFadeInAnimation()
                }
            }
        }
    })

    setImageClickListener { position->
        val item = items[position]
        if(!item.linkToOpen.isNullOrBlank()){
            WebBrowserUtils.openUrlInExternalWebBrowser(item.linkToOpen!!)
        }
    }
}

