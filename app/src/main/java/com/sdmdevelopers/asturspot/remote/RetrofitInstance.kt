package com.sdmdevelopers.asturspot.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    //Singleton
    companion object {
        val API_LOCALBUSINESSDATA = "https://local-business-data.p.rapidapi.com/"
        val API_SERPAPI  = "https://serpapi.com/"

        private val _retrofitElements by lazy {
            // Interceptor para ver el log de peticiones
            val logging  = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            Retrofit.Builder()
                .baseUrl(API_LOCALBUSINESSDATA)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }

        private val _retrofitEvents by lazy {
            val logging  = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val httpClient : OkHttpClient.Builder = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)

            Retrofit.Builder()
                .baseUrl(API_SERPAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
        }

        val localBusinessApi by lazy {
            _retrofitElements.create(LocalBusinessAPI::class.java)
        }

        val serpApi by lazy {
            _retrofitEvents.create(SerpAPI::class.java)
        }
    }
}