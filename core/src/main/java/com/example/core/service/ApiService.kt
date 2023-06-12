package com.example.core.service

import com.example.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiService {
    val retrofitService: ApiServiceDao by lazy {
        getRetrofit().create(ApiServiceDao::class.java)
    }

    private fun okHttpProvider(): OkHttpClient {
        val bodyInterceptor = HttpLoggingInterceptor()
        bodyInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(bodyInterceptor)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpProvider())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}