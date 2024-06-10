package com.example.projetojuquery


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.Style


class SensorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor, container, false)
    }


    private lateinit var recyclerView: RecyclerView
    private lateinit var sensorViewAdapter: SensorViewAdapter
    private lateinit var sensorList: List<bdConnect.Sensor>
    private lateinit var mapView: MapView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val contexto = requireContext()
        val bdSensor = bdConnect(contexto)
        val btnCadastrar = view.findViewById<Button>(R.id.btnCadastrarSensor)


        recyclerView = view.findViewById(R.id.recyclerViewSensores)
        recyclerView.layoutManager = LinearLayoutManager(context)

        val bdHelper = bdConnect(requireContext())
        sensorList = bdHelper.obterSensores()

        sensorViewAdapter = SensorViewAdapter(sensorList)
        recyclerView.adapter = sensorViewAdapter

        mapView = view.findViewById(R.id.mapView)
        mapView.mapboxMap.loadStyle(Style.OUTDOORS) {
            // Definir a localização inicial e o nível de zoom
            val initialPosition = CameraOptions.Builder()
                .center(Point.fromLngLat( -46.692587, -23.350546)) // Coordenadas o
                .zoom(12.0)
                .build()
            mapView.mapboxMap.setCamera(initialPosition)
        }




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



}

}
