package com.example.libraryapi.Api

import com.example.libraryapi.ResponseLibraryApiItem

interface NetworkTaskListener {
    fun onNetworkTaskComplete(libraryItems: List<ResponseLibraryApiItem>)
}