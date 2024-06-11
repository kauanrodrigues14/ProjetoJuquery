package com.example.projetojuquery



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AlertaViewAdapter( private val alertas: List<bdConnect.Alerta>)
    : RecyclerView.Adapter<AlertaViewAdapter.AlertaViewHolder>(){

    class AlertaViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val datahora = itemView.findViewById<TextView>(R.id.txtDataHora)
        val fogo = itemView.findViewById<TextView>(R.id.txtFogo)
        val temperatura = itemView.findViewById<TextView>(R.id.txtTemperatura)
        val umidade = itemView.findViewById<TextView>(R.id.txtUmidade)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alerta_view,
            parent, false)
        return AlertaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertaViewHolder, position: Int) {
        val alerta = alertas[position]
        holder.datahora.text = alerta.datahora
        holder.fogo.text = alerta.fogo.toString()
        holder.temperatura.text = alerta.temperatura.toString()
        holder.umidade.text = alerta.umidade.toString()
    }

    override fun getItemCount() = alertas.size
}