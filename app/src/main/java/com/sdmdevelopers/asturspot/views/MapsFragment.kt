package com.sdmdevelopers.asturspot.views

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sdmdevelopers.asturspot.R
import com.sdmdevelopers.asturspot.elemento.Elemento
import com.sdmdevelopers.asturspot.elemento.ElementoActividad
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MapsFragment : Fragment() , OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var cliente: FusedLocationProviderClient
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                inicializarMapa()
            } else {
                Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuramos el Toolbar para mostrar solo la flecha de retroceso
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // Mostramos la flecha de retroceso
            setDisplayShowTitleEnabled(false) // Ocultamos el título
        }

        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp() // Regresa a la pantalla anterior
        }

        cliente = LocationServices.getFusedLocationProviderClient(activity as AppCompatActivity)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        vistaInicial()
        inicializarMapa()
    }

    private fun vistaInicial() {
        val so = LatLng(42.766774247584124, -7.289599009856057)
        val ne = LatLng(43.706240090485714, -4.3764088984002)
        val asturiasBounds = LatLngBounds(so, ne)
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(asturiasBounds,1080, 1080, 0),1000, null)
    }

    private fun inicializarMapa() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
            obtenerUbicacionUsuario()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun obtenerUbicacionUsuario() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            try {
                cliente.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        val userLatLng = LatLng(location.latitude, location.longitude)
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))
                        if(currentUser != null){
                            mostrarFavoritos()
                        }
                    } else {
                        Toast.makeText(context, "No se pudo obtener la ubicación actual", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: SecurityException) {
                Toast.makeText(context, "Error de permisos: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Permiso de ubicación no concedido", Toast.LENGTH_SHORT).show()
        }
    }

    private fun mostrarFavoritos() {
        lifecycleScope.launch(Dispatchers.IO) {
            val elementos =loadFavorites()
            withContext(Dispatchers.Main) {
                elementos.forEach { elemento ->
                val marker = MarkerOptions()
                    .position(LatLng(elemento.latitude, elemento.longitude))
                    .title(elemento.nombre)
                    .snippet(elemento.tipo)
                    .anchor(0.5f,0.5f)
                    mMap.addMarker(marker)
                }
            }
        }
    }

    private suspend fun loadFavorites(): MutableList<ElementoActividad> {
        val favoriteElementList = mutableListOf<ElementoActividad>()
            val documents = db.collection("users")
                .document(currentUser!!.uid)
                .collection("favorites")
                .get()
                .await()

            for (document in documents) {
                val id = document.id
                val nombre = document.getString("nombre") ?: ""
                val tipo = document.getString("tipo") ?: ""
                if(tipo != "Evento"){
                    val descripcion = document.getString("descripcion") ?: ""
                    val ubicacion = document.getString("ubicacion") ?: ""

                    val horario = document.getString("horario") ?: ""

                    val urlImagen = document.getString("urlImagen") ?: ""
                    val urlIcono = document.getString("urlIcono") ?: ""
                    val urlMaps = document.getString("urlMaps") ?: ""
                    val paginaWeb = document.getString("paginaWeb") ?: ""

                    var elemento : Elemento
                    val numTelefono = document.getString("numTelefono") ?: ""
                    val estado = document.getString("estado") ?: ""
                    val rating = document.getDouble("rating")!!.toFloat()
                    val latitude = document.getDouble("latitude")!!
                    val longitude = document.getDouble("longitude")!!

                    elemento = ElementoActividad(
                        id = id,
                        nombre = nombre,
                        descripcion = descripcion,
                        ubicacion = ubicacion,
                        numTelefono = numTelefono,
                        horario = horario,
                        paginaWeb = paginaWeb,
                        estado = estado,
                        tipo = tipo,
                        urlImagen = urlImagen,
                        urlIcono = urlIcono,
                        urlMaps = urlMaps,
                        emptyList(),
                        rating = rating,
                        latitude = latitude,
                        longitude = longitude
                    )
                    favoriteElementList.add(elemento)
                }
        }
        return favoriteElementList
    }
}