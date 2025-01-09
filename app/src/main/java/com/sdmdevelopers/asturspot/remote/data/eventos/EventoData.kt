package com.sdmdevelopers.asturspot.remote.data.eventos

import com.google.gson.annotations.SerializedName

data class EventoData(
    @SerializedName("title")
    val title: String,
    @SerializedName("date")
    val date: EventoDate,
    @SerializedName("address")
    val address: List<String>,
    @SerializedName("link")
    val link: String,
    @SerializedName("event_location_map")
    val location : Location,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("thumbnail")
    val thumbnail: String,
    @SerializedName("ticket_info")
    val tickets : List<Ticket>,
    @SerializedName("venue")
    val local : Local
)

data class EventoDate(
    @SerializedName("when")
    val date: String,
)

data class Ticket(
    @SerializedName("link")
    val link: String,
)

data class Local(
    @SerializedName("name")
    val name: String,
    @SerializedName("link")
    val link: String,
)

data class Location(
    @SerializedName("link")
    val link: String,
)

