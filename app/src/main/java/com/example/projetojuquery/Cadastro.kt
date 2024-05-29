package com.example.projetojuquery


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import bdConnect

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)


        val btnCadastrar = findViewById<Button>(R.id.btnCadastrar)


        btnCadastrar.setOnClickListener {
            val edtNome = findViewById<EditText>(R.id.edtNome).text.toString()
            val edtCPF = findViewById<EditText>(R.id.edtCPF).text.toString()
            val edtCargo = findViewById<EditText>(R.id.edtCargo).text.toString()
            val edtLogin = findViewById<EditText>(R.id.edtLoginCad).text.toString()
            val edtSenha = findViewById<EditText>(R.id.edtSenhaCad).text.toString()


            if (edtNome.isEmpty() || edtCPF.isEmpty() || edtCargo.isEmpty() || edtLogin.isEmpty() || edtSenha.isEmpty()) {

                Toast.makeText(
                    this,
                    "Cadastro incompleto, preencha todos os campos",
                    Toast.LENGTH_SHORT
                ).show()
            } else {


                val bdCad = bdConnect(this)
                val proxLinha =
                    bdCad.cadastroBombeiro(edtNome, edtCPF, edtLogin, edtCargo, edtSenha)


                if (proxLinha != -1L) {
                    Toast.makeText(
                        this,
                        "Bombeiro Cadastrado com sucesso!!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Falha no Cadastro ", Toast.LENGTH_SHORT).show()
                }


            }
        }
    }
}
