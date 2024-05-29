package com.example.projetojuquery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import bdConnect

class SensorFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sensor, container, false)






    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contexto = requireContext()
        val bdSensor = bdConnect(contexto)
        val btnCadastrar = view.findViewById<Button>(R.id.btnCadastrarSensor)

        btnCadastrar.setOnClickListener {
            val latitude = view.findViewById<EditText>(R.id.edtLat).text.toString()
            val longitude = view.findViewById<EditText>(R.id.edtLong).text.toString()

            val cadSensorInserido = bdSensor.cadastrarSensor(latitude, longitude)

            if (cadSensorInserido != 1L) {
                Toast.makeText(contexto, "Cadastro bem sucessido!!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(
                    contexto,
                    "Cadastro incompleto, preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
        bdSensor.exibirDadosSensor()
    }




}
