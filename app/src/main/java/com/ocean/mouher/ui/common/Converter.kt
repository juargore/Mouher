package com.ocean.mouher.ui.common

fun completeUrlForImage(imageUrl: String?): String{
    return if(imageUrl.isNullOrEmpty()){
        ""
    } else{
        "${""}${imageUrl}"
    }
}

fun completeUrlForImageOnStore(imageUrl: String?, storeId: String): String{
    return if(imageUrl.isNullOrEmpty()){
        ""
    } else{
        "${""}/tienda${storeId}/${imageUrl}"
    }
}