package com.curso.eletriccarapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.curso.eletriccarapp.R
import com.curso.eletriccarapp.adapter.CarAdapter
import com.curso.eletriccarapp.data.local.CarRepository
import com.curso.eletriccarapp.databinding.ActivityCalculateAutonomyBinding.inflate
import com.curso.eletriccarapp.databinding.CarFragmentBinding
import com.curso.eletriccarapp.databinding.FavoriteFragmentBinding
import com.curso.eletriccarapp.model.Car

class FavoriteFragment: Fragment(), View.OnClickListener {

    private lateinit var binding: FavoriteFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.favorite_fragment, container, false)
        binding = FavoriteFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository = CarRepository(requireContext())
        val carList = repository.getAllCars()
        setList(carList)

    }

    override fun onClick(view: View) {
        if(view.id == R.id.iv_close){

        }
    }

    private fun setList(list: List<Car>) {
        val adapter = CarAdapter(list, isFavoriteScreen = true)
        val listCar = binding.rvListCarFavorite
        listCar.adapter = adapter
    }

}