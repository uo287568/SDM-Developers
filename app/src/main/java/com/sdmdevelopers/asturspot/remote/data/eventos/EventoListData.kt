package com.sdmdevelopers.asturspot.remote.data.eventos

import com.google.gson.annotations.SerializedName

data class EventoListData(
    @SerializedName("events_results")
    val data: List<EventoData>,
)
