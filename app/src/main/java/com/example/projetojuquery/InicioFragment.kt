package com.example.projetojuquery

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class InicioFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inicio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val contexto = requireContext()
        val edtSenha = view.findViewById<EditText>(R.id.edtSenhaAdm).toString()
        val edtLogin = view.findViewById<EditText>(R.id.edtLoginAdm).toString()
        val btnLogin = view.findViewById<Button>(R.id.btnLoginAdm)

        btnLogin.setOnClickListener {
            if (edtLogin == "admin" || edtSenha == "0000") {

                Toast.makeText(contexto, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                val intent = Intent(contexto, PaginaInicial::class.java)
                startActivity(intent)

            }
            else if(edtLogin.isEmpty() || edtSenha.isEmpty()){
                Toast.makeText(contexto, "Login e senha não podem estar vazios.", Toast.LENGTH_SHORT).show()
        }else{
                Toast.makeText(contexto, "Login ou Senha Inválidos.", Toast.LENGTH_SHORT).show()
            }
        }


    }
}
