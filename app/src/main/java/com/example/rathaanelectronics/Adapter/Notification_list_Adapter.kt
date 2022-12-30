package com.example.rathaanelectronics.Adapter

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
import com.example.rathaanelectronics.R
import java.text.SimpleDateFormat
import java.util.*


class Notification_list_Adapter(activity: FragmentActivity?,alNotification : List<NotificationModel.Data>) :
    RecyclerView.Adapter<Notification_list_Adapter.ViewHolder>() {

    val alNotification = alNotification

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Notification_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_list_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Notification_list_Adapter.ViewHolder, position: Int) {


       holder.bindItems(alNotification)

        if(position %2 == 0)
        {
            holder.ll_notification.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
        else
        {
            holder.ll_notification.setBackgroundColor(Color.parseColor("#EEEEEE"));

        }

    }


    override fun getItemCount(): Int {
        return alNotification.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var tvNotification = itemView.findViewById<View>(R.id.txt_notification) as TextView
        var tvDate = itemView.findViewById<View>(R.id.txt_notification_date) as TextView
        var ll_notification = itemView.findViewById<View>(R.id.ll_notification) as LinearLayout




        fun bindItems(alNotification: List<NotificationModel.Data>) {
            tvNotification.text = alNotification[adapterPosition].message
            tvDate.text = getTime(alNotification[adapterPosition].createdAt!!)

        }
        fun getTime(date : String) : String{
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                val time: Long = sdf.parse(date).getTime()
                val now = System.currentTimeMillis()
                val ago = DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)
                return ago.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                return date
            }
        }
    }
}
