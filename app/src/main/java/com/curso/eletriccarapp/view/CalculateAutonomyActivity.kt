package com.curso.eletriccarapp.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.curso.eletriccarapp.R
import com.curso.eletriccarapp.databinding.ActivityCalculateAutonomyBinding

class CalculateAutonomyActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding : ActivityCalculateAutonomyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateAutonomyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCalculate.setOnClickListener(this)
        binding.ivClose.setOnClickListener(this)

        setCachedResult()

    }

    override fun onClick(view: View) {
        if(view.id == R.id.btn_calculate){
            calculateValue()
        }
        if(view.id == R.id.iv_close){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun calculateValue(){
        val price = binding.etPriceKwh.text.toString().toFloat()
        val kmTraveled = binding.etKmTraveled.text.toString().toFloat()
        val total: Float = price / kmTraveled
        binding.tvTotal.text = total.toString()

        saveSharedPreferences(total)

    }

    private fun saveSharedPreferences(result : Float){
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putFloat(getString(R.string.text_saved_calculate), result)
            apply()
        }
    }

    private fun getSharedPreferences(): Float{
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.text_saved_calculate), 0.0f)
    }

    private fun setCachedResult() {
        val valueCalculated = getSharedPreferences()
        val resultText = binding.tvTotal
        resultText.text = valueCalculated.toString()
    }
}