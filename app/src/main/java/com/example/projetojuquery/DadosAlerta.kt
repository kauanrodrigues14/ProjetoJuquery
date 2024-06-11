package com.example.projetojuquery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DadosAlerta : AppCompatActivity() {

    private var bd = bdConnect(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dados_alerta)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewAlertas)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val alertas = bd.obterAlertas().toMutableList()


        val adapter = AlertaAdapter(alertas)
        recyclerView.adapter = adapter


        }
    }
