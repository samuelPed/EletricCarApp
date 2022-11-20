package com.curso.eletriccarapp.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_BATTERY
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_CAR_ID
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_POTENCY
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_PRICE
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_RECHARGE
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_URL_PHOTO
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.TABLE_NAME
import com.curso.eletriccarapp.model.Car

class CarRepository(private val context: Context) {

    private fun saveOnDatabase(car: Car): Boolean {
        var isSaved = false
        try {
            val dbHelper = CarsDbHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, car.id)
                put(COLUMN_NAME_PRICE, car.price)
                put(COLUMN_NAME_BATTERY, car.battery)
                put(COLUMN_NAME_POTENCY, car.potency)
                put(COLUMN_NAME_RECHARGE, car.recharge)
                put(COLUMN_NAME_URL_PHOTO, car.urlPhoto)
            }

            val inserted = db.insert(CarsContract.CarEntry.TABLE_NAME, null, values)
            if (inserted > 0) {
                isSaved = true
            }
        } catch (e: Exception) {
            e.message?.let {
                Log.e("Error", it)
            }
        }
        return isSaved
    }

    private fun findCarById(id: Int): Car {
        var car =  Car(
        id = 0,
        price = "price",
        battery = "battery",
        potency = "potency",
        recharge = "recharge",
        urlPhoto = "urlPhoto",
        isFavorite = false
        )

        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase
        val filterValues = arrayOf(id.toString())
        val column = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POTENCY,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_URL_PHOTO
        )
        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val cursor = db.query(
            TABLE_NAME,
            column,
            filter,
            filterValues,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID ->", itemId.toString())

                val price = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                Log.d("PRICE -> ", price)

                val battery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                Log.d("BATTERY -> ", battery)

                val potency = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCY))
                Log.d("POTENCY -> ", potency)

                val recharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE))
                Log.d("RECHARGE -> ", recharge)

                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("URL -> ", urlPhoto)

                car = Car(
                    id = itemId.toInt(),
                    price = price,
                    battery = battery,
                    potency = potency,
                    recharge = recharge,
                    urlPhoto = urlPhoto,
                    isFavorite = true
                )
            }
        }
        cursor.close()
        return car
    }

    fun saveIfNotExists(car: Car) {
        val findCar = findCarById(car.id)
        if (findCar.id == ID_WHEN_NO_CAR) {
            saveOnDatabase(car)
        }
    }

    fun getAllCars(): List<Car>{

        val dbHelper = CarsDbHelper(context)
        val db = dbHelper.readableDatabase
        var cars = mutableListOf<Car>()
        val column = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POTENCY,
            COLUMN_NAME_RECHARGE,
            COLUMN_NAME_URL_PHOTO
        )
        val cursor = db.query(
            TABLE_NAME,
            column,
            null,
            null,
            null,
            null,
            null
        )

        with(cursor) {
            while (moveToNext()) {
                val itemId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                Log.d("ID ->", itemId.toString())

                val price = getString(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                Log.d("PRICE -> ", price)

                val battery = getString(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                Log.d("BATTERY -> ", battery)

                val potency = getString(getColumnIndexOrThrow(COLUMN_NAME_POTENCY))
                Log.d("POTENCY -> ", potency)

                val recharge = getString(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE))
                Log.d("RECHARGE -> ", recharge)

                val urlPhoto = getString(getColumnIndexOrThrow(COLUMN_NAME_URL_PHOTO))
                Log.d("URL -> ", urlPhoto)

                cars.add(Car(
                    id = itemId.toInt(),
                    price = price,
                    battery = battery,
                    potency = potency,
                    recharge = recharge,
                    urlPhoto = urlPhoto,
                    isFavorite = true
                ))
            }
        }
        cursor.close()
        return cars
    }
    companion object {
        const val ID_WHEN_NO_CAR = 0
    }
}