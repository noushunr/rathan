package com.example.rathaanelectronics.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Interface.ManageCartItem
import com.example.rathaanelectronics.Model.BestDealModel
import com.example.rathaanelectronics.Model.ShowCartResponseModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants


class Cart_list_Adapter(activity: FragmentActivity?,
                        cartData: List<ShowCartResponseModel.Data.CartItems>,
                        listener: ManageCartItem
) : RecyclerView.Adapter<Cart_list_Adapter.ViewHolder>() {
    var context = activity
    var cartData = cartData
    var listener = listener



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Cart_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_list_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Cart_list_Adapter.ViewHolder, position: Int) {
       // holder.bindItems(userList[position])
        Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + cartData.get(position).productImage)
            .into(holder.imageView)
        holder.nameTxtView.setText(cartData.get(position).productName)
        holder.priceTxtView.setText("KD " + cartData.get(position).productSellPrice)
        holder.quantityTxtView.setText(cartData.get(position).cartQuantity)
        holder.deleteBtn.setOnClickListener{
            listener.onDeleteCartItem(cartData.get(position).cartId)
        }
        holder.incrementBtn.setOnClickListener{
            var quantity = Integer.parseInt(cartData.get(position).cartQuantity)+1
            listener.onUpdateCartQuantity(cartData.get(position).cartId, quantity.toString())
        }
        holder.decrementBtn.setOnClickListener{
            var quantity = Integer.parseInt(cartData.get(position).cartQuantity) - 1
            listener.onUpdateCartQuantity(cartData.get(position).cartId, quantity.toString())
        }

    }


    override fun getItemCount(): Int {
        return cartData.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.product_image)
        var nameTxtView = itemView.findViewById<TextView>(R.id.product_name)
        var priceTxtView = itemView.findViewById<TextView>(R.id.product_Price)
        var quantityTxtView = itemView.findViewById<TextView>(R.id.product_qty)
        var incrementBtn = itemView.findViewById<TextView>(R.id.cart_increment)
        var decrementBtn = itemView.findViewById<TextView>(R.id.cart_decrement)
        var deleteBtn = itemView.findViewById<ImageView>(R.id.deleteBtn)

//        fun bindItems(user: Model_Details) {
//            itemView.tv_name.text = user.name
//            itemView.tv_des.text = user.des
//            itemView.iv_name.setImageResource(user.image)
//        }

        }
    init {
        this.context = activity
        this.cartData = cartData
    }
    }

