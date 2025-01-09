package com.sdmdevelopers.asturspot.remote

import com.sdmdevelopers.asturspot.elemento.ElementoActividad
import com.sdmdevelopers.asturspot.elemento.Evento
import com.sdmdevelopers.asturspot.remote.data.elementos.ElementoActividadData
import com.sdmdevelopers.asturspot.remote.data.eventos.EventoData

object DataMapper {

    fun convertElementListToDomain(elementData: List<ElementoActividadData>): MutableList<ElementoActividad> {
        val lElementos: MutableList<ElementoActividad> = mutableListOf()

        for (elemento in elementData) {
            //capitalizamos el nombre (primera letra en mayúsculas)
            var nombre = elemento.name.lowercase()
            nombre = nombre.get(0).titlecase() + nombre.substring(1)

            val descripcion = if (elemento.about == null || elemento.about.resumen == null) {
                "[Descripción no disponible]"
            } else {
                elemento.about.resumen
            }
            val numTelefono = if (elemento.phone_number == null) {
                "[Número no disponible]"
            } else {
                elemento.phone_number
            }
            val horario = if (elemento.working_hours == null) {
                "[Horario no disponible]"
            } else {
                elemento.working_hours.toString()
            }
            val paginaWeb = if (elemento.website == null) {
                "[Página no disponible]"
            } else {
                elemento.website
            }
            val ubicacion = if (elemento.full_address == null) {
                if(elemento.address == null){
                    "[Ubicación no disponible]"
                }
                else{
                    elemento.address
                }
            } else {
                elemento.full_address
            }
            //por defecto cargamos la imagen de foto no disponible
            var photoMin = "https://static.vecteezy.com/system/resources/previews/005/720/408/non_2x/crossed-image-icon-picture-not-available-delete-picture-symbol-free-vector.jpg"
            var photoLarge = "https://static.vecteezy.com/system/resources/previews/005/720/408/non_2x/crossed-image-icon-picture-not-available-delete-picture-symbol-free-vector.jpg"
            //si la respuesta incluye fotos las cargamos
            if (elemento.photos_sample != null && elemento.photos_sample[0] != null) {
                if (elemento.photos_sample[0].photo_url != null) {
                    photoMin = elemento.photos_sample[0].photo_url
                }
                if (elemento.photos_sample[0].photo_url_large != null) {
                    photoLarge = elemento.photos_sample[0].photo_url_large
                }
            }
            val subtypes = if(elemento.subtypes!=null){
                elemento.subtypes
            }
            else{
                emptyList<String>()
            }

            lElementos.add(
                ElementoActividad(
                    elemento.business_id,
                    nombre,
                    descripcion,
                    ubicacion,
                    numTelefono,
                    horario,
                    paginaWeb,
                    elemento.business_status,
                    elemento.type,
                    photoLarge,
                    photoMin,
                    elemento.place_link,
                    subtypes,
                    elemento.rating,
                    elemento.latitude,
                    elemento.longitude
                )
            )
        }
        return lElementos
    }

    fun convertEventListToDomain(elementData: List<EventoData>): MutableList<Evento> {
        val lEventos: MutableList<Evento> = mutableListOf()
        val lIds: MutableList<String> = mutableListOf()

        for (elemento in elementData) {
            //capitalizamos el nombre (primera letra en mayúsculas)
            var nombre = elemento.title.lowercase()
            nombre = nombre.get(0).titlecase() + nombre.substring(1)
            //como los eventos no vienen con id generamos uno a partir del nombre
            var id = ""
            nombre.chars().forEach{
                id += ""+it.toInt()
            }
            //como la api a veces devuelve resultados repetidos guardamos una lista con los ids de los eventos
            //para evitar que se carguen dos veces
            if(!lIds.contains(id)){
                lIds.add(id)
                val descripcion = if (elemento.description == null) {
                    "[Descripción no disponible]"
                } else {
                    elemento.description
                }
                val horario = if (elemento.date.date == null) {
                    "[Horario no disponible]"
                } else {
                    elemento.date.date
                }
                val ubicacion = if (elemento.address == null || elemento.address[0] == null) {
                    "[Ubicación no disponible]"
                } else {
                    elemento.address[0]
                }
                val local = if (elemento.local == null || elemento.local.name == null){
                    "[Local no disponible]"
                } else{
                    elemento.local.name
                }
                val paginaWeb = if (elemento.link == null) {
                        "[Página web no disponible]"
                    } else{
                        elemento.link
                }

                val image = if (elemento.image == null) {
                    "https://static.vecteezy.com/system/resources/previews/005/720/408/non_2x/crossed-image-icon-picture-not-available-delete-picture-symbol-free-vector.jpg"
                } else {
                    elemento.image
                }
                val icono = if (elemento.thumbnail == null) {
                    "https://static.vecteezy.com/system/resources/previews/005/720/408/non_2x/crossed-image-icon-picture-not-available-delete-picture-symbol-free-vector.jpg"
                } else {
                    elemento.thumbnail
                }
                var tickets = emptyList<String>()
                if(elemento.tickets!=null){
                    elemento.tickets.forEach{
                        tickets += it.link
                    }
                }
                val urlMaps = if (elemento.location == null ){
                    null
                } else{
                    elemento.location.link
                }
                lEventos.add(
                    Evento(
                        id,
                        nombre,
                        descripcion,
                        ubicacion,
                        local,
                        horario,
                        paginaWeb,
                        tickets,
                        "Evento",
                        image,
                        icono,
                        urlMaps
                    )
                )
            }
        }
        return lEventos
    }
}