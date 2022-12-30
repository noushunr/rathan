package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.rathaanelectronics.Adapter.Wish_list_Adapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.ManageWishlistItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CartCountModel
import com.example.rathaanelectronics.Model.CartResponseModel
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.WishListResponseModel
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
 * Use the [Wish_list_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Wish_list_Fragment : Fragment(), ManageWishlistItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var cart_Fragment = Cart_Fragment()
    private var Menufilter: MenuItem? = null
    private var manager: MyPreferenceManager? = null
    private var wishListData: List<WishListResponseModel.Data.Details>? =null
    private lateinit var wishlistRecycler: RecyclerView
    lateinit var emptyText:LinearLayout
    var showToolBar : ShowToolBar?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)

        showToolBar = context as ShowToolBar
    }

    override fun onDetach() {
        super.onDetach()
        showToolBar = null
    }
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
      val  view = inflater.inflate(R.layout.fragment_wish_list_, container, false)
        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()

      wishlistRecycler =  view.findViewById<RecyclerView>(R.id.recycler_wishlist)
        emptyText = view.findViewById(R.id.emptyText)
        //val wish_list_Adapter = Wish_list_Adapter(activity,wishListData)
        wishlistRecycler.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
       // wishlistRecycler.adapter =wish_list_Adapter
        fetchWishlist()
//        if(manager?.getUserToken()!!.isNotEmpty()){
//            fetchWishlist()
//        }else{
//            startActivity(Intent(activity, Sign_in_Activity::class.java))
//        }

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

//    fun wishListShowRequest() {
//
//        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
//        val call: Call<NewArrivalModel> = apiService.NewArrivals(ApiConstants.LG_APP_KEY)
//
//        call.enqueue(object : Callback<NewArrivalModel?> {
//
//
//            override fun onResponse(call: Call<NewArrivalModel?>?, response: Response<NewArrivalModel?>) {
//                Log.e("Signin Response", response.toString() + "")
//                if (response.isSuccessful()) {
//
//                    val status: String = response.body()!!.status.toString()
//                    val messege: String? = response.body()!!.message
//
//                    Log.e("status", status.toString() + "")
//                    Log.e("messege", messege.toString() + "")
//
//                    if (status == "true") {
//                        NewArrival_data = response.body()!!.data!!
//
//                        Log.e("NewArrival_data", NewArrival_data.size.toString())
//                    }
//
//
//                    recy_deals_.adapter= New_arrival_list_Adapter(requireActivity(),NewArrival_data,this@HomeFragment)
//
//
//
//
//                } else {
//                }
//            }
//
//
//            override fun onFailure(call: Call<NewArrivalModel?>?, t: Throwable?) {
//                // something went completely south (like no internet connection)
//                Log.e("onFailure", t.toString())
//            }
//        })
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Wish_list_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Wish_list_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun fetchWishlist(){
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        LoadingDialog.showLoadingDialog(requireContext(),"")
        Log.d("Token",token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<WishListResponseModel> = apiService.showWishlist(ApiConstants.LG_APP_KEY,
            token)

        call.enqueue(object : Callback<WishListResponseModel?> {


            override fun onResponse(
                call: Call<WishListResponseModel?>?,
                response: Response<WishListResponseModel?>
            ) {
                Log.e("show cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        wishListData = response.body()?.data?.details

                        Log.e("Show Wishlist data", wishListData.toString())

                        wishlistRecycler.adapter = Wish_list_Adapter(activity,wishListData!!,this@Wish_list_Fragment,manager?.locale.equals("ar"))
                    }else{
                        wishlistRecycler.isGone = true
                        emptyText.isVisible = true
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
//                    Toast.makeText(
//                        activity,
//                        "Wish list fetching failed",
//                        Toast.LENGTH_SHORT
//                    ).show()
                }
            }


            override fun onFailure(call: Call<WishListResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }

    override fun addToCart(productId: String?, quantity: String?) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartResponseModel> =
            apiService.addToCart(ApiConstants.LG_APP_KEY,
                token,
                productId,
                quantity)

        call.enqueue(object : Callback<CartResponseModel?> {


            override fun onResponse(call: Call<CartResponseModel?>?, response: Response<CartResponseModel?>) {
                Log.e("Add to cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
                    val status: Boolean = response.body()!!.status
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status) {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        getCartCount()
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
                        getString(R.string.add_cart_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Add to cart", "Adding to cart failed")
                }
            }

            override fun onFailure(call: Call<CartResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun getCartCount() {


        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartCountModel> = apiService.getCartCount(
            ApiConstants.LG_APP_KEY,
            token
        )

        call.enqueue(object : Callback<CartCountModel?> {


            override fun onResponse(
                call: Call<CartCountModel?>?,
                response: Response<CartCountModel?>
            ) {
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        var cartCount = response.body()!!.data?.count
                        var wishListCount = response.body()!!.data?.wishlistCount
                        showToolBar?.updateCartCount(cartCount!!)
                        showToolBar?.updateWalletCount(response.body()!!.data?.wishlistCount!!)


                    } else {
                    }

                } else {

                }
            }


            override fun onFailure(call: Call<CartCountModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }

    fun addToCartBundle(productId: String?, quantity: String?) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartResponseModel> =
            apiService.bundleAddToCart(ApiConstants.LG_APP_KEY,
                token,
                productId,
                quantity)

        call.enqueue(object : Callback<CartResponseModel?> {


            override fun onResponse(call: Call<CartResponseModel?>?, response: Response<CartResponseModel?>) {
                Log.e("Add to cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
                    val status: Boolean = response.body()!!.status
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status) {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        getCartCount()
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
                        getString(R.string.add_cart_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Add to cart", "Adding to cart failed")
                }
            }

            override fun onFailure(call: Call<CartResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }
    override fun addToBundleCart(productId: String?, quantity: String?) {
        addToCartBundle(productId, quantity)
    }

    override fun deleteItem(productId: String?) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.deleteWishList(ApiConstants.LG_APP_KEY,
            token, productId)

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("Delete Response", response.toString() + "")
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        fetchWishlist()
                        getCartCount()
                    }else{
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.wishlist_removed_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }
}