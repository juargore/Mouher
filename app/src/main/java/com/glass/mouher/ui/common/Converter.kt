package com.glass.mouher.ui.common

import com.glass.mouher.BuildConfig

fun completeUrlForImage(imageUrl: String?): String{
    return if(imageUrl.isNullOrEmpty()){
        ""
    } else{
        "${BuildConfig.IMAGE_URL_MALL}${imageUrl}"
    }
}

fun completeUrlForImageOnStore(imageUrl: String?, storeId: String): String{
    return if(imageUrl.isNullOrEmpty()){
        ""
    } else{
        "${BuildConfig.IMAGE_URL_STORE}/tienda${storeId}/${imageUrl}"
    }
}