package com.example.rathaanelectronics.Activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.lifecycleScope
import com.example.rathaanelectronics.Common.LocaleHelper
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.R
import kotlinx.coroutines.launch
import java.util.*

class IntroActivity : AppCompatActivity() {
    lateinit var btnArabic : AppCompatButton
    lateinit var btnEnglish : AppCompatButton
    var manager : MyPreferenceManager?=null
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(getLanguageAwareContext(newBase!!))
    }
    private fun getLanguageAwareContext(context: Context): Context? {
        if (manager == null)
            manager = MyPreferenceManager(context)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(Locale(manager?.locale))
        return context.createConfigurationContext(configuration)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        if (manager == null)
            manager = MyPreferenceManager(this)
        btnArabic = findViewById(R.id.btn_arabic)
        btnEnglish = findViewById(R.id.btn_english)
        if (manager?.locale.equals("en")){
            btnArabic.setBackgroundColor(resources.getColor(R.color.colorPrimary_transp))
            btnArabic.setTextColor(resources.getColor(R.color.white))
            btnEnglish.setBackgroundColor(resources.getColor(R.color.white))
            btnEnglish.setTextColor(resources.getColor(R.color.colorPrimary))
        }else{
            btnEnglish.setBackgroundColor(resources.getColor(R.color.colorPrimary_transp))
            btnEnglish.setTextColor(resources.getColor(R.color.white))
            btnArabic.setBackgroundColor(resources.getColor(R.color.white))
            btnArabic.setTextColor(resources.getColor(R.color.colorPrimary))
        }
        btnArabic.setOnClickListener {
            lifecycleScope.launch {

                LocaleHelper().setLocale(this@IntroActivity, "ar")
                manager?.locale = "ar"
                recreate()
                startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                finishAffinity()
            }
        }
        btnEnglish.setOnClickListener {
            lifecycleScope.launch {

                LocaleHelper().setLocale(this@IntroActivity, "en")
                manager?.locale = "en"
                recreate()
                startActivity(Intent(this@IntroActivity, MainActivity::class.java))
                finishAffinity()
            }
        }
    }
}