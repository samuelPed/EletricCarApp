package com.curso.eletriccarapp.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.curso.eletriccarapp.R
import com.curso.eletriccarapp.adapter.CarAdapter
import com.curso.eletriccarapp.data.CarFactory.list
import com.curso.eletriccarapp.data.CarsApi
import com.curso.eletriccarapp.data.local.CarRepository
import com.curso.eletriccarapp.databinding.CarFragmentBinding
import com.curso.eletriccarapp.model.Car
import org.json.JSONArray
import org.json.JSONTokener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class CarFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: CarFragmentBinding
    private val listCar: ArrayList<Car> = ArrayList()
    private lateinit var progressLoader: ProgressBar
    private lateinit var carApi: CarsApi

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inflater.inflate(R.layout.car_fragment, container, false)
        binding = CarFragmentBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        progressLoader = binding.pbLoader
        binding.fabOpenCalculate.setOnClickListener(this)

        createRetrofit()
    }

    override fun onResume() {
        super.onResume()

        if (checkForInternet(context)) {
            //Other form of call services
            //callService()

            getAllCars()
        } else {
            emptyState()
        }
        getStateFavorite()

    }

    override fun onClick(view: View) {
        if (view.id == R.id.fab_open_calculate) {
            startActivity(Intent(context, CalculateAutonomyActivity::class.java))
        }
    }

    private fun setList(list: List<Car>) {
        val adapter = CarAdapter(list)
        val listCar = binding.rvListCar
        listCar.adapter = adapter

        adapter.carItemListener = {
            val isSaved = CarRepository(requireContext()).saveIfNotExists(it)
        }

    }

    private fun getStateFavorite(){
        //
    }

    private fun callService() {
        val urlBase = "https://igorbag.github.io/cars-api/cars.json"
        GetCarInformation().execute(urlBase)
    }

    private fun checkForInternet(context: Context?): Boolean {
        val connectionManager = context?.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectionManager.activeNetwork ?: return false
            val activeNetwork = connectionManager.getNetworkCapabilities(network) ?: return false

            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectionManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    private fun emptyState() {
        binding.rvListCar.isVisible = false
        progressLoader.isVisible = false
        binding.ivEmptyState.isVisible = true
        binding.tvEmptyInternet.isVisible = true
    }

    private fun createRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://igorbag.github.io/cars-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        carApi = retrofit.create(CarsApi::class.java)
    }

    private fun getAllCars() {
        carApi.getAllCars().enqueue(object : Callback<List<Car>> {
            override fun onResponse(call: Call<List<Car>>, response: Response<List<Car>>) {
                if (response.isSuccessful) {
                    progressLoader.isVisible = false

                    response.body()?.let {
                        setList(it)
                    }
                } else {
                    Toast.makeText(context, R.string.response_error, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Car>>, t: Throwable) {
                Toast.makeText(context, R.string.response_error, Toast.LENGTH_SHORT).show()
            }

        })
    }

    //Not uses this class, use only Retrofit for abstract services
    inner class GetCarInformation : AsyncTask<String, String, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressLoader.isVisible = true

        }

        override fun doInBackground(vararg url: String?): String {
            val urlConnection: HttpsURLConnection?
            try {
                val urlBase = URL(url[0])

                urlConnection = urlBase.openConnection() as HttpsURLConnection
                urlConnection.connectTimeout = 60000
                urlConnection.readTimeout = 60000
                urlConnection.setRequestProperty(
                    "Accept",
                    "application/json"
                )

                val responseCode = urlConnection.responseCode

                if (responseCode == HttpsURLConnection.HTTP_OK) {
                    val response = urlConnection.inputStream.bufferedReader().use { it.readText() }
                    publishProgress(response)

                } else Log.e("ErrorHttp", "Error at Server, not response...")

            } catch (ex: Exception) {
                Log.e("Error", "Error at realize processing...")
            }

            return ""
        }

        override fun onProgressUpdate(vararg values: String?) {
            try {
                val jsonArray = JSONTokener(values[0]).nextValue() as JSONArray

                for (i in 0 until jsonArray.length()) {

                    val id = jsonArray.getJSONObject(i).getString("id")
                    Log.d("Description", "ID $id")

                    val price = jsonArray.getJSONObject(i).getString("preco")
                    Log.d("Description", "PRICE $price")

                    val battery = jsonArray.getJSONObject(i).getString("bateria")
                    Log.d("Description", "BATTERY $battery")

                    val potency = jsonArray.getJSONObject(i).getString("potencia")
                    Log.d("Description", "POTENCY $potency")

                    val recharge = jsonArray.getJSONObject(i).getString("recarga")
                    Log.d("Description", "RECHARGE $recharge")

                    val urlPhoto = jsonArray.getJSONObject(i).getString("urlPhoto")
                    Log.d("Description", "URL $urlPhoto")

                    val model = Car(
                        id = id.toInt(),
                        price = price,
                        battery = battery,
                        potency = potency,
                        recharge = recharge,
                        urlPhoto = urlPhoto,
                        isFavorite = false
                    )
                    listCar.add(model)
                }
                progressLoader.isVisible = false
                //setList()
            } catch (e: Exception) {
                Log.e("Error", "Error at user JSONArray")
            }
        }
    }
}