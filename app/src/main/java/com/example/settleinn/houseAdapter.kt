package com.example.settleinn
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.text.NumberFormat
import java.util.Locale

const val HOUSE_EXTRA = "HOUSE_EXTRA"
private const val TAG = "HouseAdapter"

class HouseListAdapter(private val context: Context, private var houselist: MutableList<HouseDetail>) : RecyclerView.Adapter<HouseListAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val houseImageView: ImageView = itemView.findViewById(R.id.houseImage)
        private val locationView: TextView = itemView.findViewById(R.id.location)
        private val priceView: TextView = itemView.findViewById(R.id.house_cost)
        private val areaView: TextView = itemView.findViewById(R.id.house_area)
        private val bedroomsView: TextView = itemView.findViewById(R.id.bedrooms)
        private val statusView: TextView = itemView.findViewById(R.id.status)
        init {
            itemView.setOnClickListener(this)
        }

        fun bind(house: HouseDetail) {
            bedroomsView.text = "${house.bedrooms} BR | ${house.bathrooms} BA"
            areaView.text = "${house.livingArea} sq.ft"
            priceView.text = "$" + formatPriceWithCommas(house.price)
            locationView.text = house.address
            statusView.text = house.listingStatus

            Glide.with(context)
                .load(house.imgSrc)
                .into(houseImageView)
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
    fun formatPriceWithCommas(price: Int): String {
        val formatter = NumberFormat.getNumberInstance(Locale.US)
        return formatter.format(price)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val house = houselist[position]
        holder.bind(house)
    }

    override fun getItemCount(): Int = houselist.size

    fun updateData(newData: List<HouseDetail>) {
        houselist.clear()
        houselist.addAll(newData)
        notifyDataSetChanged()
    }
}