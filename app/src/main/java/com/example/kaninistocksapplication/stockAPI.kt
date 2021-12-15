package com.example.kaninistocksapplication

import android.app.Application
import android.content.Context
import android.widget.TextView
import com.fasterxml.jackson.databind.ObjectMapper
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import okhttp3.Interceptor

import okhttp3.OkHttpClient
import okhttp3.Request

class stockAPI: Application() {



    public lateinit var httpApiService: HttpApiService
    override fun onCreate() {
        super.onCreate()


        httpApiService = initStockApiService()
    }

    private fun initStockApiService(): HttpApiService {
        var mSettings = getSharedPreferences("mysettings", Context.MODE_PRIVATE)
        val token = mSettings.getString("token", "")!!

        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val newRequest: Request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://android-kanini-course.cloud/priceapp-secure-backend/")
            .addConverterFactory(JacksonConverterFactory.create(ObjectMapper()))
            .client(client)
            .build()
        return retrofit.create(HttpApiService::class.java)
    }
}
