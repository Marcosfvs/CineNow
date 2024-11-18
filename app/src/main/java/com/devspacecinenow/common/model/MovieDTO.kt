package com.devspacecinenow.common.model

import com.google.gson.annotations.SerializedName

//DTO - Data Transfer Object, representa a transferencia de objetos
//Notação para o retrofit transformar o Gson em dataclass,
//nesse caso não precisa pois temos um conversor(.addConverterFactory(GsonConverterFactory.create())) no retrofit que já possui essa funcionalidade
//@kotlinx.serialization.Serializable
data class MovieDTO(
    val id: Int,
    val title:String,
    val overview:String,
    @SerializedName("poster_path")
    val postPath: String,
){
    val posterFullPath: String
        get() = "https://image.tmdb.org/t/p/w300$postPath"
}


