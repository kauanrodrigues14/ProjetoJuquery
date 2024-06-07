
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.projetojuquery.R

class SensorAdapter(
    private val context: Context,
    private val sensores: MutableList<bdConnect.Sensor>,
    private val bd: bdConnect
) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.txtIdsensor)
        val latitude: TextView = itemView.findViewById(R.id.txtLatitude)
        val longitude: TextView = itemView.findViewById(R.id.txtLongitude)
        val btnAtualizar: ImageButton = itemView.findViewById(R.id.btnEditar)
        val btnDeletar: ImageButton = itemView.findViewById(R.id.btnDeletar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SensorViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_sensor, parent, false)
        return SensorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SensorViewHolder, position: Int) {
        val currentSensor = sensores[position]
        holder.id.text = currentSensor.id.toString()
        holder.latitude.text = currentSensor.latitude
        holder.longitude.text = currentSensor.longitude

        holder.btnAtualizar.setOnClickListener {
            // Abrir diálogo para atualizar dados
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sensor, null)
            val edtLatitude = dialogView.findViewById<EditText>(R.id.edtLatitudeAtualizar)
            val edtLongitude = dialogView.findViewById<EditText>(R.id.edtLongitudeAtualizar)

            edtLatitude.setText(currentSensor.latitude)
            edtLongitude.setText(currentSensor.longitude)

            AlertDialog.Builder(context)
                .setView(dialogView)
                .setTitle("Atualizar Sensor")
                .setPositiveButton("Atualizar") { dialog, _ ->
                    val novaLatitude = edtLatitude.text.toString()
                    val novaLongitude = edtLongitude.text.toString()

                    if (novaLatitude.isNotEmpty() && novaLongitude.isNotEmpty()) {
                        // Atualizar o sensor no banco de dados
                        bd.atualizarSensor(currentSensor.id, novaLatitude, novaLongitude)
                        // Atualizar a lista localmente
                        currentSensor.latitude = novaLatitude
                        currentSensor.longitude = novaLongitude
                        notifyItemChanged(position)
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        holder.btnDeletar.setOnClickListener {
            bd.deletarSensor(currentSensor.id)
            sensores.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount() = sensores.size
}
