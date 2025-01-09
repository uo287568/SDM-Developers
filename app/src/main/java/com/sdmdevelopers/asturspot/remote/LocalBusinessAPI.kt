package com.sdmdevelopers.asturspot.remote

import com.sdmdevelopers.asturspot.remote.data.elementos.ElementoActividadListData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface LocalBusinessAPI {

    @GET("search")
    @Headers("x-rapidapi-key: be99f62281msh2f311bb475ae569p1b5a71jsn329a77f33a1b",
        "x-rapidapi-host: local-business-data.p.rapidapi.com",
        "X-User-Agent: mobile")
    suspend fun getListElements(
        @Query("query") query : String,
        @Query("limit") limit : Int,
        @Query("language") language : String,
        @Query("region") region : String
    ): Response<ElementoActividadListData>

}