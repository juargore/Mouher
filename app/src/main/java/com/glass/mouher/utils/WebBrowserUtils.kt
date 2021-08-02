package com.glass.mouher.utils

import android.content.Intent
import android.net.Uri
import com.glass.mouher.App.Companion.context

object WebBrowserUtils {

    fun openUrlInExternalWebBrowser(url: String){
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context?.startActivity(browserIntent)
    }
}