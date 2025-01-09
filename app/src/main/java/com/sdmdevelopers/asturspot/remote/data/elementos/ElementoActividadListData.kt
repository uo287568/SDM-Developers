package com.sdmdevelopers.asturspot.remote.data.elementos

import com.google.gson.annotations.SerializedName

data class ElementoActividadListData(
    @SerializedName("status")
    val status: String,
    @SerializedName("request_id")
    val request_id: String,
    @SerializedName("parameters")
    val parameters: Parametros,
    @SerializedName("data")
    val data: List<ElementoActividadData>
)
