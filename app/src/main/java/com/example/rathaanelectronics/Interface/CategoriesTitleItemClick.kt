package com.example.rathaanelectronics.Interface

import com.example.rathaanelectronics.Model.AllCategoriesModel
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.SliderModel

interface CategoriesTitleItemClick {

    fun onTitleClicked(position: Int, item: AllCategoriesModel.Datum)

}