package com.example.libraryapi

import com.google.gson.annotations.SerializedName

data class ResponseLibraryApi(
	val responseLibraryApi: List<ResponseLibraryApiItem>? = null
)

data class WoResultadoOpacPK(

	@field:SerializedName("ficha")
	val ficha: String? = null,

	@field:SerializedName("fkWaEmpresa")
	val fkWaEmpresa: Int? = null
)

data class ResponseLibraryApiItem(

	@field:SerializedName("editorial")
	val editorial: String? = null,

	@field:SerializedName("fecha")
	val fecha: Any? = null,

	@field:SerializedName("dfisica")
	val dfisica: String? = null,

	@field:SerializedName("autorTitulo")
	val autorTitulo: String? = null,

	@field:SerializedName("isbn")
	val isbn: String? = null,

	@field:SerializedName("titulo")
	val titulo: String? = null,

	@field:SerializedName("signatura")
	val signatura: String? = null,

	@field:SerializedName("woResultadoOpacPK")
	val woResultadoOpacPK: WoResultadoOpacPK? = null,

	@field:SerializedName("autor")
	val autor: Any? = null,

	@field:SerializedName("edicion")
	val edicion: String? = null
)
