package com.sdmdevelopers.asturspot.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sdmdevelopers.asturspot.R
import com.sdmdevelopers.asturspot.elemento.Elemento
import com.sdmdevelopers.asturspot.elemento.ElementoActividad
import com.sdmdevelopers.asturspot.elemento.ElementoAdapter
import com.sdmdevelopers.asturspot.elemento.Evento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class FavoritosFragment : Fragment() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var adapter: ElementoAdapter
    private var listaElementos : MutableList<Elemento> = mutableListOf()

    private val db = FirebaseFirestore.getInstance()
    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        recyclerView = view.findViewById(R.id.recyclerFavoritos)
        searchView = view.findViewById(R.id.searchView)

        inicializaRecyclerElementos()

        configurarSearchView()

        return view
    }

    private fun inicializaRecyclerElementos() {
        lifecycleScope.launch(Dispatchers.IO) {
            listaElementos = loadFavorites()

            withContext(Dispatchers.Main) {
                recyclerView.layoutManager = LinearLayoutManager(requireContext())
                adapter = ElementoAdapter(listaElementos) { elemento ->
                    if(elemento is ElementoActividad){
                        val destino =
                            FavoritosFragmentDirections.actionFavoritosFragmentToElementoDetalle(
                                elemento as ElementoActividad
                            )
                        findNavController().navigate(destino)
                    }
                    else{
                        val destino =
                            FavoritosFragmentDirections.actionFavoritosFragmentToEventoDetalle(
                                elemento!! as Evento
                            )
                        findNavController().navigate(destino)
                    }

                }
                recyclerView.adapter = adapter

                // Configurar el SearchView después de inicializar el adaptador
                configurarSearchView()
            }
        }
    }


    private fun configurarSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText)
                }
                return true
            }
        })
    }

    private fun filterList(query: String) {
        if (::adapter.isInitialized) { // Verificar si el adaptador está inicializado
            val filteredList = listaElementos.filter {
                it.nombre.contains(query, ignoreCase = true)
            }
            adapter.updateList(filteredList)
        }
    }

    private suspend fun loadFavorites(): MutableList<Elemento> {
        val favoriteElementList = mutableListOf<Elemento>()
        if (currentUser != null) {
            val documents = db.collection("users")
                .document(currentUser.uid)
                .collection("favorites")
                .get()
                .await()

            for (document in documents) {
                val id = document.id
                val nombre = document.getString("nombre") ?: ""
                val tipo = document.getString("tipo") ?: ""
                val descripcion = document.getString("descripcion") ?: ""
                val ubicacion = document.getString("ubicacion") ?: ""

                val horario = document.getString("horario") ?: ""

                val urlImagen = document.getString("urlImagen") ?: ""
                val urlIcono = document.getString("urlIcono") ?: ""
                val urlMaps = document.getString("urlMaps") ?: ""
                val paginaWeb = document.getString("paginaWeb") ?: ""

                var elemento : Elemento
                if(tipo != "Evento"){
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
                }
                else{
                    val local = document.getString("local") ?: ""
                    val tickets = document.get("tickets") as? List<String> ?: emptyList()

                    elemento = Evento(
                        id,
                        nombre,
                        descripcion,
                        ubicacion,
                        local,
                        horario,
                        paginaWeb,
                        tickets,
                        tipo,
                        urlImagen,
                        urlIcono,
                        urlMaps
                    )
                }
                favoriteElementList.add(elemento)

            }
        }
        return favoriteElementList
    }

}