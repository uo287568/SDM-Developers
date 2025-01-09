package com.sdmdevelopers.asturspot.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdmdevelopers.asturspot.R
import com.sdmdevelopers.asturspot.elemento.ElementoAdapter
import com.sdmdevelopers.asturspot.elemento.Evento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.sdmdevelopers.asturspot.remote.DataMapper.convertEventListToDomain
import com.sdmdevelopers.asturspot.remote.RetrofitInstance

class EventosFragment : Fragment() {
    private lateinit var recyclerView : RecyclerView

    private lateinit var txElementos : TextView
    private var listaElementos : MutableList<Evento> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inicializaRecyclerElementos()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elemento, container, false)
    }

    private fun inicializaRecyclerElementos() {
        lifecycleScope.launch(Dispatchers.IO) {
            listaElementos.clear()
            for (i in 0..1) {
                val result = RetrofitInstance.serpApi
                    .getListEventos(
                        "039ea72ab103865dfa987acad24de1f3ebc6d1ee66f640242be1d965431a147c",
                        "google_events",
                        "Asturias",
                        "es",
                        "es",
                        "google.es",
                        "Spain",
                        i*10
                    )
                val elementList = result.body() //conjunto de peliculas
                if (result.isSuccessful) {
                    listaElementos += convertEventListToDomain(elementList?.data ?: emptyList())
                } else {
                    //tratamos el error
                    Log.e("Error en peticion retrofit", "Tipo de error")
                }
            }

            withContext(Dispatchers.Main){
                mostrarElementos()
                txElementos = requireView().findViewById(R.id.txElementos)
                txElementos.text = "Eventos"
            }
        }
    }

    private fun mostrarElementos(){
        recyclerView = requireView().findViewById(R.id.recyclerElemento)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ElementoAdapter(listaElementos) { elemento -> val destino =
            EventosFragmentDirections.actionEventosFragmentToEventoDetalle(
                elemento!! as Evento
            )
            findNavController().navigate(destino)
        }
    }
}