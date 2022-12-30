package com.example.rathaanelectronics.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Fragment.OderCancelFragment
import com.example.rathaanelectronics.Fragment.Oder_History_items_Fragment
import com.example.rathaanelectronics.Model.Order
import com.example.rathaanelectronics.R


class Oder_history_list_Adapter(activity: FragmentActivity?,orderList : List<Order>) :
    RecyclerView.Adapter<Oder_history_list_Adapter.ViewHolder>() {
    val context: FragmentActivity? = activity
    val alOrders = orderList


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Oder_history_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.oder_history_list_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Oder_history_list_Adapter.ViewHolder, position: Int) {


        holder.bindItems(alOrders, context!!)

        holder.ll_oder_history.setOnClickListener { view->


            val transaction = context!!.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame, Oder_History_items_Fragment.newInstance(alOrders[position].ordersUniqId,""))
            transaction?.addToBackStack(null)
            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction?.commit()
        }
    }


    override fun getItemCount(): Int {
        return alOrders.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var oder_history_status = itemView.findViewById<View>(R.id.oder_history_status) as Button
        val oder_title = itemView.findViewById<TextView>(R.id.oder_history_name)
        val orderTotal = itemView.findViewById<TextView>(R.id.tv_total)
        val orderDate = itemView.findViewById<TextView>(R.id.tv_date)
        val ll_oder_history = itemView.findViewById<CardView>(R.id.ll_oder_history)


        fun bindItems(orders: List<Order>,context: FragmentActivity) {
            var totalAmount = orders[adapterPosition].ordersTotalAmount?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
            var deliveryCharge = orders[adapterPosition].deliveryCharge?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
            var sameDayeCharge = orders[adapterPosition].ordersSameDelcharge?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
//            var walletApplied = orders[adapterPosition].walletapplied?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
//            var loyaltyApplied = orders[adapterPosition].loyaltyapplied?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
            totalAmount = totalAmount!! + deliveryCharge!! + sameDayeCharge!!
            oder_title.text = context?.getString(R.string.order_id,orders[adapterPosition].ordersId)
            orderTotal.text = context?.getString(R.string.total_label) + " KD $totalAmount"
            orderDate.text =context?.getString(R.string.date,orders[adapterPosition].ordersDate)
            if (orders[adapterPosition].ordersCancelStatus == "1") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_primarycolour);
                oder_history_status.text= context?.getString(R.string.cancelled)

            } else if (orders[adapterPosition].ordersStatus == "1") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_yellow);
                oder_history_status.text=context?.getString(R.string.pending)

            }else if (orders[adapterPosition].ordersStatus == "2") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_request_withdraw);
                oder_history_status.text=context?.getString(R.string.shipped)

            }else if (orders[adapterPosition].ordersStatus == "3") {
                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_green);
                oder_history_status.text=context?.getString(R.string.deliverd)

            }





        }
    }
}
