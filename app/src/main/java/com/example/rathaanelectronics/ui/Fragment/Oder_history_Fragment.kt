package com.example.rathaanelectronics.ui.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.ui.Adapter.Oder_history_list_Adapter
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.OrderListModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Oder_history_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Oder_history_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var cart_Fragment = Cart_Fragment()
    private var manager: MyPreferenceManager? = null
    lateinit var rvOrderHistory:RecyclerView
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
        val view=inflater.inflate(R.layout.fragment_oder_history_, container, false)
        setHasOptionsMenu(true);
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        rvOrderHistory =  view.findViewById<RecyclerView>(R.id.recycler_oder_history)
        rvOrderHistory.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
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
        val call: Call<OrderListModel> = apiService.getOrderList(
            ApiConstants.LG_APP_KEY,
            token
        )
        call.enqueue(object : Callback<OrderListModel?> {


            override fun onResponse(
                call: Call<OrderListModel?>?,
                response: Response<OrderListModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message



                    if (status == "true") {
                        if (response.body()?.data != null) {
                            if (!response.body()?.data?.orderList.isNullOrEmpty()){
                                val Oder_history_list_Adapter = Oder_history_list_Adapter(activity,response.body()?.data?.orderList!!)
                                rvOrderHistory.adapter = Oder_history_list_Adapter
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


            override fun onFailure(call: Call<OrderListModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                LoadingDialog.cancelLoading()
            }
        })

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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Oder_history_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Oder_history_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}