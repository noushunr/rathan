package com.example.rathaanelectronics.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.rathaanelectronics.R

class Thank_You_Activity : AppCompatActivity() {

    var backtohome: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.thank_layout)



        backtohome = findViewById(R.id.backtohome)

        backtohome?.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

    }
}