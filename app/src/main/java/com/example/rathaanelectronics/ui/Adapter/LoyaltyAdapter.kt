package com.example.rathaanelectronics.ui.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Model.LoyaltyModel
import com.example.rathaanelectronics.R
import java.text.SimpleDateFormat


class LoyaltyAdapter(context: Context, alWallet: List<LoyaltyModel.Details>, isArabic: Boolean) :
    RecyclerView.Adapter<LoyaltyAdapter.ViewHolder>() {

    val context = context
    val alWallet = alWallet
    val isArabic = isArabic

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoyaltyAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_loyalty, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LoyaltyAdapter.ViewHolder, position: Int) {


        holder.bindItems(alWallet, context, isArabic)

    }


    override fun getItemCount(): Int {
        return alWallet.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var tvProductName = itemView.findViewById<View>(R.id.tv_product_name) as TextView
        var tvAmount = itemView.findViewById<View>(R.id.tv_amount) as TextView
        var tvOrderId = itemView.findViewById<View>(R.id.tv_order_id) as TextView
        var tvDate = itemView.findViewById<View>(R.id.tv_date) as TextView
        var tvAction = itemView.findViewById<View>(R.id.tv_action) as TextView


        fun bindItems(alWallet: List<LoyaltyModel.Details>, context: Context, isArabic: Boolean) {


            if (alWallet[adapterPosition].action.equals("credit")) {
                tvAction.text = context.getString(R.string.credited)
                tvAmount.setTextColor(context.resources.getColor(R.color.green))
                tvAction.setTextColor(context.resources.getColor(R.color.green))
                tvAmount.text =
                    "+ ${"%.2f".format(alWallet[adapterPosition].productLoyalty?.toDouble())}"
                if (isArabic)
                    tvProductName.text = alWallet[adapterPosition].productNameAr
                else
                    tvProductName.text = alWallet[adapterPosition].productName
                tvDate.text = context.getString(
                    R.string.date,
                    getTime(alWallet[adapterPosition].creditDate!!)
                )
            } else {
                tvAmount.setTextColor(context.resources.getColor(R.color.colorPrimary))
                tvAction.setTextColor(context.resources.getColor(R.color.colorPrimary))
                tvAction.text = context.getString(R.string.debited)
                tvAmount.text =
                    "- ${"%.2f".format(alWallet[adapterPosition].redeemAmount?.toDouble())}"
                tvProductName.text = alWallet[adapterPosition].redeemOrderId
                tvDate.text = context.getString(
                    R.string.date,
                    getTime(alWallet[adapterPosition].redeemDate!!)
                )
            }

        }

        fun getTime(date: String): String {
            val sdf = SimpleDateFormat("yyyy-mm-dd HH:mm:ss")
            val sdf1 = SimpleDateFormat("dd-MMM-yyyy")
            try {
                val time: Long = sdf.parse(date).getTime()
                val now = System.currentTimeMillis()
                val ago = sdf1.format(time)
                return ago.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                return date
            }
        }
    }
}
