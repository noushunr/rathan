package com.example.rathaanelectronics.Activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rathaanelectronics.Common.Globalmetheds

import com.skyfishjy.library.RippleBackground

import android.view.View
import android.content.Intent
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.GuestTokenModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Splash_Screen_Activity : AppCompatActivity() {
    val globalmetheds = Globalmetheds()
    private var manager: MyPreferenceManager? = null
    var rippleBackground: RippleBackground? = null

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

       // globalmetheds.setStatusbar(this)
        setContentView(R.layout.activity_splash_screen)
        manager = MyPreferenceManager(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        if (manager?.userToken!!.isEmpty()) {
            if (manager?.guestToken!!.isEmpty()) {
                getGuestToken()
            }
        }
//        rippleBackground = findViewById<View>(R.id.content) as RippleBackground
//        rippleBackground!!.startRippleAnimation()
//        val secondsDelayed = 1


        Handler(Looper.getMainLooper()).postDelayed({
            if (manager?.isFirstTime!!){
                manager?.isFirstTime( false)
                startActivity(Intent(this, IntroActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }

        }, 5000)

    }

    fun getGuestToken() {


        // val deviceid: String = Settings.Secure.ANDROID_ID
        val deviceid = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<GuestTokenModel> = apiService.getGuestToken(ApiConstants.LG_APP_KEY, deviceid)

        call.enqueue(object : Callback<GuestTokenModel?> {


            override fun onResponse(
                call: Call<GuestTokenModel?>?,
                response: Response<GuestTokenModel?>
            ) {
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        manager?.saveGuestToken(response.body()!!.data?.guesttokenAccessToken)
                    } else {

                    }

                } else {

                }
            }


            override fun onFailure(call: Call<GuestTokenModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }
}