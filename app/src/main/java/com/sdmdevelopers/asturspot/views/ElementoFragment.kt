package com.sdmdevelopers.asturspot.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sdmdevelopers.asturspot.R
import com.sdmdevelopers.asturspot.elemento.ElementoActividad
import com.sdmdevelopers.asturspot.remote.DataMapper.convertElementListToDomain
import com.sdmdevelopers.asturspot.remote.data.elementos.ElementoActividadListData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class ElementoFragment : Fragment() {
    private lateinit var txElementos : TextView

    protected var listaElementos : MutableList<ElementoActividad> = mutableListOf()


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
            val result = cargarElementos()

            val elementList = result.body() //conjunto de peliculas
            if(result.isSuccessful) {
                listaElementos = convertElementListToDomain(elementList?.data ?: emptyList())
                anadirFiltros()

            } else {
                //tratamos el error
                Log.e("Error en peticion retrofit", "Tipo de error")
            }

            withContext(Dispatchers.Main){
                mostrarElementos()
                crearFiltros()
                inicializarComponentes()
                txElementos.text = getTipoElementos()
            }
        }
    }

    private fun inicializarComponentes(){
        txElementos = requireView().findViewById(R.id.txElementos)

    }

    protected abstract fun mostrarElementos()

    protected abstract suspend fun cargarElementos() : Response<ElementoActividadListData>

    protected abstract fun getTipoElementos() : String

    protected open fun anadirFiltros(){ }

    protected open fun crearFiltros(){}

}