package com.curso.eletriccarapp.model

import com.google.gson.annotations.SerializedName

data class Car(
    @SerializedName("id")
    val id: Int,
    @SerializedName("preco")
    val price: String,
    @SerializedName("bateria")
    val battery: String,
    @SerializedName("potencia")
    val potency: String,
    @SerializedName("recarga")
    val recharge: String,
    @SerializedName("urlPhoto")
    val urlPhoto: String,
    var isFavorite: Boolean
)