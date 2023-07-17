package com.example.rathaanelectronics.Utils

import android.os.Build
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.rathaanelectronics.R

 public class Globalmetheds {

   public fun setStatusbar(context: AppCompatActivity) {
        if (Build.VERSION.SDK_INT >= 21) {
            val window = context.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.statusBarColor = context.resources.getColor(R.color.colorPrimaryDark)
        }
    }
}