package com.example.rathaanelectronics.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Interface.AddressItemClick
import com.example.rathaanelectronics.Model.AddressResponseModel
import com.example.rathaanelectronics.R


class Address_list_Adapter(
    activity: FragmentActivity?,
    addressData: List<AddressResponseModel.Details>,
    listener: AddressItemClick
) : RecyclerView.Adapter<Address_list_Adapter.ViewHolder>() {
    var context = activity
    var addressData = addressData
    var listener = listener


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Address_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adress_list_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Address_list_Adapter.ViewHolder, position: Int) {
       // holder.bindItems(userList[position])

        holder.username.text = addressData.get(position).addressFname +" "+ addressData.get(position).addressLname
        holder.addresstxt.text = addressData.get(position).addressTitle+", "+
                addressData.get(position).address_houseBuilding+", "+
                addressData.get(position).addressStreet + ", "+
                addressData.get(position).addressCity
        holder.addressDetailtxt.text = addressData.get(position).addressBlock+", "+
                addressData.get(position).addressAvanue+", "+
                addressData.get(position).addressGovernarate
        holder.mobileTxt.text = "Mob: "+ addressData.get(position).addressMobile1
        holder.emailTxt.text = "E-mail: "+ addressData.get(position).addressMail
        holder.editBtn.setOnClickListener{
            listener.editAddressItem(addressData.get(position))
        }
        holder.deleteBtn.setOnClickListener{
            addressData.get(position).addressId?.let { it1 -> listener.deleteAddressItem(it1) }
        }

    }


    override fun getItemCount(): Int {
        return addressData.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var username = itemView.findViewById<TextView>(R.id.usernameTxt)
        var addresstxt = itemView.findViewById<TextView>(R.id.addresstxt)
        var addressDetailtxt = itemView.findViewById<TextView>(R.id.addressDetailtxt)
        var mobileTxt = itemView.findViewById<TextView>(R.id.mobileTxt)
        var emailTxt = itemView.findViewById<TextView>(R.id.emailTxt)
        var editBtn = itemView.findViewById<ImageView>(R.id.editBtn)
        var deleteBtn = itemView.findViewById<ImageView>(R.id.deleteBtn)
//
//        fun bindItems(user: Model_Details) {
//            itemView.tv_name.text=user.name
//            itemView.tv_des.text=user.des
//            itemView.iv_name.setImageResource(user.image)

        }
    }

