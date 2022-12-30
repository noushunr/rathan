package com.example.rathaanelectronics.Activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Managers.ConnectivityReceiver
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.SignInModel
import com.example.rathaanelectronics.Model.SignUpModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants.LG_APP_KEY
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Sign_in_Activity : AppCompatActivity() {

    lateinit var ll_signup: LinearLayout
    lateinit var ll_forgot_password: LinearLayout
    lateinit var btn_sign_in: Button
    lateinit var edt_login_username: EditText
    lateinit var edt_login_password: EditText
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)



        UIWidgets()
        btn_sign_in.setOnClickListener {
            doRegister(edt_login_username.text.toString().trim(), edt_login_password.text.toString().trim())
        }

        ll_signup.setOnClickListener {
            startActivity(Intent(this, Sign_up_Activity::class.java))
            finish()
        }
        ll_forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPassword::class.java))
            finish()
        }

    }


    fun UIWidgets() {
        manager = MyPreferenceManager(this)
        ll_forgot_password = findViewById<LinearLayout>(R.id.ll_forgot_password)
        ll_signup = findViewById<LinearLayout>(R.id.ll_signup)
        btn_sign_in = findViewById<Button>(R.id.btn_sign_in)
        edt_login_username = findViewById(R.id.edt_login_username)
        edt_login_password = findViewById(R.id.edt_login_password)

//        edt_login_username.setText("ansebkali@gmail.com")
//        edt_login_password.setText("anseb328")

    }


    private fun doRegister(email: String, pass: String) {


        var isError = false
        var mFocusView: View? = null
        edt_login_username!!.setError(null)
        edt_login_password!!.setError(null)


        if (!isDataValid(email)) {
            isError = true
            edt_login_username!!.setError(getString(R.string.empty_field_error))
            mFocusView = edt_login_username
        }
        if (!isDataValid(pass)) {
            isError = true
            edt_login_password!!.setError(getString(R.string.empty_field_error))
            mFocusView = edt_login_password
        }

        if (isError) {

            mFocusView!!.requestFocus()

            return
        } else {

            if (ConnectivityReceiver.isConnected()) {

                user_Signin(email, pass)

            } else {

                Toast.makeText(this@Sign_in_Activity, getString(R.string.no_internet_connection), Toast.LENGTH_LONG)
                    .show()

            }
        }
    }

    fun isDataValid(mETFirstName: String): Boolean {

        return !(mETFirstName == "" || mETFirstName.isEmpty())

    }


    fun user_Signin(username: String, pass: String) {

        LoadingDialog.showLoadingDialog(this,"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<SignInModel> =
            apiService.signin(LG_APP_KEY, username, pass)

        call.enqueue(object : Callback<SignInModel?> {


            override fun onResponse(call: Call<SignInModel?>?, response: Response<SignInModel?>) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: Boolean = response.body()!!.status
                    val messege: String = response.body()!!.message
                    if (status) {

                        if (response?.body()?.data!=null){
                            val usermail: String = response.body()!!.data.usermail
                            val Token: String = response.body()!!.data.token
                            val usermobile: String = response.body()!!.data.usermobile
                            val USERNAME: String = response.body()!!.data.username


                            manager!!.saveUsereDetails(USERNAME, usermail, usermobile, Token)
                            manager!!.saveUserToken(Token)
                            manager!!.setLogIn(true)

                            Toast.makeText(
                                this@Sign_in_Activity,
                                messege,
                                Toast.LENGTH_SHORT
                            ).show()

                            startActivity(Intent(this@Sign_in_Activity, MainActivity::class.java))
                            finish()
                        } else{
                            Toast.makeText(
                                this@Sign_in_Activity,
                                messege,
                                Toast.LENGTH_SHORT
                            ).show()
                        }




                    } else {

                        Toast.makeText(
                            this@Sign_in_Activity,
                            messege,
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                } else {


                }
            }

            override fun onFailure(call: Call<SignInModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }
}