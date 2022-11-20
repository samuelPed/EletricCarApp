package com.curso.eletriccarapp.data.local

import android.provider.BaseColumns

object CarsContract {
    object CarEntry : BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_CAR_ID = "car_id"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_POTENCY = "potency"
        const val COLUMN_NAME_RECHARGE = "recharge"
        const val COLUMN_NAME_URL_PHOTO = "url_photo"
    }

    const val TABLE =
        "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${CarEntry.COLUMN_NAME_CAR_ID} TEXT," +
                "${CarEntry.COLUMN_NAME_PRICE} TEXT," +
                "${CarEntry.COLUMN_NAME_BATTERY} TEXT," +
                "${CarEntry.COLUMN_NAME_POTENCY} TEXT," +
                "${CarEntry.COLUMN_NAME_RECHARGE} TEXT," +
                "${CarEntry.COLUMN_NAME_URL_PHOTO} TEXT)"

    const val SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"
}