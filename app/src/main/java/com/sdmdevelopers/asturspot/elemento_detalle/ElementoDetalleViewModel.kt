package com.sdmdevelopers.asturspot.elemento_detalle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sdmdevelopers.asturspot.elemento.ElementoActividad
import com.sdmdevelopers.asturspot.elemento.Evento

class ElementoDetalleViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    private val _isFavorited = MutableLiveData<Boolean>()
    val isFavorited: LiveData<Boolean> = _isFavorited

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _canUseFavorites = MutableLiveData<Boolean>()
    val canUseFavorites: LiveData<Boolean> = _canUseFavorites

    // Verificar si un elemento está en favoritos
    fun checkIfFavorited(eventId: String) {
        if (currentUser != null) {
            db.collection("users")
                .document(currentUser.uid)
                .collection("favorites")
                .document(eventId)
                .get()
                .addOnSuccessListener { document ->
                    _isFavorited.value = document.exists()
                }
        }
    }

    fun canUseFavorites(){
        _canUseFavorites.value = currentUser != null
        if(!_canUseFavorites.value!!){
            _message.value = "Inicia sesión para utilizar los favoritos"
        }
    }

    // Añadir un elemento a favoritos
    fun addToFavorites(elemento: ElementoActividad) {
        //si ya está en favoritos no hacemos nada
        if (isFavorited.value!= null && isFavorited.value!! ) {
            return
        }
        val favoriteData = mapOf(
            "nombre" to elemento.nombre,
            "descripcion" to elemento.descripcion,
            "tipo" to elemento.tipo,
            "ubicacion" to elemento.ubicacion,
            "horario" to elemento.horario,
            "horarioEstado" to elemento.estado,
            "numTel" to elemento.numTelefono,
            "pagWeb" to elemento.paginaWeb,
            "urlImagen" to elemento.urlImagen,
            "estado" to elemento.estado,
            "urlMaps" to elemento.urlMaps,
            "rating" to elemento.rating,
            "latitude" to elemento.latitude,
            "longitude" to elemento.longitude
        )

        db.collection("users")
            .document(currentUser!!.uid)
            .collection("favorites")
            .document(elemento.id)
            .set(favoriteData)
            .addOnSuccessListener {
                _isFavorited.value = true
                _message.value = "Añadido a favoritos"
            }
            .addOnFailureListener {
                _message.value = "Error al añadir a favoritos"
            }
    }

    fun addToFavorites(elemento: Evento) {
        if (isFavorited.value!! ) {
            return
        }
        val favoriteData = mapOf(
            "nombre" to elemento.nombre,
            "descripcion" to elemento.descripcion,
            "ubicacion" to elemento.ubicacion,
            "tipo" to elemento.tipo,
            "local" to elemento.local,
            "paginaWeb" to elemento.paginaWeb,
            "horario" to elemento.horario,
            "tickets" to elemento.tickets,
            "urlImagen" to elemento.urlImagen,
            "urlIcono" to elemento.urlIcono,
            "urlMaps" to elemento.urlMaps
        )
        db.collection("users")
            .document(currentUser!!.uid)
            .collection("favorites")
            .document(elemento.id)
            .set(favoriteData)
            .addOnSuccessListener {
                _isFavorited.value = true
                _message.value = "Añadido a favoritos"
            }
            .addOnFailureListener {
                _message.value = "Error al añadir a favoritos"
            }
    }

    // Eliminar un elemento de favoritos
    fun removeFromFavorites(eventId: String) {
        if (!isFavorited.value!! ) {
            return
        }
        db.collection("users")
            .document(currentUser!!.uid)
            .collection("favorites")
            .document(eventId)
            .delete()
            .addOnSuccessListener {
                _isFavorited.value = false
                _message.value = "Eliminado de favoritos"
            }
            .addOnFailureListener {
                _message.value = "Error al eliminar de favoritos"
            }
    }

    // Actualizar el estado actual (por ejemplo, al girar la pantalla)
    fun setFavoriteState(isFavorited: Boolean) {
        _isFavorited.value = isFavorited
    }
}