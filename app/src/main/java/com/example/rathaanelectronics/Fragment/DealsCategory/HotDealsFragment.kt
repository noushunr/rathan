package com.example.rathaanelectronics.Fragment.DealsCategory

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.Top_deals_list_Adapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Fragment.MainCategoryFragment
import com.example.rathaanelectronics.Fragment.Product_Detail_view_Fragment
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CartCountModel
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.Model.Product
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import kotlinx.android.synthetic.main.fragment_home.*
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
class HotDealsFragment : Fragment(), HotdealsItemClick {

    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    lateinit var hot_deals: RecyclerView
    var Hotdeals_data: List<Product> = ArrayList<Product>()
    private var manager: MyPreferenceManager? = null
    lateinit var progressBar : ProgressBar
    var showToolBar : ShowToolBar?=null


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

        hot_deals = view.findViewById<View>(R.id.hot_deals) as RecyclerView
        progressBar = view.findViewById(R.id.progress_circular)
        val numberOfColumns = 2
        hot_deals.layoutManager = GridLayoutManager(activity, numberOfColumns)
        hot_deals.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )

        progressBar.visibility = View.VISIBLE
        Hotdeal()

        return view
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)

        showToolBar = context as ShowToolBar
    }

    override fun onDetach() {
        super.onDetach()
        showToolBar = null
    }

    fun Hotdeal() {

        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
//        var loadingDialogue = LoadingDialogue()
//        loadingDialogue?.showLoadingDialog(context,"Loading..")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        var call: Call<DealsModel>?= null
        if (param1.equals("1")){
            call = apiService.HotDeals(ApiConstants.LG_APP_KEY,
                token)
        }else if (param1.equals("2")){
            call = apiService.NewArrivals(ApiConstants.LG_APP_KEY,
                token)
        }else if (param1.equals("3")){
            call = apiService.TopTwenty(ApiConstants.LG_APP_KEY,
                token)
        }

        call?.enqueue(object : Callback<DealsModel?> {


            override fun onResponse(call: Call<DealsModel?>?, response: Response<DealsModel?>) {
//                loadingDialogue.cancelLoading()
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    if (status == "true") {
                        Hotdeals_data = response.body()!!.data!!

                    }


                    hot_deals.adapter = Top_deals_list_Adapter(
                        requireActivity(),
                        Hotdeals_data,
                        this@HotDealsFragment,
                        manager?.locale.equals("ar",ignoreCase = true)
                    )


                } else {
                }
            }


            override fun onFailure(call: Call<DealsModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                progressBar.visibility = View.GONE
//                loadingDialogue?.ca
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
            HotDealsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onHotdealsClicked(position: Int, item: Product?) {
//        val subcategory = MainCategoryFragment()
//        val transaction = activity?.supportFragmentManager?.beginTransaction()
//        transaction?.replace(R.id.frame, subcategory)
//        transaction?.addToBackStack(null)
//        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction?.commit()

        showToolBar?.showToolBar(false)

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
        addToWishlist(productId)
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {
        removeFromWishlist(productId)
    }

    fun addToWishlist(productId: String) {
        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.addToWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    if (status == "true") {
                        val toast = Toast.makeText(context, getString(R.string.wishlist_added), Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        getCartCount()
                        var position = 0
                        Hotdeals_data?.forEachIndexed { index, product ->
                            if (productId == product.productId){
                                position = index
                                Hotdeals_data[index].wishlistExist = 1
                                return@forEachIndexed
                            }
                            hot_deals.adapter?.notifyItemChanged(position)
                        }
//                        Toast.makeText(
//                            activity,
//                            "Wish list added successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
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
                        getString(R.string.wishlist_added_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                progressBar.visibility = View.GONE
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun removeFromWishlist(productId: String) {
        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.deleteWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
                    if (status == "true") {
                        val toast = Toast.makeText(context, getString(R.string.wishlist_removed), Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        getCartCount()
                        var position = 0
                        Hotdeals_data?.forEachIndexed { index, product ->
                            if (productId == product.productId){
                                position = index
                                Hotdeals_data[index].wishlistExist = 0
                                return@forEachIndexed
                            }
                            hot_deals.adapter?.notifyItemChanged(position)
                        }
//                        Toast.makeText(
//                            activity,
//                            "Wish list added successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
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
                        getString(R.string.wishlist_removed_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                progressBar.visibility = View.GONE
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
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
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
}