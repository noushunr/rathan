package com.example.rathaanelectronics.Adapter

import android.content.Context
import android.graphics.Color
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Model.NotificationModel
import com.example.rathaanelectronics.Model.WalletModel
import com.example.rathaanelectronics.R
import java.text.SimpleDateFormat
import java.util.*


class WalletAdapter(context: Context, alWallet : List<WalletModel.Details>) :
    RecyclerView.Adapter<WalletAdapter.ViewHolder>() {

    val context = context
    val alWallet = alWallet

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WalletAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_wallet, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: WalletAdapter.ViewHolder, position: Int) {


       holder.bindItems(alWallet,context)

    }


    override fun getItemCount(): Int {
        return alWallet.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var tvOrderId = itemView.findViewById<View>(R.id.tv_order_id) as TextView
        var tvDate = itemView.findViewById<View>(R.id.tv_date) as TextView
        var tvAction = itemView.findViewById<View>(R.id.tv_action) as TextView
        var tvAmount = itemView.findViewById<View>(R.id.tv_amount) as TextView



        fun bindItems(alWallet: List<WalletModel.Details>,context:Context) {


            if (alWallet[adapterPosition].action.equals("credit")){
                tvAction.setTextColor(context.resources.getColor(R.color.green))
                tvAmount.setTextColor(context.resources.getColor(R.color.green))
                tvAction.text = context.getString(R.string.credited)

                tvAmount.text = "+ ${"%.2f".format(alWallet[adapterPosition].walletAmount?.toDouble())}"
                tvOrderId.text = alWallet[adapterPosition].walletOrderId
                tvDate.text = context.getString(R.string.date,getTime(alWallet[adapterPosition].walletDate!!))
            }else {
                tvAction.setTextColor(context.resources.getColor(R.color.colorPrimary))
                tvAmount.setTextColor(context.resources.getColor(R.color.colorPrimary))
                tvAction.text = context.getString(R.string.debited)
                tvAmount.text = "- ${"%.2f".format(alWallet[adapterPosition].redeemAmount?.toDouble())}"
                tvOrderId.text = alWallet[adapterPosition].redeemOrderId
                tvDate.text = context.getString(R.string.date,getTime(alWallet[adapterPosition].redeemDate!!))
            }

        }
        fun getTime(date : String) : String{
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
