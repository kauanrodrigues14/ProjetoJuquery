package com.example.projetojuquery

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class AdmInicial : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_adm_inicial)


        val btnBombeiros = findViewById<Button>(R.id.btnBombeiros)
        val btnSensores = findViewById<Button>(R.id.btnSensores)


        btnBombeiros.setOnClickListener {
            val intent = Intent(this, DadosBombeiro::class.java)
            startActivity(intent)
        }
        btnSensores.setOnClickListener {
            val intent = Intent(this, DadosSensor::class.java)
            startActivity(intent)
        }
    }
}

