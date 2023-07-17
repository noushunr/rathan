package com.example.rathaanelectronics.ui.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Interface.PickUpStoreClickListener
import com.example.rathaanelectronics.Model.PickUpStoreModel
import com.example.rathaanelectronics.R


class PickUpShop_list_Adapter(
    activity: FragmentActivity?,
    PickUpStore_data: List<PickUpStoreModel.Detail>,
    listener: PickUpStoreClickListener,
    isArabic: Boolean,

    ) :
    RecyclerView.Adapter<PickUpShop_list_Adapter.ViewHolder>() {
    var context = activity
    var PickUpStore_data = PickUpStore_data
    var listener = listener
    var lastCheckedLayout: LinearLayout? = null
    var isArabic = isArabic

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PickUpShop_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.pickup_list_single_item, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: PickUpShop_list_Adapter.ViewHolder, position: Int) {


        holder.bindItems(PickUpStore_data.get(position),isArabic)
        holder.imageLayout.setOnClickListener{
            lastCheckedLayout?.setBackgroundResource(R.drawable.radio_icon)
            lastCheckedLayout = holder.imageLayout
            holder.imageLayout.setBackgroundResource( R.drawable.green_tick)
        }


        holder.ll_pickupstore.setOnClickListener { view ->
            listener.onPickStoreClicked(position, PickUpStore_data.get(position))
        }


    }


    override fun getItemCount(): Int {
        return PickUpStore_data.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ll_pickupstore =
            itemView.findViewById<RelativeLayout>(R.id.ll_pickupstore)

        val txt_store_name =
            itemView.findViewById<TextView>(R.id.txt_store_name)
        val txt_store_add = itemView.findViewById<TextView>(R.id.txt_store_add)
        val imageLayout = itemView.findViewById<LinearLayout>(R.id.imageLayout)


        fun bindItems(get: PickUpStoreModel.Detail, isArabic: Boolean) {

            if (isArabic){
                txt_store_name.text = get.storeNameAr
                txt_store_add.text = get.storeAddressAr
            }else{
                txt_store_name.text = get.storeName
                txt_store_add.text = get.storeAddress
            }



        }
    }

    init {
        this.context = activity
        this.PickUpStore_data = PickUpStore_data
        this.listener = listener

    }
}
