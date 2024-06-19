package com.example.projetojuquery

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView

class PaginaInicial : AppCompatActivity() {

    private lateinit var barraNavegacao: BottomNavigationView
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagina_inicial)

        viewPager = findViewById(R.id.viewPager)
        barraNavegacao = findViewById(R.id.navView)

        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                barraNavegacao.menu.getItem(position).isChecked = true
            }
        })

        barraNavegacao.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.Início -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.sensor -> {
                    viewPager.currentItem = 1
                    true
                }
                R.id.Clima -> {
                    viewPager.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }
}
