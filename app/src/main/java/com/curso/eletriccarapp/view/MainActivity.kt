package com.curso.eletriccarapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.curso.eletriccarapp.R
import com.curso.eletriccarapp.adapter.CarAdapter
import com.curso.eletriccarapp.adapter.TabAdapter
import com.curso.eletriccarapp.data.CarFactory
import com.curso.eletriccarapp.databinding.ActivityMainBinding
import com.curso.eletriccarapp.model.Car
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setTabs()

    }

    private fun setTabs(){
        val adapter = TabAdapter(this)
        val viewPager = binding.vp2Car
        viewPager.adapter = adapter

        val mTabLayout = binding.tlCar
        mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                mTabLayout.getTabAt(position)?.select()
            }
        })
    }
}