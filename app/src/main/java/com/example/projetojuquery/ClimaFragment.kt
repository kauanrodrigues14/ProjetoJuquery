package com.example.projetojuquery

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
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


class ClimaFragment : FragmentActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.fragment_clima)


        val txtTemp = findViewById<TextView>(R.id.txtTemp)
        val txtUmidade = findViewById<TextView>(R.id.txtUmidade)
        val txtVento = findViewById<TextView>(R.id.txtVento)
        val txtChuva = findViewById<TextView>(R.id.txtChuva)
        val txtData = findViewById<TextView>(R.id.txtData)
        val txtTempmax = findViewById<TextView>(R.id.txtTempmax)
        val txtTempmin = findViewById<TextView>(R.id.txtTempmin)

        // Previsão dos próximos dias
        val txtTempmin1 = findViewById<TextView>(R.id.txtTempmin1)
        val txtTempmax1 = findViewById<TextView>(R.id.txtTempmax1)
        val txtData1 = findViewById<TextView>(R.id.txtData1)

        val txtTempmin2 = findViewById<TextView>(R.id.txtTempmin2)
        val txtTempmax2 = findViewById<TextView>(R.id.txtTempmax2)
        val txtData2 = findViewById<TextView>(R.id.txtData2)

        val txtTempmin3 =findViewById<TextView>(R.id.txtTempmin3)
        val txtTempmax3 = findViewById<TextView>(R.id.txtTempmax3)
        val txtData3 = findViewById<TextView>(R.id.txtData3)

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
                    val precipitationProbability = dadosClima?.pop ?: 0.0
                    val chanceChuva = (precipitationProbability * 100).toInt()
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
                        txtChuva.text = "${chanceChuva}%"
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

                }
            } catch (e: Exception) {

            }
        }
    }

    private fun formatarData(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
        val date = Date(timestamp * 1000)
        return sdf.format(date)
    }


}
