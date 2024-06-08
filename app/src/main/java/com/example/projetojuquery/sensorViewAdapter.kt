package com.example.projetojuquery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SensorViewAdapter(private val context: Context, private val sensores: List<bdConnect.Sensor>) :
    RecyclerView.Adapter<SensorViewAdapter.SensorViewHolder>() {

    class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idTextView: TextView = itemView.findViewById(R.id.txtId)
        val latitudeTextView: TextView = itemView.findViewById(R.id.txtLatitude)
        val longitudeTextView: TextView = itemView.findViewById(R.id.txtLongitude)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_sensor_view, parent, false)
        return SensorViewHolder(view)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val sensor = sensores[position]
        holder.idTextView.text = sensor.id.toString()
        holder.latitudeTextView.text = sensor.latitude
        holder.longitudeTextView.text = sensor.longitude
    }

    override fun getItemCount() = sensores.size
}
