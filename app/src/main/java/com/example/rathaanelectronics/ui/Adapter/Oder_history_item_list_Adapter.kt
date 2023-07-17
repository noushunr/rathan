package com.example.rathaanelectronics.ui.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.ui.Fragment.OderCancelFragment
import com.example.rathaanelectronics.Model.OrderDetails
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants


class Oder_history_item_list_Adapter(
    activity: FragmentActivity?,
    orderDetails: List<OrderDetails>,
    isArabic: Boolean
) :
    RecyclerView.Adapter<Oder_history_item_list_Adapter.ViewHolder>() {


    val context: FragmentActivity? =activity
    val orderDetails = orderDetails

    val isArabic = isArabic

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Oder_history_item_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.oder_history_line_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Oder_history_item_list_Adapter.ViewHolder, position: Int) {


        holder.bindItems(orderDetails,context,isArabic)

        holder.btn_oder_cancellation.setOnClickListener { view->

       Log.e("lineitem","done")

            val transaction = context!!.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame, OderCancelFragment.newInstance(orderDetails[position],""))
            transaction?.addToBackStack(null)
            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction?.commit()



        }


    }


    override fun getItemCount(): Int {
        return orderDetails.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var oder_history_status = itemView.findViewById<View>(R.id.oder_history_status) as Button
        val oder_title = itemView.findViewById<TextView>(R.id.tv_item_name)
        val orderTotal = itemView.findViewById<TextView>(R.id.tv_total)
        val orderDate = itemView.findViewById<TextView>(R.id.tv_date)
        val refundLabel = itemView.findViewById<TextView>(R.id.tv_refund_label)
        val btn_oder_cancellation = itemView.findViewById<Button>(R.id.btn_oder_cancellation)
        val ivProduct = itemView.findViewById<ImageView>(R.id.iv_item)

        fun bindItems(
            orderDetails: List<OrderDetails>,
            context: FragmentActivity?,
            isArabic: Boolean
        ) {

            Glide.with(context!!).load(ApiConstants.IMAGE_BASE_URL + orderDetails[adapterPosition].dcProdImage)
                .into(ivProduct)
            if (isArabic){
                oder_title.text = orderDetails[adapterPosition].productNameArab
            }else{
                oder_title.text = orderDetails[adapterPosition].dcProdName
            }

            orderTotal.text = "KD ${orderDetails[adapterPosition].dcProdMrp}"
            orderDate.text = context.getString(R.string.date,orderDetails[adapterPosition].ordersDate)
            refundLabel.text = "${orderDetails[adapterPosition].cancellingProduct}"
            if (orderDetails[adapterPosition].ordersStatus == "1") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_yellow);
                oder_history_status.text=context.getString(R.string.pending)

            }else if (orderDetails[adapterPosition].ordersStatus == "2") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_request_withdraw);
                oder_history_status.text=context.getString(R.string.shipped)

            }else if (orderDetails[adapterPosition].ordersStatus == "3") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_green);
                oder_history_status.text=context.getString(R.string.deliverd)

            }

            if (orderDetails[adapterPosition].ordersCancelStatus == "1") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_primarycolour);
                oder_history_status.text=context.getString(R.string.cancelled)
                btn_oder_cancellation.visibility = View.GONE
            }else{
                if (orderDetails[adapterPosition].ordersStatus == "3")
                    btn_oder_cancellation.visibility = View.GONE
                else {
                    if (orderDetails[adapterPosition].cancellingProduct.equals("Cancellation available")){
                        btn_oder_cancellation.visibility = View.VISIBLE
                    }else{
                        btn_oder_cancellation.visibility = View.GONE
                    }

                }
            }


        }
    }
}
