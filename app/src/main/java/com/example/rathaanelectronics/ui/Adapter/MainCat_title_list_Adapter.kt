package com.example.rathaanelectronics.ui.Adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentActivity

import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.ui.Fragment.MainCategoryFragment
import com.example.rathaanelectronics.Interface.CategoriesTitleItemClick
import com.example.rathaanelectronics.Model.AllCategoriesModel
import com.example.rathaanelectronics.R


class MainCat_title_list_Adapter(
    activity: FragmentActivity?,
    allcategory_data: List<AllCategoriesModel.Datum>,
    listener: CategoriesTitleItemClick,

    ) :
    RecyclerView.Adapter<MainCat_title_list_Adapter.ViewHolder>() {
    var context = activity
    var allcategory_data = allcategory_data
    var listener = listener


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainCat_title_list_Adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_cat_title_single_item, parent, false)



        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: MainCat_title_list_Adapter.ViewHolder, position: Int) {


        holder.bindItems(allcategory_data.get(position))
        holder.ll_cat_title.setOnClickListener { view ->

            listener.onTitleClicked(position, allcategory_data[position])
        }


    }


    override fun getItemCount(): Int {
        return allcategory_data.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        val txt_title = itemView.findViewById<TextView>(R.id.txt_title)
        val ll_cat_title = itemView.findViewById<LinearLayout>(R.id.ll_cat_title)


        @SuppressLint("ResourceAsColor")
        fun bindItems(get: AllCategoriesModel.Datum) {

            if (get.isCategorySelected){
                txt_title.setTextColor(Color.BLACK)
            }else{
                txt_title.setTextColor(Color.RED)
            }

            txt_title.text = get.categoryLabel



        }
    }

    init {
        this.context = activity
        this.allcategory_data = allcategory_data
        this.listener = listener

    }
}
