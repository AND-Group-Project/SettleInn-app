package com.example.settleinn

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale

const val HOUSE_EXTRA = "HOUSE_EXTRA"
private const val TAG = "HouseAdapter"
class HouseListAdapter(
    private val context: Context,
    private var houselist: MutableList<HouseDetail>,
    private val onSaveButtonClick: (HouseDetail) -> Unit,
    param: (Any) -> Unit
) : RecyclerView.Adapter<HouseListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val houseImageView: ImageView = itemView.findViewById(R.id.houseImage)
        val locationView: TextView = itemView.findViewById(R.id.location)
        val priceView: TextView = itemView.findViewById(R.id.house_cost)
        val areaView: TextView = itemView.findViewById(R.id.house_area)
        val bedroomsView: TextView = itemView.findViewById(R.id.bedrooms)
        val statusView: TextView = itemView.findViewById(R.id.status)
        val saveButton: Button = itemView.findViewById(R.id.saveButton)

        init {
            itemView.setOnClickListener(this)
            saveButton.setOnClickListener {
                val house = houselist[adapterPosition]
                onSaveButtonClick(house)
            }
        }

        override fun onClick(v: View?) {
            val house = houselist[adapterPosition]
            val intent = Intent(context, HouseDetailsActivity::class.java)
            intent.putExtra(HOUSE_EXTRA, house)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val house = houselist[position]
        holder.bedroomsView.text = "${house.bedrooms} bds • ${house.bathrooms} ba •"
        holder.areaView.text = " ${house.livingArea} sqft • "
        holder.priceView.text = "$" + house.price?.let { formatPriceWithCommas(it) }
        holder.locationView.text = house.address
        if (house.listingStatus == "FOR_SALE") {
            holder.statusView.text = "Sale"
        } else if (house.listingStatus == "FOR_RENT") {
            holder.statusView.text = "Rent"
        } else {
            holder.statusView.text = "Inactive"
        }


        Glide.with(context)
            .load(house.imgSrc)
            .into(holder.houseImageView)
    }

    override fun getItemCount(): Int = houselist.size

    fun updateData(newData: List<HouseDetail>) {
        houselist.clear()
        houselist.addAll(newData)
        notifyDataSetChanged()
    }

    private fun formatPriceWithCommas(price: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(price)
    }
}
