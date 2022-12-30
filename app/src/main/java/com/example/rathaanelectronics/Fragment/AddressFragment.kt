package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.Address_list_Adapter
import com.example.rathaanelectronics.Adapter.Cart_list_Adapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.AddressItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.AddressResponseModel
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.ShowCartResponseModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AdressFragment : Fragment(), AddressItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val addAddressFragment = AddAddressFragment()
    var cart_Fragment = Cart_Fragment()
    private var manager: MyPreferenceManager? = null
    lateinit var recycler_address: RecyclerView
    private lateinit var llBack: LinearLayout
    lateinit var adapter : Address_list_Adapter
    var addressList : MutableList<AddressResponseModel.Details> = mutableListOf()
    private var Menufilter: MenuItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
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
        val view = inflater.inflate(R.layout.fragment_adress, container, false)
        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        recycler_address = view.findViewById<RecyclerView>(R.id.recycler_address)
        val flot_add_address = view.findViewById<FloatingActionButton>(R.id.flot_add_address)

        llBack = view.findViewById(R.id.ll_back)
        llBack.setOnClickListener{
            activity?.onBackPressed()
        }
        //val Address_list_Adapter = Address_list_Adapter(activity)
        recycler_address.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        //recycler_address.adapter = Address_list_Adapter
        fetchAddressList()



        flot_add_address.setOnClickListener { view ->

            changeFragemnt(addAddressFragment)

        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart -> {
                changeFragemnt(cart_Fragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun changeFragemnt(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()

    }

    private fun fetchAddressList(){
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AddressResponseModel> = apiService.showAddress(
            ApiConstants.LG_APP_KEY,
            token)

        call.enqueue(object : Callback<AddressResponseModel?> {


            override fun onResponse(
                call: Call<AddressResponseModel?>?,
                response: Response<AddressResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    if (status == "true") {
                        addressList.clear()
                        addressList.addAll(response.body()?.data?.details!!)
                        if (addressList != null && addressList?.size > 0) {
                            adapter = Address_list_Adapter(
                                activity,
                                addressList!!,this@AdressFragment,0)
                            recycler_address.adapter = adapter
                        }

                    }else{
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun editAddressItem(addressItem: AddressResponseModel.Details) {
        changeFragemnt(AddAddressFragment.newInstance(addressItem,""))
    }

    override fun deleteAddressItem(addressId: String,position:Int) {

        deleteAddress(addressId,position)
    }

    override fun onSelected(addressItem: AddressResponseModel.Details) {

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
                if (response.isSuccessful) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    if (status == "true") {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
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