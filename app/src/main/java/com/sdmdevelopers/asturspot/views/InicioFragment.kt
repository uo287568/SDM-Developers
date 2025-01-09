package com.sdmdevelopers.asturspot.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.sdmdevelopers.asturspot.R


class InicioFragment : Fragment() {
    private lateinit var btMapa: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btMapa = requireView().findViewById(R.id.btMapa)
        vincularBtMapa()
    }

    private fun vincularBtMapa(){
        btMapa.setOnClickListener{
            showMap()
        }
    }

    private fun showMap(){
        val destino = InicioFragmentDirections.actionInicioFragmentToMapsFragment()
        findNavController().navigate(destino)
    }
}