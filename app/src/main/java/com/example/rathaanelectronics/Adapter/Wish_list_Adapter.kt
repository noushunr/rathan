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


class Wish_list_Adapter(activity: FragmentActivity?,
                        wishListData: List<WishListResponseModel.Data.Details>,
                        listener: ManageWishlistItemClick
) : RecyclerView.Adapter<Wish_list_Adapter.ViewHolder>() {
    var context = activity
    var wishListData = wishListData
    var listener = listener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Wish_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.wish_list_sing_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Wish_list_Adapter.ViewHolder, position: Int) {
       // holder.bindItems(userList[position])
        Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + wishListData.get(position).productImage)
            .into(holder.imageView)
        holder.nameTxt.setText(wishListData.get(position).productName)
        holder.priceTxt.setText("KD "+wishListData.get(position).productSellPrice)
        holder.addToCartBtn.setOnClickListener{
            // handle add to cart click event
            listener.addToCart(wishListData.get(position).productId, wishListData.get(position).productQuantity)
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

