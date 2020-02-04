package com.rizky.madefinalsubmission.network

import com.rizky.madefinalsubmission.BuildConfig.API_KEY
import com.rizky.madefinalsubmission.BuildConfig.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiRepository {
    private var retrofit: Retrofit? = null
    fun getClient(): Retrofit? {
        val okHttpClient = OkHttpClient().newBuilder().addInterceptor { chain ->
            var originalRequest = chain.request()
            val httpUrl = originalRequest.url
                .newBuilder()
                .addQueryParameter("api_key", API_KEY)
                .build()
            originalRequest = originalRequest.newBuilder()
                .url(httpUrl)
                .build()
            chain.proceed(originalRequest)
        }.build()
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }
}