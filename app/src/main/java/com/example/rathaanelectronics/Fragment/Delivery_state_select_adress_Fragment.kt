package com.example.rathaanelectronics.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.Address_list_Adapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.AddressItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.AddressResponseModel
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.DeliveryChargeModel
import com.example.rathaanelectronics.Model.PreCheckoutModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.google.android.material.floatingactionbutton.FloatingActionButton


import com.kofigyan.stateprogressbar.StateProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Delivery_state_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Delivery_state_select_adress_Fragment : Fragment(), AddressItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: PreCheckoutModel? = null
    var btn_continue: Button? = null
    var ll_selected: LinearLayout? = null
    lateinit var tvTotal: TextView

    val addAddressFragment = AddAddressFragment()
    var cart_Fragment = Cart_Fragment()
    private var manager: MyPreferenceManager? = null
    lateinit var recycler_address: RecyclerView
    var addressItem: AddressResponseModel.Details? = null
    lateinit var preCheckoutModel: PreCheckoutModel
    var addressList : MutableList<AddressResponseModel.Details> = mutableListOf()
    lateinit var adapter : Address_list_Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            preCheckoutModel = it.get(ARG_PARAM2) as PreCheckoutModel
        }
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delivery_state_address, container, false)

        btn_continue = view.findViewById(R.id.btn_continue);
        tvTotal = view.findViewById(R.id.tv_total)
//        ll_selected = view.findViewById(R.id.ll_selected);
        recycler_address = view.findViewById<RecyclerView>(R.id.recycler_address)
        val flot_add_address = view.findViewById<FloatingActionButton>(R.id.flot_add_address)


        //val Address_list_Adapter = Address_list_Adapter(activity)
        recycler_address.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        tvTotal.text = preCheckoutModel.total
        //recycler_address.adapter = Address_list_Adapter


        flot_add_address.setOnClickListener { view ->

            changeFragemnt(addAddressFragment)

        }
        val stateProgressBar =
            view.findViewById(com.example.rathaanelectronics.R.id.state_progress) as StateProgressBar
        stateProgressBar.enableAnimationToCurrentState(true)
        var descriptionData = arrayOf(getString(R.string.address_), getString(R.string.delivery), getString(R.string.payment))
        stateProgressBar.setStateDescriptionData(descriptionData)
//        val typeface:String = activity?.let { ResourcesCompat.getFont(it, R.font.roboto) }
//        stateProgressBar.setStateDescriptionTypeface(typeface);


//        ll_selected!!.visibility = View.VISIBLE
        btn_continue?.setOnClickListener { view ->
            if (addressItem == null)
                Toast.makeText(requireContext(), getString(R.string.choose_address), Toast.LENGTH_SHORT)
                    .show()
            else {
                preCheckoutModel.addressId = addressItem?.addressId!!
                preCheckoutModel.area = addressItem?.cityId!!
                if (preCheckoutModel.pickStore.equals("0"))
                    preCheckoutModel.deliveryType = "del"
                else
                    preCheckoutModel.deliveryType = "pick"
                changeFragemnt(Delivery_state_select_delivery_Fragment.newInstance(addressItem!!,preCheckoutModel))
//                if (preCheckoutModel.pickStore.equals("0"))
//                    changeFragemnt(Delivery_state_select_delivery_Fragment.newInstance(addressItem!!,preCheckoutModel))
//                else
//                    changeFragemnt(Delivery_state_select_payment_Fragment.newInstance(addressItem!!,preCheckoutModel))
            }

        }

        return view
    }


    override fun onResume() {
        super.onResume()
        fetchAddressList()
    }

    fun changeFragemnt(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Delivery_state_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: PreCheckoutModel) =
            Delivery_state_select_adress_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }

    private fun fetchAddressList() {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        Log.d("Guest Token",token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AddressResponseModel> = apiService.showAddress(
            ApiConstants.LG_APP_KEY,
            token
        )

        call.enqueue(object : Callback<AddressResponseModel?> {


            override fun onResponse(
                call: Call<AddressResponseModel?>?,
                response: Response<AddressResponseModel?>
            ) {
                Log.e("show cart Response", response.toString() + "")
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status == "true") {
                        addressList.clear()
                        addressList.addAll(response.body()?.data?.details!!)
                        if (addressList != null && addressList?.size > 0) {
                            adapter = Address_list_Adapter(
                                activity,
                                addressList!!,
                                this@Delivery_state_select_adress_Fragment,
                                1
                            )
                            recycler_address.adapter = adapter
                        }

                    } else {
//                        recycler_cart.isGone = true
//                        emptyText.isVisible = true
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {

                }
            }


            override fun onFailure(call: Call<AddressResponseModel?>?, t: Throwable?) {
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    override fun editAddressItem(addressItem: AddressResponseModel.Details) {

        changeFragemnt(AddAddressFragment.newInstance(addressItem,""))
    }

    override fun deleteAddressItem(addressId: String,position:Int) {

        deleteAddress(addressId,position)
    }

    override fun onSelected(addressItem: AddressResponseModel.Details) {

        this.addressItem = addressItem
    }
    private fun deleteAddress(addressId:String, position: Int) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        Log.d("Guest Token",token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.deleteAddress(
            ApiConstants.LG_APP_KEY,
            token,addressId
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

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status == "true") {
                        addressList.removeAt(position)
                        adapter?.notifyItemRemoved(position)

                    } else {
//                        recycler_cart.isGone = true
//                        emptyText.isVisible = true
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
                LoadingDialog.cancelLoading()
            }
        })
    }
}