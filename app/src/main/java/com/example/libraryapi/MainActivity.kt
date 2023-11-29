package com.example.libraryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.libraryapi.Api.NetworkTaskListener

class MainActivity : AppCompatActivity(), NetworkTaskListener {
//    private var dataBooks: List<dataBooks?> = emptyList()
    private lateinit var recycler: RecyclerView
    private lateinit var loading: ImageView
    private lateinit var error: TextView
    private lateinit var scrollView: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        Thread.sleep(3000)
        setTheme(R.style.menu)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val opcionesTipoConsulta: Spinner = findViewById(R.id.opcionesTipo)
        val opcionesTipoConsulta2: Spinner = findViewById(R.id.opcionesTipo2)
        val listaTipoConsulta1 = resources.getStringArray(R.array.opcionesTipoConsulta)
        val listaTipoConsulta2 = resources.getStringArray(R.array.opcionesCatalogoConsulta)
        val btn_consulta:Button = findViewById(R.id.btn_consulta)
        scrollView = findViewById(R.id.scrollView)
        val cards : LinearLayout = findViewById(R.id.linearLayout2)
        error = findViewById(R.id.error)
        loading = findViewById(R.id.loading)
        val adaptadorTipoConsulta1 =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTipoConsulta1)
        val adaptadorTipoConsulta2 =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, listaTipoConsulta2)
        var tipoConsultaUser = ""
        val inputConsulta: EditText = findViewById(R.id.input_consulta)
        recycler = findViewById(R.id.recyclerView)
        opcionesTipoConsulta.adapter = adaptadorTipoConsulta1
        opcionesTipoConsulta2.adapter = adaptadorTipoConsulta2
        opcionesTipoConsulta.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (listaTipoConsulta1[p2] == "Todos los Campos") {
                    tipoConsultaUser = "F"
                } else {
                    tipoConsultaUser = listaTipoConsulta1[p2][0].toString()
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        btn_consulta.setOnClickListener {
            val palabraBuscar = inputConsulta.text.toString()
            if(palabraBuscar.length>1){
                loading.visibility = View.VISIBLE
                scrollView.visibility = View.GONE
                error.visibility = View.GONE
                Glide.with(this)
                    .asGif() // Indica a Glide que es un archivo GIF
                    .load(R.drawable.loader) // Reemplaza con el nombre de tu archivo GIF
                    .into(loading)
                cards.visibility = View.GONE
                Log.d("tipoConsultaUser",tipoConsultaUser)
                val network = NetworkTask(tipoConsultaUser,palabraBuscar)
                network.setListener(this@MainActivity)
                network.execute()
            }

        }
    }
    override fun onNetworkTaskComplete(libraryItems: List<ResponseLibraryApiItem>) {
        val layoutManager = LinearLayoutManager(this)
        recycler.layoutManager = layoutManager
        if(libraryItems.isEmpty()){
            loading.visibility = View.GONE
            error.visibility = View.VISIBLE
        }else{
            val adapter = BooksAdapter(libraryItems)
            recycler.adapter = adapter
            loading.visibility = View.GONE
            scrollView.visibility = View.VISIBLE
        }
    }
//    fun fetchDataFromAPI() {
//        val catalogoValue = 1
//        val tipoValue = "F"
//        val palabraValue = "farmacologia"
//        ApiConfig.getApiService().getBook(catalogoValue,tipoValue,palabraValue).enqueue(object : retrofit2.Callback<dataBooks> {
//            override fun onResponse(call: Call<dataBooks>, response: Response<dataBooks>) {
//                if (response.isSuccessful) {
//                    val bookList = response.body()
//                    val datos = bookList?.datos?: emptyList()
//                    Log.d("datos",datos.toString())
////                    val gameAdapter = BooksAdapter(datos)
////                    recycler.layoutManager = LinearLayoutManager(this@MainActivity)
////                    recycler.setHasFixedSize(true)
////                    recycler.adapter = gameAdapter
//                } else {
//                    Log.d("error en la api", response.code().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<dataBooks>, t: Throwable) {
//                Log.d("error1", t.message.toString())
//                Log.d("error", t.localizedMessage)
//                Toast.makeText(applicationContext, t.localizedMessage, Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//    fun main() {
//        val apiUrl = "http://190.242.60.213:8383/restlmC/webresources/opacd/consultapalabra?catalogo=1&tipo=F&palabra=farmacologia"
//
//        try {
//            val url = URL(apiUrl)
//            val connection = url.openConnection() as HttpURLConnection
//
//            // Configura la solicitud HTTP
//            connection.requestMethod = "GET"
//            connection.setRequestProperty("Content-Type", "application/json")
//
//            val responseCode = connection.responseCode
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                val inputStream = connection.inputStream
//                val reader = BufferedReader(InputStreamReader(inputStream))
//                val response = StringBuilder()
//                var line: String?
//
//                while (reader.readLine().also { line = it } != null) {
//                    response.append(line)
//                }
//                reader.close()
//
//                // Aquí tienes la respuesta en formato de cadena
//                val responseBody = response.toString()
//                println(responseBody)
//
//                // Puedes convertir la respuesta JSON en objetos de Kotlin si es necesario
//                // Utiliza una biblioteca como Gson o Moshi para hacerlo
//            } else {
//                // Manejo de errores de respuesta (códigos HTTP, mensajes de error, etc.)
//                println("Error en la respuesta: $responseCode")
//            }
//            connection.disconnect()
//        } catch (e: Exception) {
//            // Manejo de excepciones de red u otras excepciones
//            e.printStackTrace()
//        }
//    }


}