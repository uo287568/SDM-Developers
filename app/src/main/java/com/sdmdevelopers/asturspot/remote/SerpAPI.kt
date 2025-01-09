package com.sdmdevelopers.asturspot.remote

import com.sdmdevelopers.asturspot.remote.data.eventos.EventoListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SerpAPI {

    @GET("search")
    suspend fun getListEventos(
        @Query("api_key") apiKey : String,
        @Query("engine") engine: String,
        @Query("q") query : String,
        @Query("hl") language : String,
        @Query("gl") country : String,
        @Query("google_domain") domain : String,
        @Query("location") location : String,
        @Query("start") start : Int
    ): Response<EventoListData>

}