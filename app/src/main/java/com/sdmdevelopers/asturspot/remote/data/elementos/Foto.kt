package com.sdmdevelopers.asturspot.remote.data.elementos

import com.google.gson.annotations.SerializedName

data class Foto(
    @SerializedName("photo_id")
    val photo_id: String,
    @SerializedName("photo_url")
    val photo_url: String,
    @SerializedName("photo_url_large")
    val photo_url_large: String,
    @SerializedName("video_thumbnail_url")
    val video_thumbnail_url: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("type")
    val type: String,
    @SerializedName("photo_datetime_utc")
    val photo_datetime_utc: String,
    @SerializedName("photo_timestamp")
    val photo_timestamp: Long,
)
