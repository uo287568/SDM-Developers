package com.sdmdevelopers.asturspot.views

import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sdmdevelopers.asturspot.R
import com.sdmdevelopers.asturspot.elemento.ElementoActividad
import com.sdmdevelopers.asturspot.elemento.ElementoAdapter
import com.sdmdevelopers.asturspot.remote.RetrofitInstance
import com.sdmdevelopers.asturspot.remote.data.elementos.ElementoActividadListData
import retrofit2.Response

class GastronomiaFragment : ElementoFragmentFiltros() {
    private lateinit var recyclerView : RecyclerView

    override fun mostrarElementos(){
        recyclerView = requireView().findViewById(R.id.recyclerElemento)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ElementoAdapter(listaFiltrada) { elemento -> val destino =
            GastronomiaFragmentDirections.actionGastronomiaFragmentToElementoDetalle(elemento!! as ElementoActividad)
            findNavController().navigate(destino)
        }
    }

    override suspend fun cargarElementos(): Response<ElementoActividadListData> {
        return RetrofitInstance.localBusinessApi
            .getListElements("Restaurantes, Asturias", 40, "es","es" )
    }

    override fun getTipoElementos(): String {
        return "Gastronomía"
    }
}