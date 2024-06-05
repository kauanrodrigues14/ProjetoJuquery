package com.example.projetojuquery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CRUD : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_crud)

            val btnEditar = findViewById<ImageButton>(R.id.btnEditar)


        btnEditar.setOnClickListener{

            dialogAtualizacao(this)
        }


        }
    fun dialogAtualizacao(context: Context) {
        // Criar o Dialog
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog)

        val edtAttNome = dialog.findViewById<EditText>(R.id.edtAttNome)
        val edtAttCargo = dialog.findViewById<EditText>(R.id.edtAttCargo)
        val botaoAtualizar = dialog.findViewById<Button>(R.id.btnAtualizar)


        botaoAtualizar.setOnClickListener {

            val nome = edtAttNome.text.toString()
            val cargo = edtAttCargo.text.toString()

            // Fechar o Dialog após a atualização
            dialog.dismiss()
        }

        // Configurar o layout do Dialog
        dialog.window?.setBackgroundDrawableResource(R.drawable.fundo_dialog)
        dialog.window?.setLayout(900,900)
        dialog.show()
    }
}
