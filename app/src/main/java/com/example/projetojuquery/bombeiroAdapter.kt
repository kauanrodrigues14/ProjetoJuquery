package com.example.projetojuquery


import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class BombeiroAdapter(
    private val context: Context,
    private val bombeiros: MutableList<bdConnect.Bombeiro>,
    private val bd: bdConnect
) : RecyclerView.Adapter<BombeiroAdapter.BombeiroViewHolder>() {


    class BombeiroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nome: TextView = itemView.findViewById(R.id.txtNome)
        val cpf: TextView = itemView.findViewById(R.id.txtCPF)
        val login: TextView = itemView.findViewById(R.id.txtLogin)
        val cargo: TextView = itemView.findViewById(R.id.txtCargo)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
        val btnDeletar: ImageButton = itemView.findViewById(R.id.btnDeletar)


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
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog, null)
            val edtNome = dialogView.findViewById<EditText>(R.id.edtAttNome)
            val btnAtualizar = dialogView.findViewById<Button>(R.id.btnAtualizar)
            val edtCargo = dialogView.findViewById<EditText>(R.id.edtAttCargo)

            edtNome.setText(currentBombeiro.nome)
            edtCargo.setText(currentBombeiro.cargo)

            val alertDialog = AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setView(dialogView)
                .create()



            btnAtualizar.setOnClickListener {
                val novoNome = edtNome.text.toString()
                val novoCargo = edtCargo.text.toString()

                if (novoNome.isNotEmpty() && novoCargo.isNotEmpty()) {
                    bd.atualizarBombeiro(currentBombeiro.id, novoNome, novoCargo)
                    currentBombeiro.nome = novoNome
                    currentBombeiro.cargo = novoCargo
                    notifyItemChanged(position)
                }
                alertDialog.dismiss()
            }
            alertDialog.show()
            alertDialog.window?.setLayout(900, 900)



        }
        holder.btnDeletar.setOnClickListener {
            val dialog = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null)
            val btnCancelar = dialog.findViewById<ImageButton>(R.id.btnCancelar)
            val btnConfirmar = dialog.findViewById<ImageButton>(R.id.btnConfirmar)
            val dialogAlerta = AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setView(dialog)
                .create()

            btnConfirmar.setOnClickListener{
                bd.deletarBombeiro(currentBombeiro.id)
                bombeiros.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)

                dialogAlerta.dismiss()
            }

            btnCancelar.setOnClickListener{
                dialogAlerta.dismiss()
            }
            dialogAlerta.show()
            dialogAlerta.window?.setLayout(800, 600)





        }
    }

    override fun getItemCount() = bombeiros.size
}
