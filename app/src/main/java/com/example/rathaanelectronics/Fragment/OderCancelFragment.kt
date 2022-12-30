package com.example.rathaanelectronics.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.ReasonAdapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.ReasonItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.OrderCancelReasons
import com.example.rathaanelectronics.Model.OrderDetails
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
 * Use the [OderCancelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OderCancelFragment : Fragment(), ReasonItemClick {
    // TODO: Rename and change types of parameters
    private var param1: OrderDetails? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    private var manager: MyPreferenceManager? = null
    private lateinit var rvReason : RecyclerView
    lateinit var tvItemName : TextView
    lateinit var tvTotal : TextView
    lateinit var tvQty : TextView
    lateinit var ivItem : ImageView
    lateinit var btnCancel : Button
    lateinit var btnSubmit : Button
    lateinit var etMessage : EditText
    var reasonId = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.get(ARG_PARAM1) as OrderDetails?
            param2 = it.getString(ARG_PARAM2)
        }
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_oder_cancel, container, false)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        rvReason = view.findViewById(R.id.rv_reasons)
        tvItemName = view.findViewById(R.id.tv_item_name)
        tvTotal = view.findViewById(R.id.tv_total)
        tvQty = view.findViewById(R.id.tv_qty)
        ivItem = view.findViewById(R.id.iv_item)
        etMessage = view.findViewById(R.id.et_message)
        btnCancel = view.findViewById(R.id.btn_cancel)
        btnSubmit = view.findViewById(R.id.btn_submit)
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL +param1?.dcProdImage)
            .into(ivItem)
        tvItemName.text = param1?.dcProdName
        tvTotal.text = "KD ${param1?.ordersTotalAmount}"
        tvQty.text = getString(R.string.quantity) + param1?.dcProdQuantity
        rvReason.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        getReasonList()

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        btnCancel?.setOnClickListener {
            activity?.onBackPressed()
        }
        btnSubmit?.setOnClickListener {
            requestCancel()
        }

        return view
    }

    private fun getReasonList() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        LoadingDialog.showLoadingDialog(requireContext(), "")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<OrderCancelReasons> = apiService.getOrderReturnReasons(
            ApiConstants.LG_APP_KEY,
            token
        )
        call.enqueue(object : Callback<OrderCancelReasons?> {


            override fun onResponse(
                call: Call<OrderCancelReasons?>?,
                response: Response<OrderCancelReasons?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message



                    if (status == "true") {
                        if (response.body()?.data != null) {
                            if (!response.body()?.data?.isNullOrEmpty()!!) {
                                val adater = ReasonAdapter(activity,response.body()?.data!!,this@OderCancelFragment,
                                    manager?.locale.equals("ar"))
                                rvReason.adapter = adater
                                reasonId = response.body()?.data!![0].returnReasonId!!
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


            override fun onFailure(call: Call<OrderCancelReasons?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                LoadingDialog.cancelLoading()
            }
        })

    }

    private fun requestCancel() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        LoadingDialog.showLoadingDialog(requireContext(), "")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.returnRequest(
            ApiConstants.LG_APP_KEY,
            token,
            param1?.dcOrderId,
            param1?.dcProdId,
            reasonId,
            etMessage?.text.toString()
        )
        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message



                    if (status == "true") {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        activity?.onBackPressed()
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


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                LoadingDialog.cancelLoading()
            }
        })

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(false)
        this.Menufilter = menu.findItem(R.id.filter).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)


    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OderCancelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: OrderDetails, param2: String) =
            OderCancelFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onReasonClick(reasons: OrderCancelReasons.Data) {

        reasonId = reasons.returnReasonId!!

    }
}