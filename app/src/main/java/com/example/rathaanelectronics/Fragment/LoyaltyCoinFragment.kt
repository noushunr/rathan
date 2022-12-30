package com.example.rathaanelectronics.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.LoyaltyAdapter
import com.example.rathaanelectronics.Adapter.WalletAdapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.LoyaltyModel
import com.example.rathaanelectronics.Model.WalletModel
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
 * Use the [LoyalityCoinFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoyalityCoinFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var manager: MyPreferenceManager? = null
    private lateinit var rvWallet : RecyclerView
    lateinit var tvBalance: TextView
    lateinit var tvWithdraw: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
         }
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_loyality_coin, container, false)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        rvWallet = view.findViewById(R.id.rv_wallet)
        tvBalance = view.findViewById(R.id.tv_balance)
//        tvWithdraw = view.findViewById(R.id.tv_withdraw)
        rvWallet.layoutManager =
            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        getMyLoyalty()



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoyalityCoinFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoyalityCoinFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getMyLoyalty() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        LoadingDialog.showLoadingDialog(requireContext(),"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<LoyaltyModel> = apiService.getMyLoyaltyPoints(
            ApiConstants.LG_APP_KEY,
            token
        )
        call.enqueue(object : Callback<LoyaltyModel?> {


            override fun onResponse(
                call: Call<LoyaltyModel?>?,
                response: Response<LoyaltyModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message



                    if (status == "true") {
                        if (response.body()?.data != null) {
                            if (!response.body()?.data?.details?.isNullOrEmpty()!!){

                                var loyaltyAdapter = LoyaltyAdapter(requireContext(),response.body()?.data?.details!!,manager?.locale.equals("ar"))
                                rvWallet.adapter = loyaltyAdapter
                            }
                            try {

                                if (response.body()?.data?.amountBalance!=null){
                                    tvBalance.text = response.body()?.data?.amountBalance
                                }
                            }catch (e:Exception){

                            }
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {

                }
            }


            override fun onFailure(call: Call<LoyaltyModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                LoadingDialog.cancelLoading()
            }
        })

    }
}