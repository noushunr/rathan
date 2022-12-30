package com.example.rathaanelectronics.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Interface.ReasonItemClick
import com.example.rathaanelectronics.Model.OrderCancelReasons
import com.example.rathaanelectronics.R


class ReasonAdapter(
    activity: FragmentActivity?,
    reasons: List<OrderCancelReasons.Data>,
    reasonItemClick: ReasonItemClick,
    isArabic: Boolean
) :
    RecyclerView.Adapter<ReasonAdapter.ViewHolder>() {
    val Context: FragmentActivity? = activity
    val alReasons = reasons
    var checkedPosition = 0
    val reasonItemClick = reasonItemClick
    val isArabic = isArabic

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReasonAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_reasons, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ReasonAdapter.ViewHolder, position: Int) {


        holder.bindItems(alReasons, isArabic)
        if (position == checkedPosition)
            holder.rbReason.setChecked(true)
        else
            holder.rbReason.setChecked(false)
        holder.rbReason.setOnClickListener {
            checkedPosition = position
            reasonItemClick.onReasonClick(alReasons[position])
            notifyDataSetChanged()
        }
//        holder.rbReason.setOnCheckedChangeListener { compoundButton, b ->
//
//
//        }

    }


    override fun getItemCount(): Int {
        return alReasons.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


//        var oder_history_status = itemView.findViewById<ImageView>(R.id.oder_history_status)

        var rbReason = itemView.findViewById<RadioButton>(R.id.rb_reason)


        fun bindItems(reasons: List<OrderCancelReasons.Data>, isArabic: Boolean) {

            if (isArabic)
                rbReason.text = reasons[adapterPosition].returnReasonAr
            else
                rbReason.text = reasons[adapterPosition].returnReason
//            rbReason.isChecked = adapterPosition == checkedPosition
//            if (adapterPosition == checkedPosition)
//                rbReason.setChecked(true)
//            else
//                rbReason.setChecked(false)


        }
    }
}
