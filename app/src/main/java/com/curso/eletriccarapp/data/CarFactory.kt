package com.curso.eletriccarapp.data

import com.curso.eletriccarapp.model.Car

object CarFactory {

    val list = listOf(
        Car(
            id = 0,
            price = "300.000,00",
            battery = "350 kWh",
            potency = "700 CV",
            recharge = "28 MIN",
            urlPhoto = "www.google.com",
            isFavorite = false
        )


    )
}