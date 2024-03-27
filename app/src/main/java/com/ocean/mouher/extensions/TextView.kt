package com.ocean.mouher.extensions

import android.view.animation.AnimationUtils
import android.widget.TextView
import com.ocean.mouher.R

fun TextView.startFadeInAnimation(){
    val animation = AnimationUtils.loadAnimation(this.context, R.anim.banner_text_animation)
    this.startAnimation(animation)
}
