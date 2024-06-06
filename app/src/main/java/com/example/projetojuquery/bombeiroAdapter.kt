package com.example.projetojuquery


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bdConnect


class BombeiroAdapter(private val bd:bdConnect ,private val context: Context, private val bombeiros: List<bdConnect.Bombeiro>) : RecyclerView.Adapter<BombeiroAdapter.BombeiroViewHolder>() {


    class BombeiroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.txtNome)
        val cpf: TextView = itemView.findViewById(R.id.txtCPF)
        val login: TextView = itemView.findViewById(R.id.txtLogin)
        val cargo: TextView = itemView.findViewById(R.id.txtCargo)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BombeiroViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_bombeiro, parent, false)
        return BombeiroViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BombeiroViewHolder, position: Int) {
        val currentBombeiro = bombeiros[position]
        holder.nome.text = currentBombeiro.nome
        holder.cpf.text = "CPF: " + currentBombeiro.cpf
        holder.login.text = "Login: " + currentBombeiro.login
        holder.cargo.text = "Cargo: " + currentBombeiro.cargo

        holder.btnEditar.setOnClickListener {
            // Abrir diálogo para atualizar dados

            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
            val edtNome = dialogView.findViewById<EditText>(R.id.edtAttNome)
            val edtCargo = dialogView.findViewById<EditText>(R.id.edtAttCargo)

            edtNome.setText(currentBombeiro.nome)
            edtCargo.setText(currentBombeiro.cargo)

            AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Atualizar Bombeiro")
                .setPositiveButton("Atualizar") { dialog, _ ->
                    val novoNome = edtNome.text.toString()
                    val novoCargo = edtCargo.text.toString()

                    if (novoNome.isNotEmpty() && novoCargo.isNotEmpty()) {
                        bd.atualizarBombeiro(currentBombeiro.id, novoNome, novoCargo)
                        currentBombeiro.nome = novoNome
                        currentBombeiro.cargo = novoCargo
                        notifyItemChanged(position)
                    }
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    override fun getItemCount() = bombeiros.size
}
