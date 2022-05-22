package com.example.rathaanelectronics.Adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Fragment.OderCancelFragment
import com.example.rathaanelectronics.R


class Oder_history_item_list_Adapter(activity: FragmentActivity?) :
    RecyclerView.Adapter<Oder_history_item_list_Adapter.ViewHolder>() {


    val Context: FragmentActivity? =activity

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
    ): Oder_history_item_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.oder_history_line_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Oder_history_item_list_Adapter.ViewHolder, position: Int) {


        holder.bindItems(name[position], status[position])

        holder.btn_oder_cancellation.setOnClickListener { view->

       Log.e("lineitem","done")

            val transaction = Context!!.supportFragmentManager?.beginTransaction()
            transaction?.replace(R.id.frame, OderCancelFragment())
            transaction?.addToBackStack(null)
            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction?.commit()



        }


    }


    override fun getItemCount(): Int {
        return status.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var oder_history_status = itemView.findViewById<View>(R.id.oder_history_status) as Button
        val oder_title = itemView.findViewById<TextView>(R.id.oder_history_name)
        val btn_oder_cancellation = itemView.findViewById<Button>(R.id.btn_oder_cancellation)


        fun bindItems(name: String, status: Int) {

            oder_history_status

            oder_title.text = name
            if (status == 1) {

                oder_history_status.setBackgroundResource(R.drawable.bg_buttom_yellow);
                oder_history_status.text="Pending"

            }



        }
    }
}
