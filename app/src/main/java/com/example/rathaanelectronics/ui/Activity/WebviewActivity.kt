package com.example.rathaanelectronics.ui.Activity

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants.BASE_URL
import java.util.*

class WebviewActivity : AppCompatActivity() {
    private lateinit var webview: WebView
    private lateinit var llBack: LinearLayout
    lateinit var tvTitle : TextView
    var url:String? = null
    var installmentUrl:String? = null
    private var secondTime = false
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
        setContentView(R.layout.activity_webview)
        url = intent.extras?.getString("payment_url")
        installmentUrl = intent.extras?.getString("installment_url")
        webview = findViewById(R.id.knet_webView)
        llBack = findViewById(R.id.ll_back)
        tvTitle = findViewById(R.id.tv_title)
        llBack.setOnClickListener{
            finish()
        }
        if (url!=null){
            tvTitle.text = getString(R.string.payment)
        }else if (installmentUrl!=null){
            tvTitle.text = getString(R.string.installment_payment)
        }
        LoadingDialog.showLoadingDialog(this@WebviewActivity, "Loading...")
        webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                LoadingDialog.cancelLoading()
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                super.onReceivedError(view, request, error)
                //val htmlData ="<html><body><div align=\"center\" >Sorry!<br>Unable to connect to the page you have requested..<br></div></body>"
                //view?.loadUrl("about:blank")
                //view?.loadDataWithBaseURL(null, htmlData, "text/html", "UTF-8",null)
            }

            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                if (url != null) {
                    Log.e("url", url)
                    val successBool = url.contains(BASE_URL + "Checkout/payment_success", ignoreCase = true)
                    val failBool = url.contains(BASE_URL + "Checkout/payment_failed", ignoreCase = true)
                    println("$successBool $failBool")
                    if (successBool && secondTime) {
                        //println("successBool")
                        val intent = Intent(this@WebviewActivity, ThankYouActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (failBool && secondTime) {
                        //println("failBool")

                        Toast.makeText(this@WebviewActivity,"Payment Failed",Toast.LENGTH_LONG).show()
                        finish()
                    }
                    secondTime = true
                }
                super.doUpdateVisitedHistory(view, url, isReload)
            }
        }
        true.also { webview.settings.javaScriptEnabled = it }
        if (url!=null)
            webview.loadUrl(url!!)
        else if (installmentUrl!=null)
            webview.loadUrl(installmentUrl!!)

    }

}