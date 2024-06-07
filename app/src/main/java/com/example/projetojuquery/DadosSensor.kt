package com.example.projetojuquery

import SensorAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bdConnect

class DadosSensor : AppCompatActivity() {

    private lateinit var sensorAdapter: SensorAdapter
    private lateinit var sensorList: MutableList<bdConnect.Sensor>
    private lateinit var dbHelper: bdConnect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_sensor)

        dbHelper = bdConnect(this)

        sensorList = dbHelper.obterSensores().toMutableList()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewSensor)
        recyclerView.layoutManager = LinearLayoutManager(this)
        sensorAdapter = SensorAdapter(this, sensorList, dbHelper)
        recyclerView.adapter = sensorAdapter
    }
}
