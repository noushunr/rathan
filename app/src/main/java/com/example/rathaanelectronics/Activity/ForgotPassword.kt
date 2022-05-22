package com.example.rathaanelectronics.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import com.example.rathaanelectronics.Managers.ConnectivityReceiver
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.ForgotPasswordModel
import com.example.rathaanelectronics.Model.SignUpModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants.LG_APP_KEY
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPassword : AppCompatActivity() {

    lateinit var btn_frgt: Button
    lateinit var edt_forgot: EditText
    lateinit var txt_goto_email: TextView
    private var manager: MyPreferenceManager? = null
    var forgotmsg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        UIWidgets()


        btn_frgt.setOnClickListener {
            doForgot(edt_forgot.text.toString())
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
            edt_forgot!!.setError("Field can't be empty")
            mFocusView = edt_forgot
        }
        if (!isValidEmail(email)) {
            isError = true
            edt_forgot!!.setError("Enter valid email")
            mFocusView = edt_forgot
        }

        if (isError) {

            mFocusView!!.requestFocus()

            return
        } else {

            if (ConnectivityReceiver.isConnected()) {

                user_forgot(email)

            } else {

                Toast.makeText(this@ForgotPassword, "No internet connection", Toast.LENGTH_LONG)
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

        call.enqueue(object : Callback<ForgotPasswordModel?> {


            override fun onResponse(
                call: Call<ForgotPasswordModel?>?,
                response: Response<ForgotPasswordModel?>
            ) {
                Log.e("Signup Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String? = response.body()!!.data?.status
                    val messege: String? = response.body()!!.data?.message


                    if (status == "true" && messege == "Success") {


                        forgotmsg = response.body()!!.data?.responce?.forgotpassword

                        txt_goto_email.text = forgotmsg
                        Log.e("status", status.toString() + "")
                        Log.e("messege", messege.toString() + "")
                        Log.e("responce", forgotmsg.toString() + "")


                    } else {

                        Toast.makeText(
                            this@ForgotPassword,
                            forgotmsg,
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                } else {

                }
            }

            override fun onFailure(call: Call<ForgotPasswordModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }
}