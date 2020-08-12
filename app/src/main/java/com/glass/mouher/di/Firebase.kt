package com.glass.mouher.di

import com.glass.mouher.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.storage.FirebaseStorage

fun createAuthentication() = FirebaseAuth.getInstance()

fun createFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

fun createStorage(): FirebaseStorage {
    val storage = FirebaseStorage.getInstance()
    storage.maxOperationRetryTimeMillis = 30000
    storage.maxUploadRetryTimeMillis = 30000
    storage.maxDownloadRetryTimeMillis = 30000

    return storage
}

fun createRemoteConfig(): FirebaseRemoteConfig {
    val remoteConfig = FirebaseRemoteConfig.getInstance()
    val configSettings = FirebaseRemoteConfigSettings.Builder()
        .setMinimumFetchIntervalInSeconds(3600)
        .build()

    remoteConfig.setConfigSettingsAsync(configSettings)
    remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

    return remoteConfig
}
