package com.example.settleinn
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.w3c.dom.Text


class HouseListAdapter(private var houselist: MutableList<HouseDetail>) : RecyclerView.Adapter<HouseListAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val houseImageView: ImageView = itemView.findViewById(R.id.houseImage)
        val locationView: TextView = itemView.findViewById(R.id.location)
        val priceView: TextView = itemView.findViewById(R.id.house_cost)
        val areaView: TextView = itemView.findViewById(R.id.house_area)
        val bedroomsView: TextView = itemView.findViewById(R.id.bedrooms)
        val statusView: TextView = itemView.findViewById(R.id.status)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyclerview_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val house = houselist[position]
        holder.bedroomsView.text = "${house.bedrooms} BR | ${house.bathrooms} BA"
        holder.areaView.text = "${house.livingArea} sq.ft"
        holder.priceView.text = "$${house.price}"
        holder.locationView.text = house.address
        holder.statusView.text = house.listingStatus

        Glide.with(holder.houseImageView.context)
            .load(house.imgSrc)
            .into(holder.houseImageView)
    }

    override fun getItemCount(): Int = houselist.size

    fun updateData(newData: List<HouseDetail>) {
        houselist.clear()
        houselist.addAll(newData)
        notifyDataSetChanged()
    }
}