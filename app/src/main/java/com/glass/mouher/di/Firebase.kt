package com.glass.mouher.di

import com.google.firebase.storage.FirebaseStorage

fun createStorage(): FirebaseStorage {
    val storage = FirebaseStorage.getInstance()
    storage.maxOperationRetryTimeMillis = 30000
    storage.maxUploadRetryTimeMillis = 30000
    storage.maxDownloadRetryTimeMillis = 30000

    return storage
}
