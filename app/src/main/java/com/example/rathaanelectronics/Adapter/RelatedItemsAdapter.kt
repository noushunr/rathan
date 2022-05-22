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
import androidx.core.view.isGone
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Interface.RelatedItemClick
import com.example.rathaanelectronics.Model.ProductDetailsModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants


class RelatedItemsAdapter(
    activity: FragmentActivity?,
    relatedProductData: List<ProductDetailsModel.RelatedProducts>,
    listener: RelatedItemClick,

    ) :
    RecyclerView.Adapter<RelatedItemsAdapter.ViewHolder>() {
    var context = activity
    var relatedProductData = relatedProductData
    var listener = listener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RelatedItemsAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recy_top_deals_single_item, parent, false)

        Log.e("Hotdealadapter","done")

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RelatedItemsAdapter.ViewHolder, position: Int) {

        holder.bindItems(relatedProductData.get(position))

        holder.addToWishlistbtn.setOnClickListener{
            relatedProductData.get(position).productId?.let { it1 ->
                listener.onAddToWishListButtonClicked(
                    it1
                )
            }
        }


    }


    override fun getItemCount(): Int {
        return relatedProductData.size
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


        fun bindItems(get: ProductDetailsModel.RelatedProducts) {

            Log.e("image_url",ApiConstants.IMAGE_BASE_URL + get.prodFrondImg)

            Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + get.prodFrondImg)
                .into(img_top_deal)
            txt_top_deal_product_name.text = get.productName
            //txt_top_deal_product_name.isSelected = true;
            txt_brandname.text = get.brandName
            txt_selling_price.text = "KD " + get.productSellPrice
            txt_selling_price.setTextColor(Color.parseColor("#128400"))
            ll_offer_float.isGone = true
            txt_offer_float.isGone = true
            if (get?.wishlistExist  == 0){
                addToWishlistbtn.setBackgroundResource(R.drawable.fav_icon_black)
            }else{
                ll_offer_float.setBackgroundResource(R.drawable.un_fave_icon)
            }


        }
    }

    init {
        this.context = activity
        this.relatedProductData = relatedProductData
        this.listener = listener

    }
}
