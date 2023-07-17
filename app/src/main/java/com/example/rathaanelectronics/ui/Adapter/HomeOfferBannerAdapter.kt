package com.example.rathaanelectronics.ui.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Model.HomeOfferBannerModel.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants


class HomeOfferBannerAdapter(
    activity: FragmentActivity?,
    allcategory_data: MutableList<Data>,
    listener: HomeOfferBannerClickListener,
    isArabic: Boolean,

    ) :
    RecyclerView.Adapter<HomeOfferBannerAdapter.ViewHolder>() {
    var context = activity
    var allcategory_data = allcategory_data
    var listener = listener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeOfferBannerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_offer_banner, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: HomeOfferBannerAdapter.ViewHolder, position: Int) {


        holder.bindItems(allcategory_data.get(position))
        holder.image.setOnClickListener { view ->

            listener.onOfferBannerClick(position, allcategory_data[position])
        }


    }


    override fun getItemCount(): Int {
        return allcategory_data.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val image = itemView.findViewById<AppCompatImageView>(R.id.offer_banner_img)


        @SuppressLint("ResourceAsColor")
        fun bindItems(get: Data) {


            Glide.with(image.context)
                .load(ApiConstants.IMAGE_BASE_URL + get.homeBannerImage)
                .into(image)



        }
    }

    init {
        this.context = activity
        this.allcategory_data = allcategory_data
        this.listener = listener

    }

    interface HomeOfferBannerClickListener {
        fun onOfferBannerClick(position: Int,item: Data)
    }
}
