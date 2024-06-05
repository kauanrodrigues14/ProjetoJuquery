package com.example.projetojuquery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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

        val txtIdSensor = view.findViewById<TextView>(R.id.txtIdSensor)
        val txtIdSensor1 = view.findViewById<TextView>(R.id.txtIdSensor1)
        val txtIdSensor2 = view.findViewById<TextView>(R.id.txtIdSensor2)
        val txtLatitude = view.findViewById<TextView>(R.id.txtLatitude)
        val txtLatitude1 = view.findViewById<TextView>(R.id.txtLatitude1)
        val txtLatitude2 = view.findViewById<TextView>(R.id.txtLatitude2)
        val txtLongitude = view.findViewById<TextView>(R.id.txtLongitude)
        val txtLongitude1 = view.findViewById<TextView>(R.id.txtLongitude1)
        val txtLongitude2 = view.findViewById<TextView>(R.id.txtLongitude2)

        val dadosSensor = bdSensor.obterDadosSensor()

        if(dadosSensor.size >= 3){
            txtIdSensor.text = dadosSensor[0].first.toString()
            txtIdSensor1.text = dadosSensor[1].first.toString()
            txtIdSensor2.text = dadosSensor[2].first.toString()

            //Latitude
            txtLatitude.text = dadosSensor[0].second.first
            txtLatitude1.text = dadosSensor[1].second.first
            txtLatitude2.text = dadosSensor[2].second.first

            //Longitude

            txtLongitude.text = dadosSensor[0].second.second
            txtLongitude1.text = dadosSensor[1].second.second
            txtLongitude2.text = dadosSensor[2].second.second


        }else{
            txtIdSensor.text = "Não há dados suficientes"
            txtIdSensor1.text = ""
            txtIdSensor2.text = ""
            txtLatitude.text = ""
            txtLatitude1.text = ""
            txtLatitude2.text = ""
            txtLongitude.text = ""
            txtLongitude1.text = ""
            txtLongitude2.text = ""
        }



    }



}
