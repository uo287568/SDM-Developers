package com.sdmdevelopers.asturspot.remote.data.elementos

import com.google.gson.annotations.SerializedName

data class ElementoActividadData(
    @SerializedName("business_id")
    val business_id: String,
    @SerializedName("google_id")
    val google_id: String,
    @SerializedName("place_id")
    val place_id: String,
    @SerializedName("phone_number")
    val phone_number: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("full_address")
    val full_address: String,
    @SerializedName("review_count")
    val review_count: Int,
    @SerializedName("rating")
    val rating: Float,
    @SerializedName("working_hours")
    val working_hours: Horario,
    @SerializedName("website")
    val website: String,
    @SerializedName("verified")
    val verified: Boolean,
    @SerializedName("place_link")
    val place_link: String,
    @SerializedName("cid")
    val cid: String,
    @SerializedName("reviews_link")
    val reviews_link: String,
    @SerializedName("owner_id")
    val booking_link: String,
    @SerializedName("reservations_link")
    val reservations_link: String,
    @SerializedName("business_status")
    val business_status: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("subtypes")
    val subtypes: List<String>,
    @SerializedName("photos_sample")
    val photos_sample: List<Foto>,
    @SerializedName("photo_count")
    val photo_count: Int,
    @SerializedName("about")
    val about: AcercaDe,
    @SerializedName("address")
    val address: String,
    @SerializedName("latitude")
    val latitude : Double,
    @SerializedName("longitude")
    val longitude : Double,
)