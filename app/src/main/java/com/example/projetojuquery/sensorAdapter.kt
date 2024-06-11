
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projetojuquery.R
import com.example.projetojuquery.bdConnect

class SensorAdapter(
    private val context: Context,
    private val sensores: MutableList<bdConnect.Sensor>,
    private val bd: bdConnect
) : RecyclerView.Adapter<SensorAdapter.SensorViewHolder>() {

    class SensorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.txtIdsensor)
        val latitude: TextView = itemView.findViewById(R.id.txtLatitude)
        val longitude: TextView = itemView.findViewById(R.id.txtLongitude)
        val btnEditar: ImageButton = itemView.findViewById(R.id.btnEditar)
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

        holder.btnEditar.setOnClickListener {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_sensor, null)
            val edtLatitude = dialogView.findViewById<EditText>(R.id.edtLatitudeAtualizar)
            val edtLongitude = dialogView.findViewById<EditText>(R.id.edtLongitudeAtualizar)
            val btnAtualizar = dialogView.findViewById<Button>(R.id.btnAtualizar)

             edtLatitude.setText( currentSensor.latitude)
             edtLongitude.setText( currentSensor.longitude)

            val alertDialog = android.app.AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setView(dialogView)
                .create()

            btnAtualizar.setOnClickListener {
                val novaLatitude = edtLatitude.text.toString()
                val novaLongitude = edtLongitude.text.toString()

                if (novaLatitude.isNotEmpty() && novaLongitude.isNotEmpty()) {
                    bd.atualizarSensor(currentSensor.id, novaLatitude, novaLongitude)
                    // Atualizar a lista l
                    currentSensor.latitude = novaLatitude
                    currentSensor.longitude = novaLongitude
                    notifyItemChanged(position)
                }
                alertDialog.dismiss()
            }

            alertDialog.show()
        }

        holder.btnDeletar.setOnClickListener {
            val dialog = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null)
            val btnCancelar = dialog.findViewById<ImageButton>(R.id.btnCancelar)
            val btnConfirmar = dialog.findViewById<ImageButton>(R.id.btnConfirmar)

            val dialogAlerta = android.app.AlertDialog.Builder(context, R.style.CustomAlertDialog)
                .setView(dialog)
                .create()

            btnConfirmar.setOnClickListener {
                bd.deletarSensor(currentSensor.id)
                sensores.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount)

                dialogAlerta.dismiss()
            }

            btnCancelar.setOnClickListener {
                dialogAlerta.dismiss()
            }
            dialogAlerta.show()
            dialogAlerta.window?.setLayout(800, 600)


        }
    }



    override fun getItemCount() = sensores.size
}
