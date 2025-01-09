package com.sdmdevelopers.asturspot.elemento

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

interface Elemento : Parcelable {
    val id: String
    val nombre: String
    val tipo: String
    val descripcion: String
    val ubicacion: String
    val horario: String
    val paginaWeb: String
    val urlImagen: String
    val urlIcono: String
    val urlMaps: String?
}

@Parcelize
data class ElementoActividad(
    override val id: String,
    override val nombre: String,
    override val descripcion: String,
    override val ubicacion: String,
    val numTelefono: String,
    override val horario: String,
    override val paginaWeb: String,
    val estado: String,
    override val tipo: String,
    override val urlImagen: String,
    override val urlIcono: String,
    override val urlMaps: String?,
    val subtipos : List<String>,
    val rating : Float,
    val latitude : Double,
    val longitude : Double
): Elemento

@Parcelize
data class Evento(
    override val id: String,
    override val nombre: String,
    override val descripcion: String,
    override val ubicacion: String,
    val local : String,
    override val horario: String,
    override val paginaWeb: String,
    val tickets : List<String>,
    override val tipo: String,
    override val urlImagen: String,
    override val urlIcono: String,
    override val urlMaps: String?
): Elemento