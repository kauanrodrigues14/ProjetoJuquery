package com.example.projetojuquery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SensorViewAdapter(private val sensorList: List<bdConnect.Sensor>) : RecyclerView.Adapter<SensorViewAdapter.SensorViewHolder>() {

    class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtIdView: TextView = itemView.findViewById(R.id.txtIdView)
        val txtLatitudeView: TextView = itemView.findViewById(R.id.txtLatitudeView)
        val txtLongitudeView: TextView = itemView.findViewById(R.id.txtLongitudeView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.sensor_item_view, parent, false)
        return SensorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val currentSensor = sensorList[position]
        holder.txtIdView.text = currentSensor.id.toString()
        holder.txtLatitudeView.text = currentSensor.latitude
        holder.txtLongitudeView.text = currentSensor.longitude
    }

    override fun getItemCount() = sensorList.size
}
