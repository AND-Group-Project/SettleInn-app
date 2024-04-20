package com.example.settleinn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class HouseDetailsFragment : AppCompatActivity() {
    private lateinit var detailHouseImage: ImageView
    private lateinit var locationTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var areaTextView: TextView
    private lateinit var bedroomsTextView: TextView
    private lateinit var statusTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_house_details)

        detailHouseImage = findViewById(R.id.houseImage)
        locationTextView = findViewById(R.id.location)
        priceTextView = findViewById(R.id.house_cost)
        areaTextView = findViewById(R.id.house_area)
        bedroomsTextView = findViewById(R.id.bedrooms)
        statusTextView = findViewById(R.id.status)

        val house = intent.getSerializableExtra(HOUSE_EXTRA) as HouseDetail

        locationTextView.text = house.address
        priceTextView.text = house.price.toString()
        areaTextView.text = house.livingArea.toString()
        bedroomsTextView.text = house.bedrooms.toString()
        statusTextView.text = house.listingStatus

        Glide.with(this)
            .load(house.imgSrc)
            .into(detailHouseImage)
    }
}