package com.example.rathaanelectronics.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Interface.ManageWishlistItemClick
import com.example.rathaanelectronics.Model.WishListResponseModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants


class Wish_list_Adapter(
    activity: FragmentActivity?,
    wishListData: List<WishListResponseModel.Data.Details>,
    listener: ManageWishlistItemClick,
    isArabic: Boolean
) : RecyclerView.Adapter<Wish_list_Adapter.ViewHolder>() {
    var context = activity
    var wishListData = wishListData
    var listener = listener
    var isArabic = isArabic
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Wish_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.wish_list_sing_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Wish_list_Adapter.ViewHolder, position: Int) {
       // holder.bindItems(userList[position])
        var image = wishListData.get(position).productImage ?: wishListData.get(position).offersImage
        var name = ""
        if (isArabic)
            name = (wishListData.get(position).productNameArab ?: wishListData.get(position).offersBundleTitleArab)!!
        else
            name = (wishListData.get(position).productName ?: wishListData.get(position).offersBundleTitle)!!
        var price = wishListData.get(position).productSellPrice ?: wishListData.get(position).offersBundlePrice
        if (image!=null){
            if (image.contains(",")){
                image = image.split(",").toTypedArray()[0]
            }
        }
        Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + image)
            .into(holder.imageView)
        holder.nameTxt.setText(name)
        holder.priceTxt.setText("KD "+price)
        holder.addToCartBtn.setOnClickListener{
            // handle add to cart click event
            var quanity = 1
            if(wishListData.get(position).productQuantity!=null && wishListData.get(position).productQuantity.equals("0")){
                if(wishListData.get(position).productQuantity.equals("0"))
                    quanity = 1
                else
                    quanity = wishListData.get(position).productQuantity?.toInt()!!
            }
            if (wishListData.get(position).productName!=null)
                listener.addToCart(wishListData.get(position).productId, quanity.toString())
            else
                listener.addToBundleCart(wishListData[position].wishlistBundleOfferid, quanity.toString())
        }
        holder.deleteBtn.setOnClickListener{
            // handle delete events
            listener.deleteItem(wishListData.get(position).wishlistProdId)
        }
    }


    override fun getItemCount(): Int {
        return wishListData.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.imageView)
        var nameTxt = itemView.findViewById<TextView>(R.id.nameTxt)
        var priceTxt = itemView.findViewById<TextView>(R.id.priceTxt)
        var addToCartBtn = itemView.findViewById<Button>(R.id.addToCartBtn)
        var deleteBtn = itemView.findViewById<ImageView>(R.id.deleteBtn)

//
//        fun bindItems(user: Model_Details) {
//            itemView.tv_name.text=user.name
//            itemView.tv_des.text=user.des
//            itemView.iv_name.setImageResource(user.image)

        }
    }

