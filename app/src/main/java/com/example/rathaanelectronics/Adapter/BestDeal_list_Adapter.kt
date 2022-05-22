package com.example.rathaanelectronics.Adapter

import android.graphics.Color
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
import com.example.rathaanelectronics.Interface.BestItemClick
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Interface.NewArrivalItemClick
import com.example.rathaanelectronics.Model.BestDealModel
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.NewArrivalModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants


class BestDeal_list_Adapter(
    activity: FragmentActivity?,
    BestDeal_data: List<BestDealModel.Datum>,
    listener: BestItemClick,

    ) :
    RecyclerView.Adapter<BestDeal_list_Adapter.ViewHolder>() {
    var context = activity
    var BestDeal_data = BestDeal_data
    var listener = listener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestDeal_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recy_top_deals_single_item, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BestDeal_list_Adapter.ViewHolder, position: Int) {


        holder.bindItems(BestDeal_data.get(position))
        holder.ll_offer_float.visibility = View.GONE

        holder.coordinatorLayout_home.setOnClickListener { view ->
            listener.onBestItemClicked(position, BestDeal_data[position])
        }

        holder.addToWishListBtn.setOnClickListener{
            BestDeal_data.get(position).productId?.let { it1 ->
                listener.onAddToWishListButtonClicked(
                    it1
                )
            }
        }


    }


    override fun getItemCount(): Int {
        return BestDeal_data.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val coordinatorLayout_home =
            itemView.findViewById<CoordinatorLayout>(R.id.coordinatorLayout_home)
        val img_top_deal = itemView.findViewById<ImageView>(R.id.img_top_deal)
        val txt_top_deal_product_name =
            itemView.findViewById<TextView>(R.id.txt_top_deal_product_name)
        val txt_brandname = itemView.findViewById<TextView>(R.id.txt_brandname)
        val txt_selling_price = itemView.findViewById<TextView>(R.id.txt_selling_price)
        val addToWishListBtn = itemView.findViewById<ImageView>(R.id.iv_wish_list)

        val txt_offer_float = itemView.findViewById<TextView>(R.id.txt_offer_float)
        val ll_offer_float = itemView.findViewById<LinearLayout>(R.id.ll_offer_float)
        fun bindItems(get: BestDealModel.Datum) {


            if (get.productImage!=null && get.productImage?.contains(",")!!){
                var result: List<String>? = get.productImage?.split(",")?.map { it.trim() }
                if (result?.size!! >0){
                    Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + result[0])
                        .into(img_top_deal)
                }

            }else{
                Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + get.productImage)
                    .into(img_top_deal)
            }


            txt_top_deal_product_name.text = get.productName
//            txt_brandname.visibility = View.GONE
            txt_selling_price.text = "KD " + get.productSellPrice
            txt_selling_price.setTextColor(Color.parseColor("#128400"))


        }
    }

    init {
        this.context = activity
        this.BestDeal_data = BestDeal_data
        this.listener = listener

    }
}
