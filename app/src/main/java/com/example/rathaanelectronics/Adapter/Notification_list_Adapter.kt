package com.example.rathaanelectronics.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.R


class Notification_list_Adapter(activity: FragmentActivity?) :
    RecyclerView.Adapter<Notification_list_Adapter.ViewHolder>() {

    val name = arrayOf<String>(
        "Oder id BM106126",
        "Oder id BM106127",
        "Oder id BM106128"

    )
    val status: IntArray = intArrayOf(

        1,
        0,
        1,
    )


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Notification_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.notification_list_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Notification_list_Adapter.ViewHolder, position: Int) {


       // holder.bindItems(name[position], status[position])

        if(position %2 == 1)
        {
            holder.ll_notification.setBackgroundColor(Color.parseColor("#FFFFFF"));

        }
        else
        {
            holder.ll_notification.setBackgroundColor(Color.parseColor("#EEEEEE"));

        }


    }


    override fun getItemCount(): Int {
        return 4
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var txt_notification = itemView.findViewById<View>(R.id.txt_notification) as TextView
        var txt_notification_date = itemView.findViewById<View>(R.id.txt_notification_date) as TextView
        var ll_notification = itemView.findViewById<View>(R.id.ll_notification) as LinearLayout




        fun bindItems(name: String, status: Int) {






        }
    }
}
