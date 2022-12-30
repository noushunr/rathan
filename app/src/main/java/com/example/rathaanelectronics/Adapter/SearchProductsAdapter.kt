package com.example.rathaanelectronics.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Interface.AddressItemClick
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Model.AddressResponseModel
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.Product
import com.example.rathaanelectronics.R


class SearchProductsAdapter(
    activity: FragmentActivity?,
    alSearch: List<Product>,
    hotdealsItemClick: HotdealsItemClick,
    isArabic: Boolean
) : RecyclerView.Adapter<SearchProductsAdapter.ViewHolder>() {
    var context = activity
    var alSearch = alSearch
    var hotdealsItemClick = hotdealsItemClick
    var isArabic = isArabic
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: SearchProductsAdapter.ViewHolder, position: Int) {
       // holder.bindItems(userList[position])
        if (isArabic)
            holder.tvName.text = alSearch[position].productNameArab
        else
            holder.tvName.text = alSearch[position].productName
        holder.tvName.setOnClickListener {
            hotdealsItemClick.onAddToWishlistButtonClick(alSearch[position].productId!!)
        }


    }


    override fun getItemCount(): Int {
        return alSearch.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName = itemView.findViewById<TextView>(R.id.tv_name)

        }
    }

