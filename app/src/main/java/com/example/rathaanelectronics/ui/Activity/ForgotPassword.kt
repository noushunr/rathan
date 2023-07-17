package com.example.rathaanelectronics.ui.Activity

import android.content.Context
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Managers.ConnectivityReceiver
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.ForgotPasswordModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants.LG_APP_KEY
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ForgotPassword : AppCompatActivity() {

    lateinit var btn_frgt: Button
    lateinit var edt_forgot: EditText
    lateinit var txt_goto_email: TextView
    private var manager: MyPreferenceManager? = null
    var forgotmsg: String? = null

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
        setContentView(R.layout.activity_forgot_password)


        UIWidgets()


        btn_frgt.setOnClickListener {
            doForgot(edt_forgot.text.toString().trim())
        }
    }


    fun UIWidgets() {
        manager = MyPreferenceManager(this)
        Log.e("userToken", manager!!.userToken)
        btn_frgt = findViewById<Button>(R.id.btn_frgt)
        edt_forgot = findViewById(R.id.edt_forgot)
        txt_goto_email = findViewById(R.id.txt_goto_email)

    }

    private fun doForgot(email: String) {


        var isError = false
        var mFocusView: View? = null
        edt_forgot.setError(null)



        if (!isDataValid(email)) {
            isError = true
            edt_forgot!!.setError(getString(R.string.empty_field_error))
            mFocusView = edt_forgot
        }
        if (!isValidEmail(email)) {
            isError = true
            edt_forgot!!.setError(getString(R.string.invalid_email))
            mFocusView = edt_forgot
        }

        if (isError) {

            mFocusView!!.requestFocus()

            return
        } else {

            if (ConnectivityReceiver.isConnected()) {

                user_forgot(email)

            } else {

                Toast.makeText(this@ForgotPassword, getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
                    .show()

            }
        }
    }

    fun isValidEmail(emailAddress: String?): Boolean {
        return !TextUtils.isEmpty(emailAddress) && Patterns.EMAIL_ADDRESS.matcher(emailAddress)
            .matches()
    }

    fun isDataValid(mETFirstName: String): Boolean {

        return !(mETFirstName == "" || mETFirstName.isEmpty())

    }

    fun user_forgot(email: String) {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<ForgotPasswordModel> =
            apiService.ForgotPassword(
                LG_APP_KEY, email
            )
        LoadingDialog.showLoadingDialog(this,"")
        call.enqueue(object : Callback<ForgotPasswordModel?> {


            override fun onResponse(
                call: Call<ForgotPasswordModel?>?,
                response: Response<ForgotPasswordModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String? = response.body()!!.status
                    val messege: String? = response.body()!!.message


                    if (status == "true") {
                        forgotmsg = response.body()!!.data?.forgotpassword
                        txt_goto_email.text = forgotmsg
                        Toast.makeText(
                            this@ForgotPassword,
                            forgotmsg,
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {

                        Toast.makeText(
                            this@ForgotPassword,
                            messege,
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ForgotPasswordModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }
}