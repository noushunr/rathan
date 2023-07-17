package com.example.rathaanelectronics.ui.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Interface.BestItemClick
import com.example.rathaanelectronics.Model.Product
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants


class BestDealListAdapter(
    activity: FragmentActivity?,
    BestDeal_data: List<Product>,
    listener: BestItemClick,
    isArabic: Boolean

) :
    RecyclerView.Adapter<BestDealListAdapter.ViewHolder>() {
    var context = activity
    var BestDeal_data = BestDeal_data
    var listener = listener
    var isArabic = isArabic

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BestDealListAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recy_top_deals_single_item, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BestDealListAdapter.ViewHolder, position: Int) {


        holder.bindItems(BestDeal_data.get(position), isArabic)
        holder.ll_offer_float.visibility = View.GONE

        holder.coordinatorLayout_home.setOnClickListener { view ->
            listener.onBestItemClicked(position, BestDeal_data[position])
        }

        holder.addToWishListBtn.setOnClickListener {
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
            itemView.findViewById<RelativeLayout>(R.id.coordinatorLayout_home)
        val img_top_deal = itemView.findViewById<ImageView>(R.id.img_top_deal)
        val txt_top_deal_product_name =
            itemView.findViewById<TextView>(R.id.txt_top_deal_product_name)
        val txt_brandname = itemView.findViewById<TextView>(R.id.txt_brandname)
        val txt_selling_price = itemView.findViewById<TextView>(R.id.txt_selling_price)
        val addToWishListBtn = itemView.findViewById<ImageView>(R.id.iv_wish_list)

        val txt_offer_float = itemView.findViewById<TextView>(R.id.txt_offer_float)
        val ll_offer_float = itemView.findViewById<LinearLayout>(R.id.ll_offer_float)
        val llSameDayDelivery = itemView.findViewById<LinearLayout>(R.id.ll_same_day_delivery)
        val llSoldOut = itemView.findViewById<LinearLayout>(R.id.ll_sold_out)
        fun bindItems(get: Product, isArabic: Boolean) {


            if (get.productImage != null && get.productImage?.contains(",")!!) {
                var result: List<String>? = get.productImage?.split(",")?.map { it.trim() }
                if (result?.size!! > 0) {
                    Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + result[0])
                        .into(img_top_deal)
                }

            } else {
                Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + get.productImage)
                    .into(img_top_deal)
            }


            if (isArabic)
                txt_top_deal_product_name.text = get.productNameArab
            else
                txt_top_deal_product_name.text = get.productName
//            txt_brandname.visibility = View.GONE
            txt_selling_price.text = "KD " + get.productSellPrice
            txt_selling_price.setTextColor(Color.parseColor("#128400"))
            if (get?.prodSingleQuantity?.toInt() == 0) {
                llSoldOut.visibility = View.VISIBLE
            } else {
                llSoldOut.visibility = View.GONE
                if (get?.samedayDelivery != null && get?.samedayDelivery?.equals(
                        "yes",
                        ignoreCase = true
                    )!!
                ) {
                    llSameDayDelivery.visibility = View.VISIBLE
                } else {
                    llSameDayDelivery.visibility = View.GONE
                }
            }
            if (get?.topSelling?.toInt() == 1) {
                ll_offer_float.visibility = View.VISIBLE
                txt_offer_float.text = "Best Seller"
                ll_offer_float.setBackgroundResource(R.drawable.bg_offer_float_best_seller)
            } else if (get?.productOfferStat?.toInt() == 2) {
                if (get?.productSpofferPrice?.toDouble()?.toInt() != 0) {
                    var offerPercentage =
                        ((get?.productSellPrice?.toDouble())?.minus((get?.productSpofferPrice?.toDouble()!!)))?.div(
                            (get?.productSellPrice?.toDouble()!!)
                        )
                    var offerPrice = offerPercentage?.times(100)
                    var roundOff = offerPrice?.times(100.0)?.let { Math.round(it) }?.div(100.0)
                    ll_offer_float.visibility = View.VISIBLE
                    if (offerPercentage != null) {
                        txt_offer_float.text = "${roundOff.toString() }  % ${context?.getString(R.string.offer)}"
                    }
                    ll_offer_float.setBackgroundResource(R.drawable.bg_offer_float_offer)
                } else {
                    ll_offer_float.visibility = View.GONE
                }

            } else {
                ll_offer_float.visibility = View.GONE
            }

        }
    }

    init {
        this.context = activity
        this.BestDeal_data = BestDeal_data
        this.listener = listener

    }
}
