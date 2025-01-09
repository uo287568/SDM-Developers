package com.sdmdevelopers.asturspot.views

import android.view.View
import com.google.android.material.chip.ChipGroup
import com.sdmdevelopers.asturspot.elemento.ElementoActividad
import com.google.android.material.chip.Chip
import com.sdmdevelopers.asturspot.R

abstract class ElementoFragmentFiltros : ElementoFragment() {
    private lateinit var cgFiltros : ChipGroup

    protected var listaFiltrada : MutableList<ElementoActividad> = mutableListOf()
    protected var listaFiltros: MutableList<String> = mutableListOf()


    override fun crearFiltros(){
        cgFiltros = requireView().findViewById(R.id.cgFiltros)

        this.listaElementos.forEach{
            if(!listaFiltros.contains(it.tipo)) {
                val chip =layoutInflater.inflate(R.layout.chip_layout, cgFiltros, false) as Chip

                chip.id = listaFiltros.size
                chip.text = it.tipo
                cgFiltros.addView(chip)
                chip.setOnClickListener(View.OnClickListener {
                    filtrarElementos()
                    mostrarElementos()
                })
                listaFiltros+=it.tipo
            }
        }
    }

    private fun filtrarElementos(){
        var listaFiltrosAplicados = emptyList<String>()

        //obtenemos los filtros aplicados
        cgFiltros.getCheckedChipIds().forEach{
            listaFiltrosAplicados += this.listaFiltros.get(it)
        }

        //si hay filtros aplicados añadimos los elementos de esos tipos
        listaFiltrada = mutableListOf()
        this.listaElementos.forEach{
            if(listaFiltrosAplicados.contains(it.tipo)){
                listaFiltrada+=it
            }
        }

        //si no hay filtros aplicados mostramos todos los elementos
        if(listaFiltrosAplicados.size == 0){
            listaFiltrada = listaElementos

        }
    }

    override fun anadirFiltros(){
        listaFiltrada = listaElementos
        listaFiltros.clear()
    }

}