package com.sdmdevelopers.asturspot.elemento_detalle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sdmdevelopers.asturspot.R

class EventoDetalle  : Fragment() {
    private val viewModel: ElementoDetalleViewModel by viewModels()

    private lateinit var tvDescripcion: TextView
    private lateinit var tvDireccion: TextView
    private lateinit var tvLocalNombre: TextView
    private lateinit var tvHorario: TextView
    private lateinit var tvEntradasInfo: TextView
    private lateinit var ivImagen: ImageView
    private lateinit var tvMasInfo: TextView

    private lateinit var cbFav: CheckBox
    private lateinit var fab : FloatingActionButton

    private val args: EventoDetalleArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_evento_detalles, container, false)
        val toolbarDetalle = view.findViewById<Toolbar>(R.id.toolbar)
        toolbarDetalle.title = args.elemento.nombre
        (activity as AppCompatActivity).setSupportActionBar(toolbarDetalle)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDescripcion= view.findViewById(R.id.tvDescripcionActividad)
        tvDireccion = view.findViewById(R.id.tvDireccionEvento)
        tvLocalNombre = view.findViewById(R.id.tvLocalNombre)
        tvHorario = view.findViewById(R.id.tvHorarioActividad)
        tvEntradasInfo = view.findViewById(R.id.tvEntradasInfo)
        ivImagen = view.findViewById(R.id.ivImagenActividad)
        tvMasInfo = view.findViewById(R.id.tvMasInfoEnlace)
        cbFav = view.findViewById(R.id.ckbFavorito)
        fab = view.findViewById(R.id.fab)


        val evento = args.elemento

        val toolbarDetalle = view.findViewById<Toolbar>(R.id.toolbar)
        toolbarDetalle.title = args.elemento.nombre
        (activity as AppCompatActivity).setSupportActionBar(toolbarDetalle)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarDetalle.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        tvDescripcion.text = evento.descripcion
        tvDireccion.text = evento.ubicacion
        tvLocalNombre.text = evento.local
        tvMasInfo.text = "Para más información visite la página "+ evento.paginaWeb

        tvHorario.text = evento.horario

        var links = ""
        evento.tickets.forEach {
            links += it+"\n"
        }
        tvEntradasInfo.text = links

        ivImagen.load(evento.urlImagen)

        // Verificar si el evento ya está en favoritos
        viewModel.checkIfFavorited(evento.id)

        // Observa el estado de favorito
        viewModel.isFavorited.observe(viewLifecycleOwner) { isFavorited ->
            cbFav.isChecked = isFavorited
        }

        // Observa los mensajes
        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }

        // Listener del CheckBox de favorito
        cbFav.setOnCheckedChangeListener { _, isChecked ->
            viewModel.canUseFavorites()
            if(!viewModel.canUseFavorites.value!!){
                cbFav.isChecked = false
            }
            else{
                if (isChecked){
                    viewModel.addToFavorites(evento)
                } else {
                    viewModel.removeFromFavorites(evento.id)
                }
                // Actualizar el estado manualmente para manejar cambios de configuración
                viewModel.setFavoriteState(isChecked)
            }
        }

        fab.setOnClickListener {
            val url = evento.urlMaps
            if(url != null){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            else{
                Toast.makeText(context, "Enlace no disponible", Toast.LENGTH_SHORT).show()
            }
        }

    }
}