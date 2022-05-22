package com.example.rathaanelectronics.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rathaanelectronics.Common.Globalmetheds

import com.skyfishjy.library.RippleBackground

import android.view.View
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.example.rathaanelectronics.R


class Splash_Screen_Activity : AppCompatActivity() {
    val globalmetheds = Globalmetheds()

    var rippleBackground: RippleBackground? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       // globalmetheds.setStatusbar(this)
        setContentView(R.layout.activity_splash_screen)

        rippleBackground = findViewById<View>(R.id.content) as RippleBackground
        rippleBackground!!.startRippleAnimation()
        val secondsDelayed = 1


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 5000)

    }
}