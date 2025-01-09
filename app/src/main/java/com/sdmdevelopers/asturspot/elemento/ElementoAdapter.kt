package com.sdmdevelopers.asturspot.elemento

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sdmdevelopers.asturspot.R

class ElementoAdapter(
    private var listaElementos: List<Elemento>,
    private val onClickListener: (Elemento?) -> Unit
) : RecyclerView.Adapter<ElementoAdapter.ViewHolder>() {

    fun updateList(newList: List<Elemento>) {
        this.listaElementos = newList
        notifyDataSetChanged() // Notificamos al RecyclerView que los datos han cambiado para que se vuelva a "pintar" el recyclerview
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val layoutElemento = R.layout.elemento_item
        val view = LayoutInflater.from(viewGroup.context).inflate(layoutElemento, viewGroup, false)
        return ViewHolder(view, onClickListener)
    }

    override fun getItemCount(): Int {
        return listaElementos.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listaElementos[position])
    }

    class ViewHolder(
        view: View,
        onClickListener: (Elemento?) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        private var elementoActual: Elemento? = null
        private val tvNombre: TextView = view.findViewById(R.id.tvNombre)
        private val tvTipo: TextView = view.findViewById(R.id.tvTipo)
        private val ivElemento: ImageView = view.findViewById(R.id.ivElemento)

        init {
            view.setOnClickListener { onClickListener(elementoActual) }
        }

        fun bind(elemento: Elemento) {
            elementoActual = elemento
            tvNombre.text = elemento.nombre
            tvTipo.text = elemento.tipo
            ivElemento.load(elemento.urlIcono) // Usamos Coil para cargar la imagen
        }
    }
}
