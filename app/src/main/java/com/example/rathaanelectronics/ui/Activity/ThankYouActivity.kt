package com.example.rathaanelectronics.ui.Activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.R
import java.util.*

class ThankYouActivity : AppCompatActivity() {

    private var manager: MyPreferenceManager? = null

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
    var backtohome: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.thank_layout)



        backtohome = findViewById(R.id.backtohome)

        backtohome?.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finishAffinity()
    }
}