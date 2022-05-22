package com.example.rathaanelectronics.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Interface.CategoriesTitleItemClick
import com.example.rathaanelectronics.Model.AllCategoriesModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants


class toplevel_cat_list_Adapter(
    activity: FragmentActivity?,
    allcategory_data: List<AllCategoriesModel.Datum>,
    listener: CategoriesTitleItemClick
) : RecyclerView.Adapter<toplevel_cat_list_Adapter.ViewHolder>() {

    var context = activity
    var allcategory_data = allcategory_data
    var listener = listener


    val image: IntArray = intArrayOf(

        R.drawable.top_level_cam,
        R.drawable.top_level_gadget,
        R.drawable.top_level_game,
        R.drawable.top_level_mob,
        R.drawable.top_level_tv,
        R.drawable.top_level_gadget,
        R.drawable.top_level_game,
        R.drawable.top_level_mob,

        )


    val name = arrayOf<String>(
        "Camara",
        "Gadgets",
        "Game",
        "Mobile",
        "Computer",
        "Gadgets",
        "Game",
        "Mobile"
    )


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): toplevel_cat_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.menu_singleitem, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: toplevel_cat_list_Adapter.ViewHolder, position: Int) {
         //holder.bindItems(image[position],name[position])
        holder.bindItems(allcategory_data.get(position), listener)

    }


    override fun getItemCount(): Int {
        return allcategory_data.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val flevelcat_icon = itemView.findViewById<ImageView>(R.id.flevelcat_icon)
        val flevelcat_name = itemView.findViewById<TextView>(R.id.flevelcat_name)

        fun bindItems(item: AllCategoriesModel.Datum, listener: CategoriesTitleItemClick) {
           //flevelcat_icon.setImageResource(i)
           flevelcat_name.text = item.categoryLabel
            Glide.with(flevelcat_icon).load(ApiConstants.IMAGE_BASE_URL + item.categoryIcon)
                .into(flevelcat_icon)
            itemView.rootView.setOnClickListener {
                listener.onTitleClicked(adapterPosition, item)
            }


        }
    }
}
