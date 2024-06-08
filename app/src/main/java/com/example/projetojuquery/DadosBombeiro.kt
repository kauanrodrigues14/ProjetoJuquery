package com.example.projetojuquery


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import bdConnect

class DadosBombeiro : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_bombeiro)


        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewBombeiros)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val bd = bdConnect(this)
        val bombeiros = bd.pegarDadosBombeiro().toMutableList()

        val adapter = BombeiroAdapter(this, bombeiros, bd)
        recyclerView.adapter = adapter
    }

}
