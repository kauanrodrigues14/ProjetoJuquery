package com.example.projetojuquery


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import bdConnect
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class SensorFragment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_sensor)

        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)



        val bd = bdConnect(this)
        btnCadastrar.setOnClickListener{

            val edtLong = findViewById<EditText>(R.id.edtLong).toString()
            val edtLat = findViewById<EditText>(R.id.edtLat).toString()

            val bdCadSens = bd.cadastrarSensor(edtLong, edtLat)

            if(bdCadSens != -1L){
                Toast.makeText(this, "Sensor Cadastrado com sucesso!! ", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Falha no Cadastro ", Toast.LENGTH_SHORT).show()
            }


        }
    }




}