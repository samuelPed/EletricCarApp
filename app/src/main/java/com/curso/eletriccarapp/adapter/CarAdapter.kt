package com.curso.eletriccarapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.curso.eletriccarapp.R
import com.curso.eletriccarapp.data.CarFactory.list
import com.curso.eletriccarapp.data.local.CarsContract
import com.curso.eletriccarapp.data.local.CarsContract.CarEntry.TABLE_NAME
import com.curso.eletriccarapp.data.local.CarsDbHelper
import com.curso.eletriccarapp.model.Car

class CarAdapter(private val listCars: List<Car>, private val isFavoriteScreen: Boolean = false) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    var carItemListener: (Car) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text = listCars[position].price
        holder.battery.text = listCars[position].battery
        holder.potency.text = listCars[position].potency
        holder.recharge.text = listCars[position].recharge
        holder.favorite.setOnClickListener {
            val car = listCars[position]
            carItemListener(car)
            setFavorite(car, holder)
        }
        if (isFavoriteScreen) holder.favorite.setImageResource(R.drawable.ic_close)
    }

    override fun getItemCount(): Int = listCars.size

    private fun setFavorite(
        car: Car,
        holder: ViewHolder
    ) {
        if (isFavoriteScreen) {
            holder.favorite.setImageResource(R.drawable.ic_close)
        } else {
            car.isFavorite = !car.isFavorite
            if (car.isFavorite) holder.favorite.setImageResource(R.drawable.ic_star_selected)
            else holder.favorite.setImageResource(R.drawable.ic_star)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val price: TextView
        val battery: TextView
        val potency: TextView
        val recharge: TextView
        val favorite: ImageView

        init {
            view.apply {
                price = findViewById(R.id.tv_price_value)
                battery = findViewById(R.id.tv_battery_value)
                potency = findViewById(R.id.tv_potency_value)
                recharge = findViewById(R.id.tv_recharge_value)
                favorite = findViewById(R.id.iv_favorite)
            }

        }

    }
}