package com.ocean.mouher.di

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.ocean.domain.common.NoConnectivityException
import com.ocean.mouher.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

fun createNetworkClient(
    context: Context,
    baseUrl: String
) = retrofitClient(baseUrl, httpClient(context))

class NetworkConnectionInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isNetworkConnected()) {
            throw NoConnectivityException()
        }
        val builder: Request.Builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }

    @SuppressLint("MissingPermission")
    @Suppress("DEPRECATION")
    private fun isNetworkConnected() : Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager

        if (cm != null) {
            if (Build.VERSION.SDK_INT < 23) {
                val networkInfo = cm.activeNetworkInfo

                if (networkInfo != null) {
                    return (networkInfo.isConnected &&
                            (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE))
                }
            } else {
                val network = cm.activeNetwork

                if (network != null) {
                    val networkCapabilities = cm.getNetworkCapabilities(network)

                    return (networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ?: false ||
                            networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ?: false)
                }
            }
        }
        return false
    }
}

private fun httpClient(context: Context): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
    if (BuildConfig.DEBUG) {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        clientBuilder.addInterceptor(httpLoggingInterceptor)
    }
    clientBuilder.addInterceptor(NetworkConnectionInterceptor(context))
    clientBuilder.readTimeout(120, TimeUnit.SECONDS)
    clientBuilder.writeTimeout(120, TimeUnit.SECONDS)
    return clientBuilder.build()
}

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit {
    val moshi = Moshi.Builder()
        .add(DateMoshiAdapter())
        .build()
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
}

class DateMoshiAdapter {
    @ToJson fun dateToJson(date: Date): Long = date.time
}