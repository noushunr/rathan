package com.example.rathaanelectronics.Fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Model.TermsContentModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TermsConditionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TermsConditionFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var tvContent : TextView
    private lateinit var llBack: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_terms_condition, container, false)
        tvContent = view.findViewById(R.id.tv_content)
        llBack = view.findViewById(R.id.ll_back)
        llBack.setOnClickListener{
            activity?.onBackPressed()
        }
        getTerms()
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TermsConditionFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TermsConditionFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
    private fun getTerms()     {

        LoadingDialog.showLoadingDialog(requireContext(),"")

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<TermsContentModel> = apiService.getTerms(
            ApiConstants.LG_APP_KEY
        )
        call.enqueue(object : Callback<TermsContentModel?> {


            override fun onResponse(
                call: Call<TermsContentModel?>?,
                response: Response<TermsContentModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                tvContent.text =
                                    Html.fromHtml(response.body()?.data?.termsaleContent!!, Html.FROM_HTML_MODE_LEGACY).toString()
                            }else{
                                tvContent.text = Html.fromHtml(response.body()?.data?.termsaleContent!!)
                            }

                        }

                    } else {
                        Toast.makeText(
                            requireContext(),
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        requireContext(),
                        response?.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<TermsContentModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }

}