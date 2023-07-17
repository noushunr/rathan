package com.example.rathaanelectronics.ui.Adapter

import android.content.Context
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Interface.TimerItemClick
import com.example.rathaanelectronics.Model.Data
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class Time_deal_Adapter(
    activity: FragmentActivity?,
    alTimeOffer: List<Data>, type: Int, listener: TimerItemClick, isArabic: Boolean,) :
    RecyclerView.Adapter<Time_deal_Adapter.ViewHolder>() {

    var context = activity
    var alTimeOffer = alTimeOffer
    var type = type
    var listener = listener
    var isArabic = isArabic
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): Time_deal_Adapter.ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.time_deal_single_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: Time_deal_Adapter.ViewHolder, position: Int) {


        context?.let { holder?.bindItems(alTimeOffer.get(position), it,type,isArabic) }

        if (type == 0){
            holder.llTimer.visibility = View.VISIBLE
            holder.tvAmount.visibility = View.GONE
            holder.tvLabel.visibility = View.VISIBLE
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                val date: Date = format.parse(alTimeOffer.get(position).productSpofferEnd + " 23:59:59")
                val currentDate = Calendar.getInstance().time
                val difference  = currentDate?.time?.let { date?.time?.minus(it) }
                var seconds = difference?.div(1000)
                var minutes = seconds?.div(60)
                var hours = minutes?.div(60)
                var days = TimeUnit.HOURS.toDays(difference?.let { TimeUnit.MILLISECONDS.toDays(it) }!!)



                val countDownTimer = object : CountDownTimer(difference, 1000) {
                    override fun onTick(p0: Long) {
                        val millis: Long = p0
                        var diffInSec = TimeUnit.MILLISECONDS.toSeconds(millis)
                        seconds = diffInSec % 60
                        diffInSec /= 60
                        minutes = diffInSec % 60
                        diffInSec /= 60
                        hours = diffInSec % 24
                        diffInSec /= 24
                        days = diffInSec
//                        Log.d("CountDown", "$days days $hours Hours $minutes minutes $seconds Seconds")
                        holder?.tvDays.text = days?.toString()
                        holder?.tvHours.text = hours?.toString()
                        holder?.tvMins.text = minutes?.toString()
                        holder?.tvSecs.text = seconds?.toString()

                    }

                    override fun onFinish() {
                        /*clearing all fields and displaying countdown finished message          */
//                holder.textView_TimeCountDown?.setText("Count down completed");
                        System.out.println("Time up")
                    }
                }
                countDownTimer.start()
            }catch (e: Exception){

            }
        }else{
            holder.llTimer.visibility = View.GONE
            holder.tvAmount.visibility = View.VISIBLE
            holder.tvLabel.visibility = View.GONE
        }

        holder.coordinatorLayout_home.setOnClickListener { view ->

            listener.onHotdealsClicked(position, alTimeOffer[position])
        }

//         holder.bindItems(image[position],name[position])

    }


    override fun getItemCount(): Int {
        return alTimeOffer?.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvDays = itemView.findViewById<TextView>(R.id.tv_days)
        val tvHours = itemView.findViewById<TextView>(R.id.tv_hours)
        val tvMins = itemView.findViewById<TextView>(R.id.tv_mins)
        val tvSecs = itemView.findViewById<TextView>(R.id.tv_seconds)
        val llOfferStat = itemView?.findViewById<LinearLayout>(R.id.ll_offer_stat)
        val llOfferAmount = itemView?.findViewById<LinearLayout>(R.id.ll_offer_amount)
        val tvOfferAmount = itemView?.findViewById<TextView>(R.id.tv_offer)
        val tvOfferPercentage = itemView?.findViewById<TextView>(R.id.tv_offer_percentage)
        var llTimer = itemView?.findViewById<LinearLayout>(R.id.ll_timer)
        val tvLabel = itemView?.findViewById<TextView>(R.id.tv_label)
        val tvAmount = itemView?.findViewById<TextView>(R.id.tv_amount)
        val coordinatorLayout_home = itemView.findViewById<CoordinatorLayout>(R.id.coordinatorLayout_home)

        val tvProductName =
                itemView.findViewById<TextView>(R.id.top_deal_name)
        val ivProduct =
                itemView.findViewById<ImageView>(R.id.iv_product)



//        val flevelcat_icon = itemView.findViewById<ImageView>(R.id.flevelcat_icon)
//        val flevelcat_name = itemView.findViewById<TextView>(R.id.flevelcat_name)

        fun bindItems(data: Data, context: Context, type: Int, isArabic: Boolean) {
            Glide.with(context).load(ApiConstants.IMAGE_BASE_URL + data.prodFrondImg)
                    .into(ivProduct)
            if (isArabic)
                tvProductName?.text = data?.productNameArab
            else
                tvProductName?.text = data?.productName
            if (data?.productSpofferPrice?.toDouble()?.toInt() !=0) {
                var amount = data?.productSpofferPrice?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
                tvAmount.text ="KD $amount"
            }else{
                var amount = data?.productSellPrice?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
                tvAmount.text = "KD $amount"
            }
            if (data?.productSingleQuantity?.toInt() == 0){
                llOfferStat.visibility = View.VISIBLE
                llOfferAmount.visibility = View.GONE
                tvOfferPercentage.text=context.getString(R.string.sold_out)
                llOfferStat.setBackgroundResource(R.drawable.bg_offer_float_soldout)
            }else{

                if (data?.productSpofferPrice?.toDouble()?.toInt() !=0){
                    var offerPercentage = ((data?.productSellPrice?.toDouble())?.minus((data?.productSpofferPrice?.toDouble()!!)))?.div((data?.productSellPrice?.toDouble()!!))
                    var offerPrice = offerPercentage?.times(100)
                    var  roundOff = offerPrice?.times(100.0)?.let { Math.round(it) }?.div(100.0)
                    llOfferStat.visibility = View.VISIBLE
                    if (offerPercentage != null) {
                        tvOfferPercentage.text= "${roundOff.toString() }% ${context.getString(R.string.offer)}"
                    }
                    llOfferStat.setBackgroundResource(R.drawable.bg_offer_float_offer)
                    if(type == 0){
                        llOfferAmount.visibility = View.VISIBLE
                    }else{
                        llOfferAmount.visibility = View.GONE
                    }

                    var offerDifference = ((data?.productSellPrice?.toDouble())?.minus((data?.productSpofferPrice?.toDouble()!!)))
                    var  roundOffAmount = offerDifference?.times(100.0)?.let { Math.round(it) }?.div(100.0)
                    tvOfferAmount.text= "Save \n KD $roundOffAmount"
                }else{
                    llOfferStat.visibility = View.GONE
                    llOfferAmount.visibility = View.GONE
                }

            }
//           flevelcat_icon.setImageResource(i)
//           flevelcat_name.text = s



        }
    }

    init {
        this.context = activity
        this.alTimeOffer = alTimeOffer
        this.type = type
        this.listener = listener
    }
}
