package com.example.rathaanelectronics.Utils

import android.app.ProgressDialog
import android.content.Context

/**
 * Created by Noushad N on 21-03-2022.
 */
class LoadingDialogue {

    var progressDialog: ProgressDialog? = null

    fun showLoadingDialog(context: Context?, message: String?) {
        if (!(progressDialog != null && progressDialog!!.isShowing)) {
            progressDialog = ProgressDialog(context)
            progressDialog!!.setMessage(message)
            progressDialog!!.setCancelable(false)
            progressDialog!!.setCanceledOnTouchOutside(false)
            try {
                progressDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun cancelLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) try {
            progressDialog!!.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}