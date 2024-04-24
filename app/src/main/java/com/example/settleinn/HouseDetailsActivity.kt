package com.example.settleinn
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import java.text.NumberFormat
import java.util.Locale
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.Manifest


class HouseDetailsActivity : AppCompatActivity(),  OnMapReadyCallback  {
    private lateinit var detailHouseImage: ImageView
    private lateinit var locationTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var areaTextView: TextView
    private lateinit var bedroomBathTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var backbutton: ImageButton
    private lateinit var houseDescription: TextView
    private lateinit var schoolsContainer: LinearLayout
    private lateinit var housesContainer: LinearLayout
    private lateinit var googleMap: GoogleMap
    private var latitude: Double = 47.6205
    private var longitude: Double = -122.3493
    private var title: String = "Seattle"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_house_detail)
        detailHouseImage = findViewById(R.id.houseImage)
        locationTextView = findViewById(R.id.location)
        priceTextView = findViewById(R.id.house_cost)
        areaTextView = findViewById(R.id.house_area)
        bedroomBathTextView = findViewById(R.id.bedrooms)
        statusTextView = findViewById(R.id.status)
        backbutton = findViewById(R.id.backButton)
        houseDescription = findViewById(R.id.houseDescription)
        schoolsContainer = findViewById(R.id.schoolsContainer)
        housesContainer = findViewById(R.id.housesContainer)


        backbutton.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right)
        }

        val house = intent.getSerializableExtra("HOUSE_EXTRA") as HouseDetail
        locationTextView.text = house.address
        title = house.address.toString()
        priceTextView.text = "$" + house.price?.let { formatPriceWithCommas(it) }
        areaTextView.text = house.livingArea.toString() + " sqft"
        bedroomBathTextView.text = house.bedrooms.toString() + "bd | " + house.bathrooms.toString() + "ba"
        if (house.listingStatus == "FOR_SALE") {
            statusTextView.text = "For Sale"
        } else {
            statusTextView.text = "Not For Sale"
        }
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


        Glide.with(this)
            .load(house.imgSrc)
            .into(detailHouseImage)

        getData(house.zpid)

    }

    fun formatPriceWithCommas(price: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(price)
    }

    fun parseSchools(jsonInput: String): List<SchoolInfo> {
        val resultList = mutableListOf<SchoolInfo>()
        val jsonObject = JSONObject(jsonInput)
        val schoolsArray = jsonObject.getJSONArray("schools")

        for (i in 0 until schoolsArray.length()) {
            val school = schoolsArray.getJSONObject(i)
            val name = school.getString("name")
            val rating = school.getInt("rating")
            val distance = school.getDouble("distance")
            val type = school.getString("type")
            val url = school.getString("link")

            resultList.add(SchoolInfo(url, rating, distance, name, type))
        }
        return resultList
    }

    private fun addSchoolsDynamically(schools : List<SchoolInfo>) {

        schools.forEach { school ->
            val schoolView = LayoutInflater.from(this).inflate(R.layout.school_item, schoolsContainer, false)
            val schoolName = schoolView.findViewById<TextView>(R.id.school_name)
            val schoolRating = schoolView.findViewById<TextView>(R.id.school_rating)
            val schoolDistance = schoolView.findViewById<TextView>(R.id.school_distance)
            val schoolType = schoolView.findViewById<TextView>(R.id.school_type)

            schoolName.text = school.name
            schoolRating.text = " Rating: ${school.rating}" + " / 10"
            schoolDistance.text = " Distance: ${school.distance} miles"
            schoolType.text = " Type: " + school.type + " school"

            schoolsContainer.addView(schoolView)
        }
    }

    fun parseHouses(jsonInput: String): List<HouseInfo> {
        val resultList = mutableListOf<HouseInfo>()
        val jsonObject = JSONObject(jsonInput)
        val housesArray = jsonObject.getJSONArray("nearbyHomes")

        for (i in 0 until housesArray.length()) {
            try {
                val house = housesArray.getJSONObject(i)
                val miniCardPhotos = house.getJSONArray("miniCardPhotos")
                val imageUrl = if (miniCardPhotos.length() > 0) miniCardPhotos.getJSONObject(0).getString("url") else "Default URL"

                val price = if (house.has("price")) house.getInt("price") else 0
                val bedrooms = if (house.has("bedrooms")) house.getInt("bedrooms") else 0
                val bathrooms = if (house.has("bathrooms")) house.getInt("bathrooms") else 0
                val area = if (house.has("livingAreaValue")) house.getInt("livingAreaValue") else 0

                resultList.add(HouseInfo(imageUrl, price, bedrooms, bathrooms, area))
            } catch (e: JSONException) {
                Log.e("JSON Parsing Error", "Error parsing an item in the houses array", e)
            }
        }
        return resultList
    }


    private fun addHousesDynamically(houses: List<HouseInfo>) {
        houses.forEach { house ->
            val houseView = LayoutInflater.from(this).inflate(R.layout.house_item, housesContainer, false)
            val houseImage = houseView.findViewById<ImageView>(R.id.house_image)
            val housePrice = houseView.findViewById<TextView>(R.id.house_price)
            val houseDetails = houseView.findViewById<TextView>(R.id.house_details)

            Glide.with(this).load(house.imageUrl).into(houseImage)
            housePrice.text = "${house.price}"
            houseDetails.text = "${house.bedrooms} beds ${house.bathrooms} baths ${house.area} sqft"

            housesContainer.addView(houseView)
        }
    }


    private fun getData(id: Int) {
        val client = OkHttpClient()
        val apiKey = getString(R.string.api_key_zillow)
        val request = Request.Builder()
            .url("https://zillow-com1.p.rapidapi.com/property?zpid=$id")
            .get()
            .addHeader("X-RapidAPI-Key", apiKey)
            .addHeader("X-RapidAPI-Host", "zillow-com1.p.rapidapi.com")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Log.e("HTTP Error", "Error fetching data", e)
                    houseDescription.text = "Failed to load data: ${e.message}"
                }
            }


            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!it.isSuccessful) {
                        runOnUiThread {
                            houseDescription.text = "Failed to load data: ${it.message}"
                            Log.e("API Error", "Failed with call: ${it.body?.string()}")
                        }
                    } else {
                        val responseData = it.body?.string()
                        if (responseData != null) {
                            try {
                                val jsonObject = JSONObject(responseData)
                                val description = jsonObject.optString("description", "No description available.")
                                latitude  = jsonObject.optDouble("latitude", Double.NaN)
                                longitude = jsonObject.optDouble("longitude", Double.NaN)
                                runOnUiThread {
                                    updateMap()
                                }
                                val school_List = parseSchools(jsonObject.toString())
                                //val nearby_houseList = parseHouses(jsonObject.toString())

                                runOnUiThread {
                                    houseDescription.text = description
                                    addSchoolsDynamically(school_List)
                                    //addHousesDynamically(nearby_houseList)
                                }
                            } catch (e: JSONException) {
                                runOnUiThread {
                                    houseDescription.text = "Error parsing data."
                                    Log.e("JSON Parsing Error", "Error parsing JSON", e)
                                }
                            }
                        }
                    }
                }
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
        }

        updateMap()
    }

    private fun updateMap() {
        val location = LatLng(latitude, longitude)
        googleMap.clear()
        googleMap.addMarker(
            MarkerOptions().position(location).title(title).snippet("Updated Place")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
    }

}

