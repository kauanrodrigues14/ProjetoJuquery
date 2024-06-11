package com.example.projetojuquery

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    // Interface para definir os pontos finais da API
    private interface ClimaApi {
        @GET("weather")
        suspend fun getClima(
            @Query("q") localizacao: String,
            @Query("appid") chaveApi: String,
            @Query("units") units: String = "metric"
        ): Response<ClimaResultado>

        @GET("forecast")
        suspend fun getPrevisao(
            @Query("q") localizacao: String,
            @Query("appid") chaveApi: String,
            @Query("units") units: String = "metric"
        ): Response<PrevisaoResultado>
    }

    // Classe de modelo para representar a resposta JSON do clima atual
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

    // Classe de modelo para representar a resposta JSON da previsão
    data class PrevisaoResultado(
        val list: List<PrevisaoItem>
    )

    data class PrevisaoItem(
        val main: Principal,
        val weather: List<Clima>,
        val wind: Vento,
        val pop: Double,
        val dt: Long
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_clima, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val txtTemp = view.findViewById<TextView>(R.id.txtTemp)
        val txtUmidade = view.findViewById<TextView>(R.id.txtUmidade)
        val txtVento = view.findViewById<TextView>(R.id.txtVento)
        val txtData = view.findViewById<TextView>(R.id.txtData)
        val txtTempmax = view.findViewById<TextView>(R.id.txtTempmax)
        val txtTempmin = view.findViewById<TextView>(R.id.txtTempmin)

        // Previsão dos próximos dias
        val txtTempmin1 = view.findViewById<TextView>(R.id.txtTempmin1)
        val txtTempmax1 = view.findViewById<TextView>(R.id.txtTempmax1)
        val txtData1 = view.findViewById<TextView>(R.id.txtData1)

        val txtTempmin2 = view.findViewById<TextView>(R.id.txtTempmin2)
        val txtTempmax2 = view.findViewById<TextView>(R.id.txtTempmax2)
        val txtData2 = view.findViewById<TextView>(R.id.txtData2)

        val txtTempmin3 = view.findViewById<TextView>(R.id.txtTempmin3)
        val txtTempmax3 = view.findViewById<TextView>(R.id.txtTempmax3)
        val txtData3 = view.findViewById<TextView>(R.id.txtData3)

        // Chamada API
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val climaApi = retrofit.create(ClimaApi::class.java)

                // Chamada para clima atual
                val respostaClima = climaApi.getClima("Francisco Morato", API_KEY)

                // Chamada para previsão
                val respostaPrevisao = climaApi.getPrevisao("Francisco Morato", API_KEY)

                if (respostaClima.isSuccessful && respostaPrevisao.isSuccessful) {
                    val dadosClima = respostaClima.body()
                    val dadosPrevisao = respostaPrevisao.body()?.list ?: emptyList()

                    // Processar dados do clima atual
                    val temperatura = dadosClima?.main?.temp ?: 0.0
                    val tempmax = dadosClima?.main?.temp_max ?: 0.0
                    val tempmin = dadosClima?.main?.temp_min ?: 0.0
                    val umidade = dadosClima?.main?.humidity ?: 0
                    val vento = (dadosClima?.wind?.speed ?: 0.0) * 3.6 // Convertendo de m/s para km/h
                    val timestamp = dadosClima?.dt ?: 0L
                    val dataFormatada = formatarData(timestamp)

                    // Processar dados da previsão
                    val previsoesPorDia = dadosPrevisao.groupBy {
                        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        sdf.format(Date(it.dt * 1000))
                    }.values.take(3)

                    val previsaoTextos = previsoesPorDia.map { previsoes ->
                        val tempMax = previsoes.maxOf { it.main.temp_max }
                        val tempMin = previsoes.minOf { it.main.temp_min }
                        val data = formatarData(previsoes.first().dt)
                        Triple(data, tempMax.toInt(), tempMin.toInt())
                    }

                    // Atualizar UI
                    launch(Dispatchers.Main) {
                        txtTemp.text = "${temperatura.toInt()}°C"
                        txtTempmax.text = "${tempmax.toInt()}°"
                        txtTempmin.text = "${tempmin.toInt()}°"
                        txtUmidade.text = "${umidade}%"
                        txtVento.text = "${vento.toInt()} Km/h"
                        txtData.text = dataFormatada + " - Hoje"

                        // Atualizando previsões
                        txtData1.text = previsaoTextos.getOrElse(0) { Triple("N/A", 0, 0) }.first
                        txtTempmax1.text = "${previsaoTextos.getOrElse(0) { Triple("N/A", 0, 0) }.second}°C"
                        txtTempmin1.text = "${previsaoTextos.getOrElse(0) { Triple("N/A", 0, 0) }.third}°C"

                        txtData2.text = previsaoTextos.getOrElse(1) { Triple("N/A", 0, 0) }.first
                        txtTempmax2.text = "${previsaoTextos.getOrElse(1) { Triple("N/A", 0, 0) }.second}°C"
                        txtTempmin2.text = "${previsaoTextos.getOrElse(1) { Triple("N/A", 0, 0) }.third}°C"

                        txtData3.text = previsaoTextos.getOrElse(2) { Triple("N/A", 0, 0) }.first
                        txtTempmax3.text = "${previsaoTextos.getOrElse(2) { Triple("N/A", 0, 0) }.second}°C"
                        txtTempmin3.text = "${previsaoTextos.getOrElse(2) { Triple("N/A", 0, 0) }.third}°C"
                    }
                } else {
                    mostrarErro("Falha na API: ${respostaClima.errorBody()?.string() ?: respostaPrevisao.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                mostrarErro("Exceção: ${e.message}")
            }
        }
    }

    private fun formatarData(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
        val date = Date(timestamp * 1000)
        return sdf.format(date)
    }

    private fun mostrarErro(mensagem: String) {
        lifecycleScope.launch(Dispatchers.Main) {
            Toast.makeText(requireContext(), mensagem, Toast.LENGTH_LONG).show()
        }
    }
}
