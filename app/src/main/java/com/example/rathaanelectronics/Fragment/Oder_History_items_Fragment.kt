package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.Oder_history_item_list_Adapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.OrderDetailsModel
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
 * Use the [Oder_History_items_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Oder_History_items_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var orderId: String? = null
    private var param2: String? = null
    private var manager: MyPreferenceManager? = null
    lateinit var rvOrderItems:RecyclerView
    lateinit var tvOrderId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            orderId = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        manager = MyPreferenceManager(activity)
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_oder__history_items_, container, false)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }

        setHasOptionsMenu(true);

        rvOrderItems =  view.findViewById<RecyclerView>(R.id.recycler_oder_history_items)
        tvOrderId = view.findViewById(R.id.tv_order_id);
        tvOrderId.text = getString(R.string.order_id,orderId)
        rvOrderItems.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        return view
    }

    override fun onResume() {
        super.onResume()
        getOrderList()
    }

    private fun getOrderList() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        LoadingDialog.showLoadingDialog(requireContext(),"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<OrderDetailsModel> = apiService.getOrderDetails(
            ApiConstants.LG_APP_KEY,
            token,
            orderId
        )
        call.enqueue(object : Callback<OrderDetailsModel?> {


            override fun onResponse(
                call: Call<OrderDetailsModel?>?,
                response: Response<OrderDetailsModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message



                    if (status == "true") {
                        if (response.body()?.data != null) {
                            if (!response.body()?.data?.orderDetails.isNullOrEmpty()){
                                val Oder_history_item_list_Adapter = Oder_history_item_list_Adapter(activity,response.body()?.data?.orderDetails!!,manager?.locale.equals("ar"))

                                rvOrderItems.adapter =Oder_history_item_list_Adapter
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


            override fun onFailure(call: Call<OrderDetailsModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                LoadingDialog.cancelLoading()
            }
        })

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Oder_History_items_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Oder_History_items_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}