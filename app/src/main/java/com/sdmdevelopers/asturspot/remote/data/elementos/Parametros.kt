package com.sdmdevelopers.asturspot.remote.data.elementos

import com.google.gson.annotations.SerializedName

data class Parametros(
    @SerializedName("query")
    val Query: String,
    @SerializedName("language")
    val Idioma: String,
    @SerializedName("region")
    val Region: String,
    @SerializedName("lat")
    val Lat: Double,
    @SerializedName("lng")
    val Lng: Double,
    @SerializedName("zoom")
    val Zoom: Int,
    @SerializedName("limit")
    val Limit: Int,
    @SerializedName("extract_emails_and_contacts")
    val emails: Boolean
)