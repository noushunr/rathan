package com.example.rathaanelectronics.Adapter

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


class Top_deals_list_Adapter(
    activity: FragmentActivity?,
    Hotdeals_data: List<DealsModel.Datum>,
    listener: HotdealsItemClick,

    ) :
    RecyclerView.Adapter<Top_deals_list_Adapter.ViewHolder>() {
    var context = activity
    var Hotdeals_data = Hotdeals_data
    var listener = listener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Top_deals_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recy_top_deals_single_item, parent, false)

        Log.e("Hotdealadapter","done")

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Top_deals_list_Adapter.ViewHolder, position: Int) {



//        if(position %2 == 1)
//        {

//
//        }
        holder.bindItems(Hotdeals_data.get(position))



        holder.coordinatorLayout_home.setOnClickListener { view ->

            listener.onHotdealsClicked(position, Hotdeals_data[position])
        }

        holder.addToWishlistbtn.setOnClickListener{
            Hotdeals_data.get(position).productId?.let { it1 ->
                listener.onAddToWishlistButtonClick(
                    it1
                )
            }
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
        val addToWishlistbtn = itemView.findViewById<ImageView>(R.id.iv_wish_list)


        fun bindItems(get: DealsModel.Datum) {

            Log.e("image_url",ApiConstants.IMAGE_BASE_URL + get.prodFrondImg)

            Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + get.prodFrondImg)
                .into(img_top_deal)
            txt_top_deal_product_name.text = get.productName
            txt_top_deal_product_name.isSelected = true;
            txt_brandname.text = get.brandName
            txt_selling_price.text = "KD " + get.hotPrice
            txt_selling_price.setTextColor(Color.parseColor("#128400"))

            if (get?.wishlistExist  == 0){
                addToWishlistbtn.setBackgroundResource(R.drawable.fav_icon_black)
            }else{
                ll_offer_float.setBackgroundResource(R.drawable.un_fave_icon)
            }

            if (get?.prodSingleQuantity?.toInt() == 0){
                ll_offer_float.visibility = View.VISIBLE
                txt_offer_float.text="Sold Out"
                ll_offer_float.setBackgroundResource(R.drawable.bg_offer_float_soldout)
            }else{
                if (get?.topSelling?.toInt() == 1){
                    ll_offer_float.visibility = View.VISIBLE
                    txt_offer_float.text="Best Seller"
                    ll_offer_float.setBackgroundResource(R.drawable.bg_offer_float_best_seller)
                }else if (get?.productOfferStat?.toInt() == 2){
                    if (get?.productSpofferPrice?.toDouble()?.toInt()!= 0){
                        var offerPercentage = ((get?.productSellPrice?.toDouble())?.minus((get?.productSpofferPrice?.toDouble()!!)))?.div((get?.productSellPrice?.toDouble()!!))
                        var offerPrice = offerPercentage?.times(100)
                        var  roundOff = offerPrice?.times(100.0)?.let { Math.round(it) }?.div(100.0)
                        ll_offer_float.visibility = View.VISIBLE
                        if (offerPercentage != null) {
                            txt_offer_float.text= roundOff.toString() + " %"
                        }
                        ll_offer_float.setBackgroundResource(R.drawable.bg_offer_float_offer)
                    }else{
                        ll_offer_float.visibility = View.GONE
                    }

                }else{
                    ll_offer_float.visibility = View.GONE
                }
            }


        }
    }

    init {
        this.context = activity
        this.Hotdeals_data = Hotdeals_data
        this.listener = listener

    }
}
