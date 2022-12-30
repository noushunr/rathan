package com.example.rathaanelectronics.Adapter

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
import com.example.rathaanelectronics.Interface.BundleItemClick
import com.example.rathaanelectronics.Model.BundleProductModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class BundleOfferAdapter(
    activity: FragmentActivity?,
    alBundleOffer: List<BundleProductModel.Data>, listener: BundleItemClick,
    isArabic: Boolean
) :
    RecyclerView.Adapter<BundleOfferAdapter.ViewHolder>() {

    var context = activity
    var alBundleOffer = alBundleOffer
    var listener = listener
    var isArabic = isArabic
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): BundleOfferAdapter.ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.iteam_deals_offer, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: BundleOfferAdapter.ViewHolder, position: Int) {


        context?.let { holder?.bindItems(alBundleOffer.get(position), it,isArabic) }

        if (alBundleOffer.get(position).offers_hastimer.equals("yes")) {
            holder.llTimer.visibility = View.VISIBLE
            holder.tvLabel.visibility = View.VISIBLE
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                val date: Date = format.parse(alBundleOffer.get(position).offers_to + " 23:59:59")
                val currentDate = Calendar.getInstance().time
                val difference = currentDate?.time?.let { date?.time?.minus(it) }
                var seconds = difference?.div(1000)
                var minutes = seconds?.div(60)
                var hours = minutes?.div(60)
                var days =
                    TimeUnit.HOURS.toDays(difference?.let { TimeUnit.MILLISECONDS.toDays(it) }!!)


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
            } catch (e: Exception) {

            }
        }else{
            holder.llTimer.visibility = View.GONE
            holder.tvLabel.visibility = View.GONE
        }

        holder.coordinatorLayout_home.setOnClickListener { view ->

            listener.onItemClicked(position, alBundleOffer[position])
        }
        holder.addToWishlistbtn.setOnClickListener { view ->

            listener.onAddToWishlistButtonClick(alBundleOffer[position].offers_id!!)
        }
        holder.addToWishlistbtnActive.setOnClickListener { view ->

            listener.onDeleteFromWishListButtonClick(alBundleOffer[position].offers_id!!)
        }

//         holder.bindItems(image[position],name[position])

    }


    override fun getItemCount(): Int {
        return alBundleOffer?.size
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
        val tvAmountOffer = itemView?.findViewById<TextView>(R.id.tv_amount_offer)
        val coordinatorLayout_home = itemView.findViewById<CoordinatorLayout>(R.id.coordinatorLayout_home)

        val addToWishlistbtn = itemView.findViewById<ImageView>(R.id.iv_wish_list)
        val addToWishlistbtnActive = itemView.findViewById<ImageView>(R.id.iv_wish_list_active)

        val tvProductName =
                itemView.findViewById<TextView>(R.id.top_deal_name)
        val ivProduct =
                itemView.findViewById<ImageView>(R.id.iv_product)



//        val flevelcat_icon = itemView.findViewById<ImageView>(R.id.flevelcat_icon)
//        val flevelcat_name = itemView.findViewById<TextView>(R.id.flevelcat_name)

        fun bindItems(data: BundleProductModel.Data, context: Context, isArabic: Boolean) {
            Glide.with(context).load(ApiConstants.IMAGE_BASE_URL + data.offers_image)
                    .into(ivProduct)
            if (isArabic){
                tvProductName?.text = data?.offers_text1_ar
            }else {
                tvProductName?.text = data?.offers_text1
            }
            tvAmount.visibility = View.GONE
            var amount = data?.offers_bundle_price?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
            tvAmountOffer.text = "KD $amount"

            if (data?.wishlist_exist  == 0){
                addToWishlistbtn.visibility = View.VISIBLE
                addToWishlistbtnActive.visibility = View.GONE
            }else{
                addToWishlistbtn.visibility = View.GONE
                addToWishlistbtnActive.visibility = View.VISIBLE
            }
        }
    }

    init {
        this.context = activity
        this.alBundleOffer = alBundleOffer
        this.listener = listener
    }
}
