package com.example.libraryapi

import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.util.Log
import android.widget.ImageView
import com.example.libraryapi.Api.NetworkTaskListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class NetworkTask(private val tipo: String, private val palabra: String) : AsyncTask<Void, Void, List<ResponseLibraryApiItem>>() {

    private var listener: NetworkTaskListener? = null

    fun setListener(listener: NetworkTaskListener) {
        this.listener = listener
    }

    override fun doInBackground(vararg params: Void?): List<ResponseLibraryApiItem> {
        val apiUrl = "http://190.242.60.213:8383/restlmC/webresources/opacd/consultapalabra?catalogo=1&tipo=$tipo&palabra=$palabra"
        try {
            val url = URL(apiUrl)
            val connection = url.openConnection() as HttpURLConnection

            // Configura la solicitud HTTP
            connection.requestMethod = "GET"
            connection.setRequestProperty("Content-Type", "application/json")

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response = StringBuilder()
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    response.append(line)
                }
                reader.close()

                // Parsea la respuesta de la API y conviértela en una lista de elementos
                val libraryItems = parseApiResponse(response.toString())
                return libraryItems
            } else {
                // Manejo de errores de respuesta (códigos HTTP, mensajes de error, etc.)
                return emptyList()
            }
        } catch (e: Exception) {
            // Manejo de excepciones de red u otras excepciones
            e.printStackTrace()
            return emptyList()
        }
    }

    override fun onPostExecute(result: List<ResponseLibraryApiItem>) {
        super.onPostExecute(result)
        listener?.onNetworkTaskComplete(result)
    }

    private fun parseApiResponse(response: String): List<ResponseLibraryApiItem> {
        val gson = Gson()

        // Definir el tipo de datos al que deseas analizar la respuesta JSON
        val libraryItemsType = object : TypeToken<List<ResponseLibraryApiItem>>() {}.type

        // Analizar la respuesta JSON en una lista de objetos ResponseLibraryApiItem
        val libraryItems: List<ResponseLibraryApiItem> = gson.fromJson(response, libraryItemsType)

        return libraryItems
    }
}
