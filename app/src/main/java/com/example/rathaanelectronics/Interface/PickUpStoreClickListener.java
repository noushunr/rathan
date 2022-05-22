package com.example.rathaanelectronics.Interface;

/*
 *Created by Adithya T Raj on 15-12-2020
 */


import com.example.rathaanelectronics.Model.PickUpStoreModel;
import com.example.rathaanelectronics.Model.SliderModel;

public interface PickUpStoreClickListener {

    public void onPickStoreClicked(int position, PickUpStoreModel.Detail item);

}
