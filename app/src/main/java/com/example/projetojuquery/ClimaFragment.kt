package com.example.projetojuquery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClimaFragment : Fragment() {

    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val API_KEY = "c224f8efb78a9df1bc94e936cf9069a7"

    // Inicializando o Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Crie a interface para definir os pontos finais da API
    private interface ClimaApi {
        @GET("weather")
        suspend fun getClima(
            @Query("q") localizacao: String,
            @Query("appid") chaveApi: String,
            @Query("units") units: String = "metric"
        ): Response<ClimaResultado>
    }

    // Classe de modelo para representar a resposta JSON
    data class ClimaResultado(
        val main: Principal,
        val weather: List<Clima>,
        val wind: Vento,
        val pop: Double,
        val dt: Long
    )

    data class Principal(
        val temp: Double,
        val humidity: Int,
        val temp_min: Double,
        val temp_max: Double
    )

    data class Clima(
        val description: String
    )

    data class Vento(
        val speed: Double
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_clima, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtTemp = view.findViewById<TextView>(R.id.txtTemp)
        val txtUmidade = view.findViewById<TextView>(R.id.txtUmidade)
        val txtVento = view.findViewById<TextView>(R.id.txtVento)
        val txtChuva = view.findViewById<TextView>(R.id.txtChuva)
        val txtData = view.findViewById<TextView>(R.id.txtData)
        val txtTempmax = view.findViewById<TextView>(R.id.txtTempmax)
        val txtTempmin = view.findViewById<TextView>(R.id.txtTempmin)



        // chamada API
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val resposta = retrofit.create(ClimaApi::class.java)
                    .getClima("Franco da Rocha", API_KEY)

                if (resposta.isSuccessful) {
                    val dadosClima = resposta.body()
                    val temperatura = dadosClima?.main?.temp ?: 0
                    val tempmax = dadosClima?.main?.temp_max ?:0
                    val tempmin = dadosClima?.main?.temp_min ?:0
                    val umidade = dadosClima?.main?.humidity ?: 0
                    val vento = dadosClima?.wind?.speed ?: 0
                    val precipitationProbability = dadosClima?.pop ?: 0.0
                    val chanceChuva = (precipitationProbability * 100).toInt()
                    val timestamp = dadosClima?.dt ?: 0L
                    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val date = Date(timestamp * 1000)
                    val dataFormatada = sdf.format(date)

                    launch(Dispatchers.Main) {
                        txtTemp.text = "${temperatura.toInt()}°C"
                        txtTempmax.text = "${tempmax.toInt()} °"
                        txtTempmin.text = "${tempmin.toInt()} °"
                        txtUmidade.text = "${umidade}%"
                        txtVento.text = "${vento.toInt()} Km/h"
                        txtChuva.text = "${chanceChuva}%"
                        txtData.text = dataFormatada
                    }

                } else {
                    Log.i("Clima", "Falha na API: ${resposta.errorBody()?.string()}")
                        // Lod de Erro
                }
            } catch (e: Exception) {
                Log.e("Clima", "Exceção: ${e.message}")
                // Log de exception
            }
        }
    }
}
