package com.example.rathaanelectronics.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants


class ImageAdapter(
    activity: FragmentActivity?,
    images: List<String>

    ) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {
    var context = activity
    var images = images


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ImageAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)


        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {

        holder.bindItems(images.get(position))

    }


    override fun getItemCount(): Int {
        return images.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivProduct = itemView.findViewById<ImageView>(R.id.imageView)
        fun bindItems(images: String) {

            Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + images)
                .into(ivProduct)



        }
    }

    init {
        this.context = activity
        this.images = images
    }
}
