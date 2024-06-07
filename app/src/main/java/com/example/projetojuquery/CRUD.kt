package com.example.projetojuquery

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import bdConnect

class CRUD : AppCompatActivity() {

    private lateinit var bd: bdConnect

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud)

        bd = bdConnect(this)

        val btnEditar = findViewById<ImageButton>(R.id.btnEditar)
        btnEditar.setOnClickListener {
            dialogAtualizacao(this)
        }
    }

    fun dialogAtualizacao(context: Context) {
        // Criar o Dialog
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.dialog)

        val edtAttNome = dialog.findViewById<EditText>(R.id.edtAttNome)
        val edtAttCargo = dialog.findViewById<EditText>(R.id.edtAttCargo)




        // Configurar o layout do Dialog
        dialog.window?.setBackgroundDrawableResource(R.drawable.fundo_dialog)
        dialog.window?.setLayout(900, 900)
        dialog.show()
    }

    private fun mostrarDialogoSucesso() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Sucesso")
        builder.setMessage("Bombeiro atualizado com sucesso.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun mostrarDialogoErro() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Erro")
        builder.setMessage("Não foi possível atualizar o bombeiro.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }
}




