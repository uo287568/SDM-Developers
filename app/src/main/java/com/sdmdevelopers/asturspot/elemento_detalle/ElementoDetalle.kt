package com.sdmdevelopers.asturspot.elemento_detalle

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RatingBar
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

class ElementoDetalle : Fragment() {
    private val viewModel: ElementoDetalleViewModel by viewModels()

    private lateinit var tvDescripcion: TextView
    private lateinit var tvTipo: TextView
    private lateinit var tvUbicacion: TextView
    private lateinit var tvHorario: TextView
    private lateinit var tvHorarioEstado: TextView
    private lateinit var tvMasInfo: TextView
    private lateinit var ivImagen: ImageView
    private lateinit var cbFav: CheckBox
    private lateinit var fab : FloatingActionButton
    private lateinit var ratingBar : RatingBar
    private lateinit var tvRating : TextView

    private val args: ElementoDetalleArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_elemento_detalles, container, false)
        val toolbarDetalle = view.findViewById<Toolbar>(R.id.toolbar)
        toolbarDetalle.title = args.elemento.nombre
        (activity as AppCompatActivity).setSupportActionBar(toolbarDetalle)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvDescripcion= view.findViewById(R.id.tvDescripcionActividad)
        tvTipo = view.findViewById(R.id.tvTipoActividad)
        tvUbicacion = view.findViewById(R.id.tvUbicacionActividad)
        tvHorario = view.findViewById(R.id.tvHorarioActividad)
        tvHorarioEstado = view.findViewById(R.id.tvHorarioEstado)
        tvMasInfo = view.findViewById(R.id.tvMasInfoEnlace)
        ivImagen = view.findViewById(R.id.ivImagenActividad)
        cbFav = view.findViewById(R.id.ckbFavorito)
        fab = view.findViewById(R.id.fab)
        ratingBar = view.findViewById(R.id.ratingBar)
        tvRating = view.findViewById(R.id.tvValoracionesValor)

        val elemento = args.elemento

        val toolbarDetalle = view.findViewById<Toolbar>(R.id.toolbar)
        toolbarDetalle.title = args.elemento.nombre
        (activity as AppCompatActivity).setSupportActionBar(toolbarDetalle)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbarDetalle.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        tvDescripcion.text = elemento.descripcion
        tvTipo.text = elemento.tipo
        tvUbicacion.text = elemento.ubicacion
        tvHorario.text = elemento.horario
        tvHorarioEstado.text = elemento.estado
        tvMasInfo.text = "Para más información llame al " + elemento.numTelefono +
                " o consulte la página " + elemento.paginaWeb
        ivImagen.load(elemento.urlImagen)
        ratingBar.rating = elemento.rating
        tvRating.text = elemento.rating.toString()

        // Verificar si el evento ya está en favoritos
        viewModel.checkIfFavorited(elemento.id)

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
                    viewModel.addToFavorites(elemento)
                } else {
                    viewModel.removeFromFavorites(elemento.id)
                }
                // Actualizar el estado manualmente para manejar cambios de configuración
                viewModel.setFavoriteState(isChecked)
            }
        }

        fab.setOnClickListener {
            val url = elemento.urlMaps
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