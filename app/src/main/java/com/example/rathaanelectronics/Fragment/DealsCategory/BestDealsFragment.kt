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
import com.example.rathaanelectronics.Adapter.BestDeal_list_Adapter
import com.example.rathaanelectronics.Adapter.Top_deals_list_Adapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Fragment.Product_Detail_view_Fragment
import com.example.rathaanelectronics.Interface.BestItemClick
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
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
 * Use the [HotDealsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BestDealsFragment : Fragment(), BestItemClick,HotdealsItemClick {

    // TODO: Rename and change types of parameters

    private var categoryId: String? = null
    private var type: Int = 0
    lateinit var hot_deals: RecyclerView
    var BestDeal_data: MutableList<Product> = ArrayList<Product>()
    private var manager: MyPreferenceManager? = null
    lateinit var progressBar : ProgressBar
    var isArabic = false
    var min = -1f
    var max = -1f

    var showToolBar : ShowToolBar?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)

        showToolBar = context as ShowToolBar
    }

    override fun onDetach() {
        super.onDetach()
        showToolBar = null
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            categoryId = it.getString(ARG_PARAM1)
            type = it.getInt(ARG_PARAM2)
//            min = it.getFloat("min")
//            max = it.getFloat("max")


        }
        manager = MyPreferenceManager(activity)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view = inflater!!.inflate(R.layout.fragment_hot_deals, container, false)

        isArabic = manager?.locale.equals("ar",ignoreCase = true)

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




        return view
    }

    override fun onResume() {
        super.onResume()
        min = manager?.min!!
        max = manager?.max!!
        if(type == 0){
            Bestdeal()
        }else{

            getSubcategoryProducts()
        }
    }


    fun Bestdeal() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        progressBar.visibility = View.VISIBLE
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealModel> = apiService.BestDealsCatWise(ApiConstants.LG_APP_KEY,token,categoryId)

        call.enqueue(object : Callback<BestDealModel?> {


            override fun onResponse(call: Call<BestDealModel?>?, response: Response<BestDealModel?>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message
                    if (status == "true") {
                        BestDeal_data.clear()
                        if (min!=-1f && max!=-1f){
                            response.body()!!.data!!?.forEach {
                                if (!it.productSellPrice.isNullOrEmpty()){
                                    if (it.productSellPrice!!.toFloat() in min..max){
                                        BestDeal_data.add(it)
                                    }
                                }
                            }
                        }else{
                            BestDeal_data.addAll( response.body()!!.data!!)
                        }
//                        BestDeal_data = response.body()!!.data!!

                    }


                    hot_deals.adapter= Top_deals_list_Adapter(requireActivity(),BestDeal_data,this@BestDealsFragment,isArabic)




                } else {
                }
            }


            override fun onFailure(call: Call<BestDealModel?>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun getSubcategoryProducts() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        progressBar.visibility = View.VISIBLE
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<SubCategoryProducts> = apiService.getSubcategoryProducts(ApiConstants.LG_APP_KEY,token,categoryId)

        call.enqueue(object : Callback<SubCategoryProducts?> {


            override fun onResponse(call: Call<SubCategoryProducts?>?, response: Response<SubCategoryProducts?>) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message
                    if (status == "true") {
                        if (response.body()!!.data!=null && response.body()!!.data?.data!=null) {
//                            BestDeal_data = response.body()!!.data?.data!!
                            BestDeal_data.clear()
                            if (min!=-1f && max!=-1f){
                                response.body()!!.data?.data!!?.forEach {
                                    if (!it.productSellPrice.isNullOrEmpty()){
                                        if (it.productSellPrice!!.toFloat() in min..max){
                                            BestDeal_data.add(it)
                                        }
                                    }
                                }
                            }else{
                                BestDeal_data.addAll( response.body()!!.data?.data!!)
                            }
                        }


//                        Log.e("Hotdeals_data", BestDeal_data.size.toString())
                    }


                    hot_deals.adapter= Top_deals_list_Adapter(requireActivity(),BestDeal_data,this@BestDealsFragment,isArabic)




                } else {
                }
            }


            override fun onFailure(call: Call<SubCategoryProducts?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                progressBar.visibility = View.GONE
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
        fun newInstance(param1: String,type:Int,min:Float,max:Float) =
            BestDealsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putInt(ARG_PARAM2, type)
                    putFloat("min", min)
                    putFloat("max", max)
                }
            }
    }

    override fun onBestItemClicked(position: Int, item: Product?) {

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

    override fun onAddToWishListButtonClicked(productId: String) {

        addToWishlist(productId)
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
                Log.e("Add wishlist response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        val toast = Toast.makeText(context, getString(R.string.wishlist_added), Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        getCartCount()
                        var position = 0
                        BestDeal_data?.forEachIndexed { index, product ->
                            if (productId == product.productId){
                                position = index
                                BestDeal_data[index].wishlistExist = 1
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
                Log.e("Add wishlist response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        val toast = Toast.makeText(context, getString(R.string.wishlist_removed), Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        var position = 0
                        getCartCount()
                        BestDeal_data?.forEachIndexed { index, product ->
                            if (productId == product.productId){
                                position = index
                                BestDeal_data[index].wishlistExist = 0
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
        addToWishlist(productId)
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {
        removeFromWishlist(productId)
    }

}