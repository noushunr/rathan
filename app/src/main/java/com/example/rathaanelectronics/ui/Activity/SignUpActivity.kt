package com.example.rathaanelectronics.ui.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.example.rathaanelectronics.R
import java.util.regex.Pattern
import android.content.res.Configuration

import com.example.rathaanelectronics.Data.ApiConstants

import com.example.rathaanelectronics.Data.ApiInterface

import com.example.rathaanelectronics.Data.ServiceGenerator


import android.widget.*
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Managers.ConnectivityReceiver.isConnected
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.SignUpModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class SignUpActivity : AppCompatActivity() {


    var edt_login_username: EditText? = null
    var edt_login_userEmail: EditText? = null
    var edt_login_userPassword: EditText? = null
    var btn_sign_up: Button? = null
    var ll_already: LinearLayout? = null
    var Susername: String? = null
    var Spassword: String? = null
    var Semail: String? = null
    val context: Context? = null
    var fristname: String? = null
    var lastname: String? = null
    var dob: String? = null
    var gender: String? = null
    var userphone: String? = null
    var userlevel: String? = null
    var createpass: String? = null
    var confirmpass: String? = null
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

    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +  //at least 1 lower case letter
                "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=\\S+$)" +  //no white spaces
                ".{8,}" +  //at least 8 characters
                "$"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        UIWidgets()


        btn_sign_up!!.setOnClickListener {
            doRegister(
                edt_login_username!!.text.toString().trim(),
                edt_login_userEmail!!.text.toString().trim(),
                edt_login_userPassword!!.text.toString().trim()
            )

        }
        ll_already!!.setOnClickListener {


            startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
            finish()

        }

    }

    fun UIWidgets() {

        edt_login_username = findViewById(R.id.edt_login_userName)
        edt_login_userEmail = findViewById(R.id.edt_login_userEmail)
        edt_login_userPassword = findViewById(R.id.edt_login_userPassword)
        btn_sign_up = findViewById(R.id.btn_sign_up)
        ll_already = findViewById<LinearLayout>(R.id.ll_already)
    }

    private fun doRegister(Name: String, email: String, pass: String) {
        var isError = false
        var mFocusView: View? = null
        edt_login_username!!.setError(null)
        edt_login_userEmail!!.setError(null)
        edt_login_userPassword!!.setError(null)

        if (!isDataValid(Name)) {
            isError = true
            edt_login_username!!.setError(getString(R.string.empty_field_error))
            mFocusView = edt_login_username
        }
//        if (!isDataValid(lName)) {
//            isError = true
//            mETUserLName.setError("Field can't be empty")
//            mFocusView = mETUserLName
//        }
        if (!isDataValid(email)) {
            isError = true
            edt_login_userEmail!!.setError(getString(R.string.empty_field_error))
            mFocusView = edt_login_userEmail
        }
        if (!isDataValid(pass)) {
            isError = true
            edt_login_userPassword!!.setError(getString(R.string.empty_field_error))
            mFocusView = edt_login_userPassword
        }
        if (!isValidEmail(email)) {
            isError = true
            edt_login_userEmail!!.setError(getString(R.string.invalid_email))
            mFocusView = edt_login_userEmail
        }
        if (!isValidPass(pass)) {
            isError = true
            edt_login_userPassword!!.setError(getString(R.string.invalid_password))
            mFocusView = edt_login_userPassword
        }
//        if (!isDataValid(phone)) {
//            isError = true
//            mETPhone.setError("Field can't be empty")
//            mFocusView = mETPhone
//        }
//        if (!isValidMobile(phone)) {
//            isError = true
//            mETPhone.setError("Enter valid number")
//            mFocusView = mETPhone
//        }
//        if (!isDataValid(otp)) {
//            isError = true
//            mETOtp.setError("Field can't be empty")
//            mFocusView = mETOtp
//        }
        if (isError) {

            mFocusView!!.requestFocus()

            return
        } else {

            if (isConnected()) {

                user_SignUp(Name, email, pass)

            } else {

                Toast.makeText(this@SignUpActivity, getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
                    .show()

            }
        }
    }

    fun isDataValid(mETFirstName: String): Boolean {

        return !(mETFirstName == "" || mETFirstName.isEmpty())

    }

    fun isValidEmail(emailAddress: String?): Boolean {
        return !TextUtils.isEmpty(emailAddress) && Patterns.EMAIL_ADDRESS.matcher(emailAddress)
            .matches()
    }

    fun isValidPass(passwordInput: String?): Boolean {
        return !TextUtils.isEmpty(passwordInput) && PASSWORD_PATTERN.matcher(passwordInput)
            .matches()
    }

    private fun isValidMobile(phone: String): Boolean {
        return android.util.Patterns.PHONE.matcher(phone).matches()
    }


    fun user_SignUp(Name: String, email: String, pass: String) {
        LoadingDialog.showLoadingDialog(this,"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<SignUpModel> =
            apiService.signup(
                ApiConstants.LG_APP_KEY,
                Name,
                "cc",
                email,
                "0000-00-00",
                "FM",
                "0",
                "sa",
                pass,
                pass
            )

        call.enqueue(object : Callback<SignUpModel?> {


            override fun onResponse(call: Call<SignUpModel?>?, response: Response<SignUpModel?>) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: Boolean = response.body()!!.status
                    val messege: String = response.body()!!.message
                    val responce: String = response.body()!!.data.response.signUp
                    if (status && messege == "Success") {

                        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                        finish()


                    } else {

                        Toast.makeText(
                            this@SignUpActivity,
                            messege,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {
//                    Toast.makeText(
//                        this@Sign_up_Activity,
//                        response.message(),
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }

            override fun onFailure(call: Call<SignUpModel?>?, t: Throwable?) {
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
            }
        })
    }


}

