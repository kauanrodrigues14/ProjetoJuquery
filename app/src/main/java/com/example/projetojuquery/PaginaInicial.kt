package com.example.projetojuquery


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import bdConnect
import com.google.android.material.bottomnavigation.BottomNavigationView



class PaginaInicial : AppCompatActivity() {

    private lateinit var barraNavegacao: BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_inicial)

        val bdIncio = bdConnect(this)

        barraNavegacao = findViewById(R.id.navView)

        barraNavegacao.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                    R.id.Início -> {
                        substuirFragment(InicioFragment())
                        true
                    }
                R.id.sensor -> {
                    substuirFragment(SensorFragment())
                    true
                }
                R.id.Clima -> {
                    substuirFragment(ClimaFragment())
                    true
                }
                else -> false
            }
        }
        substuirFragment(InicioFragment())

    }
    private fun substuirFragment(fragmentActivity : FragmentActivity){
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragmentActivity).commit()

    }
}