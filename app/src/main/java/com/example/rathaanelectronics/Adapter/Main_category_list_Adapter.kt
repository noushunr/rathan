package com.example.rathaanelectronics.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Fragment.Product_Detail_view_Fragment
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants


class Main_category_list_Adapter(
    activity: FragmentActivity?,
    Hotdeals_data: List<DealsModel.Datum>,
    listener: HotdealsItemClick,

    ) :
    RecyclerView.Adapter<Main_category_list_Adapter.ViewHolder>() {
    var context = activity
    var Hotdeals_data = Hotdeals_data
    var listener = listener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Main_category_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recy_top_deals_single_item, parent, false)

        Log.e("Hotdealadapter","done")

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Main_category_list_Adapter.ViewHolder, position: Int) {



        if(position %2 == 1)
        {
            holder.txt_offer_float.text="Best Seller"
            holder.ll_offer_float.setBackgroundResource(R.drawable.bg_offer_float_best_seller);

        }
        holder.bindItems(Hotdeals_data.get(position))



        holder.coordinatorLayout_home.setOnClickListener { view ->

            listener.onHotdealsClicked(position, Hotdeals_data[position])
        }


    }


    override fun getItemCount(): Int {
        return Hotdeals_data.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val coordinatorLayout_home =
            itemView.findViewById<CoordinatorLayout>(R.id.coordinatorLayout_home)
        val img_top_deal = itemView.findViewById<ImageView>(R.id.img_top_deal)
        val txt_top_deal_product_name =
            itemView.findViewById<TextView>(R.id.txt_top_deal_product_name)
        val txt_brandname = itemView.findViewById<TextView>(R.id.txt_brandname)
        val txt_selling_price = itemView.findViewById<TextView>(R.id.txt_selling_price)

        val txt_offer_float = itemView.findViewById<TextView>(R.id.txt_offer_float)
        val ll_offer_float = itemView.findViewById<LinearLayout>(R.id.ll_offer_float)


        @SuppressLint("ResourceAsColor")
        fun bindItems(get: DealsModel.Datum) {

            Log.e("image_url",ApiConstants.IMAGE_BASE_URL + get.prodFrondImg)

            Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + get.prodFrondImg)
                .into(img_top_deal)
            txt_top_deal_product_name.text = get.productName
            txt_top_deal_product_name.isSelected = true;
            txt_brandname.text = get.brandName
            txt_selling_price.text = "KD " + get.productSellPrice
            txt_selling_price.setTextColor(Color.RED);


        }
    }

    init {
        this.context = activity
        this.Hotdeals_data = Hotdeals_data
        this.listener = listener

    }
}
