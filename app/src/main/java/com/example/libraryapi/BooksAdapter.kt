package com.example.libraryapi

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson

class BooksAdapter (private val originalDataGames: List<ResponseLibraryApiItem?>) : RecyclerView.Adapter<BooksAdapter.MyViewHolder>() {
    private var booksData: List<ResponseLibraryApiItem?> = originalDataGames.toMutableList()
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagenBook: ImageView = view.findViewById(R.id.imagen)
        val nameBook: TextView = view.findViewById(R.id.titulo)
        val autorBook: TextView = view.findViewById(R.id.autor)
        val isbnBook: TextView = view.findViewById(R.id.isbn)
        val fichaBook: TextView = view.findViewById(R.id.ficha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.book_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameBook.text = "Titulo: ${booksData[position]?.titulo}"
        holder.autorBook.text = "Autor: ${booksData[position]?.autorTitulo}"
        holder.isbnBook.text = "ISBN: ${booksData[position]?.isbn}"
        holder.fichaBook.text = "Ficha: ${booksData[position]?.woResultadoOpacPK?.ficha}"
        val url = "http://190.242.60.213:8383/OpacService/books/${booksData[position]?.woResultadoOpacPK?.ficha}.jpg"
        Glide
            .with(holder.imagenBook.context)
            .load(url)
            .centerCrop()
            .placeholder(R.drawable.library_icon)
            .into(holder.imagenBook)
    }

    override fun getItemCount(): Int = booksData.size
}