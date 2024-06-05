package com.example.projetojuquery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment


class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contexto = requireContext()
        val edtSenhaAdm = view.findViewById<EditText>(R.id.edtSenhaAdm)
        val edtLoginAdm = view.findViewById<EditText>(R.id.edtLoginAdm)
        val btnLoginAdm = view.findViewById<Button>(R.id.btnLoginAdm)

        btnLoginAdm.setOnClickListener {
            val login = edtLoginAdm.text.toString()
            val senha = edtSenhaAdm.text.toString()

            if (login == "admin" && senha == "adm") {
                Toast.makeText(contexto, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                val intent = Intent(contexto, CRUD::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(contexto, "Credenciais inválidas.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    }

