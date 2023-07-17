package com.example.rathaanelectronics.ui.Fragment.DealsCategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.ui.Adapter.Top_deals_list_Adapter
import com.example.rathaanelectronics.Utils.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.ui.Fragment.Product_Detail_view_Fragment
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CartResponseModel
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.Product
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
 * Use the [HotDealsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewArrivalsFragment : Fragment(), HotdealsItemClick {

    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    lateinit var Rcy_newArrival: RecyclerView
    var NewArrival_data: List<Product> = ArrayList<Product>()
    private var manager: MyPreferenceManager? = null


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


        val view = inflater!!.inflate(R.layout.fragment_hot_deals, container, false)

       // val top_deals_list_Adapter = Top_deals_list_Adapter(requireActivity())

        Rcy_newArrival = view.findViewById<View>(R.id.hot_deals) as RecyclerView

        val numberOfColumns = 2
        Rcy_newArrival.layoutManager = GridLayoutManager(activity, numberOfColumns)
        Rcy_newArrival.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )

        NewArrivals()

        return view
    }

    override fun onResume() {

        super.onResume()

    }

    fun NewArrivals() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DealsModel> = apiService.NewArrivals(ApiConstants.LG_APP_KEY,
            manager?.getGuestToken())

        call.enqueue(object : Callback<DealsModel?> {


            override fun onResponse(call: Call<DealsModel?>?, response: Response<DealsModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        NewArrival_data = response.body()!!.data!!

                        Log.e("NewArrival_data", NewArrival_data.size.toString())
                    }


                    Rcy_newArrival.adapter= Top_deals_list_Adapter(requireActivity(),NewArrival_data,this@NewArrivalsFragment,true)




                } else {
                }
            }


            override fun onFailure(call: Call<DealsModel?>?, t: Throwable?) {
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
         * @return A new instance of fragment HotDealsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewArrivalsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }




    override fun onHotdealsClicked(position: Int, item: Product?) {
        val bundle = Bundle()
        bundle.putString("productId", item?.productId)
        val subcategory = Product_Detail_view_Fragment()
        subcategory.arguments = bundle
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, subcategory)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    override fun onAddToWishlistButtonClick(productId: String) {

    }

    override fun onDeleteFromWishListButtonClick(productId: String) {

    }
    override fun onAddToCart(productId: String) {
        addToCartRequest(productId,"1")
    }

    private fun addToCartRequest(productId: String?, quantity: String) {
        LoadingDialog.showLoadingDialog(requireContext(), "")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartResponseModel> =
            apiService.addToCart(
                ApiConstants.LG_APP_KEY,
                token,
                productId,
                quantity
            )

        call.enqueue(object : Callback<CartResponseModel?> {


            override fun onResponse(
                call: Call<CartResponseModel?>?,
                response: Response<CartResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Add to cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: Boolean = response.body()!!.status
                    val message: String? = if (manager?.locale.equals("en")){ response.body()!!.message } else{
                        response.body()!!.messageAr
                    }

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status) {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
//                        getCartCount()
                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        activity,
                        "Adding to Cart failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Add to cart", "Adding to cart failed")
                }
            }

            override fun onFailure(call: Call<CartResponseModel?>?, t: Throwable?) {
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

}