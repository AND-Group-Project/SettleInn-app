package com.example.settleinn

import ApiResponse
import ZillowApiService
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.navigation.fragment.findNavController

class HomeScreenFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var apiService: ZillowApiService
    private lateinit var adapter: HouseListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = HouseListAdapter(mutableListOf())
        recyclerView.adapter = adapter

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRetrofit()

        val applyFilter: ImageButton = view.findViewById(R.id.button)
        val filtersForm = view.findViewById<LinearLayout>(R.id.filters)
        val submitfilter: Button = view.findViewById(R.id.submitFilters)

        applyFilter.setOnClickListener {
            filtersForm.visibility = View.VISIBLE
        }

        submitfilter.setOnClickListener {
            val searchField = view.findViewById<EditText>(R.id.search_field).text.toString()
            val priceLow = view.findViewById<EditText>(R.id.priceLow).text.toString().toDoubleOrNull() ?: 0.0
            val priceHigh = view.findViewById<EditText>(R.id.priceHigh).text.toString().toDoubleOrNull() ?: 0.0
            val bedrooms = view.findViewById<Spinner>(R.id.bedroomSpinner).selectedItem.toString()
            val bathrooms = view.findViewById<Spinner>(R.id.bathroomSpinner).selectedItem.toString()
            val hasPool = view.findViewById<Spinner>(R.id.poolSpinner).selectedItem.toString()
            val propertyType = when (view.findViewById<RadioGroup>(R.id.rentBuySelector).checkedRadioButtonId) {
                R.id.radioRent -> "Rent"
                R.id.radioBuy -> "Buy"
                else -> "Rent"
            }
            val parkingSpots = view.findViewById<Spinner>(R.id.parkingSpotsSpinner).selectedItem.toString()
            val hasGarage = view.findViewById<Spinner>(R.id.garageSpinner).selectedItem.toString()
            val isNewConstruction = view.findViewById<Spinner>(R.id.constructionSpinner).selectedItem.toString()

            filtersForm.visibility = View.GONE
            fetchData(searchField, "homes", bedrooms, bathrooms)
        }
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://zillow-com1.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ZillowApiService::class.java)
    }

    fun fetchData(location: String?, homeType: String?, bedrooms: String?, bathrooms:String?) {
        val queryMap = mutableMapOf<String, String>()

        // Add location to the query map if not null
        location?.let { queryMap["location"] = it }

        // Add home type to the query map if not null
        homeType?.let { queryMap["home_type"] = it }

        if (bedrooms != null && bedrooms.uppercase() != "N/A") {
            queryMap["bedsMax"] = bedrooms
        }
        if (bathrooms != null && bathrooms.uppercase() != "N/A") {
            queryMap["bathsMax"] = bathrooms
        }
        val call = apiService.searchProperties(queryMap)

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.props?.let { properties ->
                        adapter.updateData(properties)
                    }
                } else {
                    Log.e("API Error", "Response Code: ${response.code()} - ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.e("API Error", "Failed to fetch data: ${t.message}")
            }
        })
    }
}
