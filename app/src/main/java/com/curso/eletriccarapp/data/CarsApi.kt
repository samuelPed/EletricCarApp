package com.curso.eletriccarapp.data

import com.curso.eletriccarapp.model.Car
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {

    @GET("cars.json")
    fun getAllCars(): Call<List<Car>>

}