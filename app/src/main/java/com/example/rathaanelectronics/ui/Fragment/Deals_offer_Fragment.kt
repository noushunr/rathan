package com.example.rathaanelectronics.ui.Fragment

import Filter_Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import com.example.rathaanelectronics.ui.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.*

import com.example.rathaanelectronics.Utils.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Interface.BestItemClick
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Interface.TimerItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.example.rathaanelectronics.databinding.FragmentDealsOfferBinding
import com.example.rathaanelectronics.ui.Adapter.DelasOfferAdapter
import com.example.rathaanelectronics.ui.Adapter.Top_deals_list_Adapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategory_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Deals_offer_Fragment : Fragment(), BestItemClick,HotdealsItemClick,TimerItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var filterFragment = Filter_Fragment()
    var BestDeal_data: List<Product> = ArrayList<Product>()
    var filtredData: MutableList<Product> = ArrayList<Product>()
    private var manager: MyPreferenceManager? = null
    private lateinit var binding: FragmentDealsOfferBinding
    var alTimeOffers: MutableList<Data> = ArrayList<Data>()

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

        binding = FragmentDealsOfferBinding.inflate(inflater,container,false)

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        binding.ivBack.setOnClickListener { activity?.onBackPressed() }
        binding.ivFilter.setOnClickListener {
            changeFragemnt(filterFragment)
        }
        binding.ivCart.setOnClickListener {
            changeFragemnt(Cart_Fragment())
        }
        requireActivity().supportFragmentManager.setFragmentResultListener("124",viewLifecycleOwner){ id,data ->
            min = data.getFloat("min")
            max = data.getFloat("max")

        }

        val numberOfColumns = 2
        binding.recycleSubcat.layoutManager = GridLayoutManager(activity, numberOfColumns)
        binding.recycleSubcat.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )
        binding.recycleSubcat.adapter

        getCartCount()
        if (param1.equals("0")) {
            Bestdeal()
            binding.tvTitle.setText(getString(R.string.best_sellers))
        }
        else {
            dealsOffer()
            binding.tvTitle.setText(getString(R.string.deals_offer))
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.filter).setVisible(false)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                changeFragemnt(filterFragment)
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

    fun Bestdeal() {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealModel> = apiService.BestDeals(ApiConstants.LG_APP_KEY, token)

        call.enqueue(object : Callback<BestDealModel?> {


            override fun onResponse(call: Call<BestDealModel?>?, response: Response<BestDealModel?>) {
                Log.e("Signin Response", response.toString() + "")
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        BestDeal_data = response.body()!!.data!!

                        Log.e("Hotdeals_data", BestDeal_data.size.toString())
                    }
                    filtredData.clear()
                    if (min!=-1f && max!=-1f){
                        BestDeal_data?.forEach {
                            if (!it.productSellPrice.isNullOrEmpty()){
                                if (it.productSellPrice!!.toFloat() in min..max){
                                    filtredData.add(it)
                                }
                            }
                        }
                    }else{
                        filtredData.addAll(BestDeal_data)
                    }

                    binding.recycleSubcat.adapter= Top_deals_list_Adapter(requireActivity(),filtredData,this@Deals_offer_Fragment,manager?.locale.equals("ar",ignoreCase = true))




                } else {
                }
            }


            override fun onFailure(call: Call<BestDealModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                LoadingDialog.cancelLoading()
            }
        })
    }

    fun dealsOffer() {

        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<TimerOfferModel> = apiService.dealsAndOffers(ApiConstants.LG_APP_KEY, token)

        call.enqueue(object : Callback<TimerOfferModel?> {


            override fun onResponse(call: Call<TimerOfferModel?>?, response: Response<TimerOfferModel?>) {
                Log.e("Signin Response", response.toString() + "")
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        alTimeOffers.clear()
                        if (min!=-1f && max!=-1f){
                            response.body()!!.data!!?.forEach {
                                if (!it.productSellPrice.isNullOrEmpty()){
                                    if (it.productSellPrice!!.toFloat() in min..max){
                                        alTimeOffers.add(it)
                                    }
                                }
                            }
                        }else{
                            alTimeOffers.addAll( response.body()!!.data!!)
                        }


                        Log.e("Hotdeals_data", BestDeal_data.size.toString())
                    }


                    binding.recycleSubcat.adapter= DelasOfferAdapter(
                        requireActivity(),
                        alTimeOffers, 0,this@Deals_offer_Fragment, manager?.locale.equals("ar",ignoreCase = true)
                    )




                } else {
                }
            }


            override fun onFailure(call: Call<TimerOfferModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
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
         * @return A new instance of fragment SubCategory_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Deals_offer_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onBestItemClicked(position: Int, item: Product?) {
        val bundle = Bundle()
        bundle.putString("productId", item?.productId)
        val productDetailFragment = Product_Detail_view_Fragment()
        productDetailFragment.arguments = bundle
        changeFragemnt(productDetailFragment)

    }

    override fun onAddToWishListButtonClicked(productId: String) {
    }

    override fun onHotdealsClicked(position: Int, item: Product?) {
        val bundle = Bundle()
        bundle.putString("productId", item?.productId)
        val productDetailFragment = Product_Detail_view_Fragment()
        productDetailFragment.arguments = bundle
        changeFragemnt(productDetailFragment)
    }

    override fun onHotdealsClicked(position: Int, item: Data?) {
        val bundle = Bundle()
        bundle.putString("productId", item?.productId)
        val productDetailFragment = Product_Detail_view_Fragment()
        productDetailFragment.arguments = bundle
        changeFragemnt(productDetailFragment)
    }

    override fun onAddToWishlistButtonClick(productId: String) {
        addToWishlist(productId)
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {
        removeFromWishlist(productId)
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

    fun addToWishlist(productId: String) {
//        progressBar.visibility = View.VISIBLE
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<WishlistModel> = apiService.addToWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<WishlistModel?> {


            override fun onResponse(
                call: Call<WishlistModel?>?,
                response: Response<WishlistModel?>
            ) {
//                progressBar.visibility = View.GONE
                Log.e("Add wishlist response", response.toString() + "")
                LoadingDialog.cancelLoading()
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
                            binding.recycleSubcat.adapter?.notifyItemChanged(position)
                        }
                        alTimeOffers?.forEachIndexed { index, product ->
                            if (productId == product.productId){
                                position = index
                                BestDeal_data[index].wishlistExist = 1
                                return@forEachIndexed
                            }
                            binding.recycleSubcat.adapter?.notifyItemChanged(position)
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


            override fun onFailure(call: Call<WishlistModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun removeFromWishlist(productId: String) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
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
                Log.e("Add wishlist response", response.toString() + "")
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        val toast = Toast.makeText(context, getString(R.string.wishlist_removed), Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                        getCartCount()
                        var position = 0
                        BestDeal_data?.forEachIndexed { index, product ->
                            if (productId == product.productId){
                                position = index
                                BestDeal_data[index].wishlistExist = 0
                                return@forEachIndexed
                            }
                            binding.recycleSubcat.adapter?.notifyItemChanged(position)
                        }
                        alTimeOffers?.forEachIndexed { index, product ->
                            if (productId == product.productId){
                                position = index
                                BestDeal_data[index].wishlistExist = 0
                                return@forEachIndexed
                            }
                            binding.recycleSubcat.adapter?.notifyItemChanged(position)
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
                        showToolBar?.updateCartCount(cartCount!!)
                        showToolBar?.updateWalletCount(response.body()!!.data?.wishlistCount!!)
                        if (cartCount!! > 0) {
                            binding.llBadge.visibility = View.VISIBLE
                            binding.tvCount.text = "$cartCount"


                        } else {

                            binding.llBadge.visibility = View.GONE
                        }

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