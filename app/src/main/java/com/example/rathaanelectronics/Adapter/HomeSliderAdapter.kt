package com.example.rathaanelectronics.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.asksira.loopingviewpager.LoopingPagerAdapter
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Interface.SliderClickListener
import com.example.rathaanelectronics.Model.SliderModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants

class HomeSliderAdapter(context: Context,
                        itemList: ArrayList<String>,
                        isInfinite: Boolean,
                        listener: SliderClickListener,
                        slider_data: ArrayList<SliderModel.Datum>

) :
    LoopingPagerAdapter<String>(itemList, isInfinite) {

    var context = context
    var slider_data = slider_data
    val listener: SliderClickListener

    //This method will be triggered if the item View has not been inflated before.
    override fun inflateView(
        viewType: Int,
        container: ViewGroup,
        listPosition: Int
    ): View {

        var view =
            LayoutInflater.from(container.context).inflate(R.layout.item_pager, container, false)

        return view


    }

    override fun bindView(
        convertView: View,
        listPosition: Int,
        viewType: Int

    ) {
        val img = convertView.findViewById<View>(R.id.image) as ImageView
        Glide.with(context).load(ApiConstants.IMAGE_BASE_URL + itemList!![listPosition])
            .into(img)

        convertView.setOnClickListener {
            listener.onSliderClicked(
                listPosition,
                slider_data[listPosition]
            )
        }
    }

    init {
        this.itemList = itemList
        this.slider_data = slider_data
        this.listener = listener
        this.context = context
    }
}