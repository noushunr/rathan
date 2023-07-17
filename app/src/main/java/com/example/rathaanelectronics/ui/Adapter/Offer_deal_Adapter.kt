package com.example.rathaanelectronics.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.R


class Offer_deal_Adapter(activity: FragmentActivity?) : RecyclerView.Adapter<Offer_deal_Adapter.ViewHolder>() {





    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Offer_deal_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.offer_deal_single_item, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Offer_deal_Adapter.ViewHolder, position: Int) {
//         holder.bindItems(image[position],name[position])

    }


    override fun getItemCount(): Int {
        return 5
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        val flevelcat_icon = itemView.findViewById<ImageView>(R.id.flevelcat_icon)
//        val flevelcat_name = itemView.findViewById<TextView>(R.id.flevelcat_name)

        fun bindItems(i: Int, s: String,) {
//           flevelcat_icon.setImageResource(i)
//           flevelcat_name.text = s


        }
    }
}
