package com.example.rathaanelectronics.ui.Fragment

import Filter_Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.ui.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.*

import com.example.rathaanelectronics.Utils.CustomPager
import com.example.rathaanelectronics.Utils.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Interface.BestItemClick
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.example.rathaanelectronics.ui.Adapter.MainCategoryProductsAdapter
import com.example.rathaanelectronics.ui.Adapter.Top_deals_list_Adapter
import com.example.rathaanelectronics.ui.Fragment.DealsCategory.BestDealsFragment
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "name"
private const val ARG_PARAM2 = "id"

/**
 * A simple [Fragment] subclass.
 * Use the [SubCategory_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubCategory_Fragment : Fragment(), BestItemClick,HotdealsItemClick,
    MainCategoryProductsAdapter.MainCategoryProductsItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var filterFragment = Filter_Fragment()
    lateinit var recycle_subcat:RecyclerView
    var BestDeal_data: List<BestDealModel.Datum> = ArrayList<BestDealModel.Datum>()
    var Hotdeals_data: MutableList<Product> = mutableListOf()
    var alSubCategories: List<CategoryProductModel.SubCategory> = ArrayList<CategoryProductModel.SubCategory>()
    private var manager: MyPreferenceManager? = null
    lateinit var viewPager2: CustomPager
    lateinit var tabLayoutCategories: TabLayout
    lateinit var progressBar : ProgressBar
    lateinit var ivFilter: ImageView
    lateinit var ivCart: ImageView
    private lateinit var llBadge: LinearLayout
    private lateinit var tvCount: TextView
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

        val view = inflater.inflate(R.layout.fragment_sub_category_, container, false)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        val title = view.findViewById<TextView>(R.id.frag_sub_cat_name_txt)
        ivFilter = view.findViewById(R.id.iv_filter)
        ivCart = view.findViewById(R.id.iv_cart)
        llBadge = view.findViewById(R.id.ll_badge)
        tvCount = view.findViewById(R.id.tv_count)
        title.text = param1
        ivFilter.setOnClickListener {
            changeFragemnt(filterFragment)
        }
        ivCart.setOnClickListener {
            changeFragemnt(Cart_Fragment())
        }
        requireActivity().supportFragmentManager.setFragmentResultListener("124",viewLifecycleOwner){ id,data ->
            min = data.getFloat("min")
            max = data.getFloat("max")

        }
        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
//        manager?.min = -1f
//        manager?.max = -1f
        recycle_subcat = view.findViewById<RecyclerView>(R.id.recycle_subcat)
        tabLayoutCategories = view.findViewById<TabLayout>(R.id.tabs2)
        viewPager2 = view.findViewById<View>(R.id.viewpager2) as CustomPager
        progressBar = view.findViewById(R.id.progress_circular)

        val numberOfColumns = 2
        recycle_subcat.layoutManager = GridLayoutManager(activity, numberOfColumns)
        recycle_subcat.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )

        getCartCount()
        catProducts(param2.toString())
        //Bestdeal()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(false)
        this.Menufilter = menu.findItem(R.id.filter).setVisible(false)

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



    fun catProducts(id: String) {

        progressBar.visibility = View.VISIBLE
        Hotdeals_data = mutableListOf()
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!

        recycle_subcat.adapter= Top_deals_list_Adapter(requireActivity(),Hotdeals_data,this@SubCategory_Fragment,manager?.locale.equals("ar",ignoreCase = true))

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CategoryProductModel> = apiService.categoryProducts(ApiConstants.LG_APP_KEY,
            token, id)

        call.enqueue(object : Callback<CategoryProductModel?> {


            override fun onResponse(call: Call<CategoryProductModel?>?, response: Response<CategoryProductModel?>) {
                Log.e("Signin Response", response.toString() + "")
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    try {
                        val status: String = response.body()!!.status.toString()
                        val messege: String? = response.body()!!.message

                        Log.e("status", status.toString() + "")
                        Log.e("messege", messege.toString() + "")

                        if (status == "true") {
                            Hotdeals_data = response.body()!!.data!!.products!!
                            alSubCategories = response.body()?.data?.subcategories!!
                            Log.e("Toptwenty_data", Hotdeals_data.size.toString())
                        }

                        if (alSubCategories.isNullOrEmpty()){
                            var products : MutableList<Product> = mutableListOf()
                            if (min!=-1f && max!=-1f){
                                Hotdeals_data?.forEach {
                                    if (!it.productSellPrice.isNullOrEmpty()){
                                        if (it.productSellPrice!!.toFloat() in min..max){
                                            products.add(it)
                                        }
                                    }else{
                                        products.add(it)
                                    }
                                }
                            }else{
                                products.addAll(Hotdeals_data)
                            }
                            tabLayoutCategories.visibility = View.GONE
                            viewPager2.visibility = View.GONE
                            recycle_subcat.visibility = View.VISIBLE
                            recycle_subcat.adapter= Top_deals_list_Adapter(requireActivity(),products,this@SubCategory_Fragment,manager?.locale.equals("ar",ignoreCase = true))
                        }else{
                            tabLayoutCategories.visibility = View.VISIBLE
                            viewPager2.visibility = View.VISIBLE
                            recycle_subcat.visibility = View.GONE
                            if (manager?.locale.equals("ar"))
                                alSubCategories = alSubCategories.reversed()
                            setupViewPager2(alSubCategories)
                            tabLayoutCategories.setupWithViewPager(viewPager2)
                        }




                    }catch (e:Exception){

                    }



                } else {
                }
            }


            override fun onFailure(call: Call<CategoryProductModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                progressBar.visibility = View.GONE
            }
        })
    }
//
//    fun Bestdeal() {
//
//        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
//        val call: Call<BestDealModel> = apiService.BestDeals(ApiConstants.LG_APP_KEY,manager?.guestToken)
//
//        call.enqueue(object : Callback<BestDealModel?> {
//
//
//            override fun onResponse(call: Call<BestDealModel?>?, response: Response<BestDealModel?>) {
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
//                        BestDeal_data = response.body()!!.data!!
//
//
//                        Log.e("Hotdeals_data", BestDeal_data.size.toString())
//                    }
//
//
//                    recycle_subcat.adapter= Subcat_list_Adapter(requireActivity(),BestDeal_data,this@SubCategory_Fragment)
//
//
//
//
//                } else {
//                }
//            }
//
//
//            override fun onFailure(call: Call<BestDealModel?>?, t: Throwable?) {
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
         * @return A new instance of fragment SubCategory_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubCategory_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onBestItemClicked(position: Int, item: Product?) {

        changeFragemnt(Product_Detail_view_Fragment())

    }

    override fun onAddToWishListButtonClicked(productId: String) {
    }

    override fun onProductClicked(position: Int, item: Product) {
        val bundle = Bundle()
        bundle.putString("productId", item.productId)
        val subcategory = Product_Detail_view_Fragment()
        subcategory.arguments = bundle
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, subcategory)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
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
        LoadingDialog.showLoadingDialog(requireContext(),"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<WishlistModel> = apiService.addToWishList(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), productId
        )

        call.enqueue(object : Callback<WishlistModel?> {


            override fun onResponse(
                call: Call<WishlistModel?>?,
                response: Response<WishlistModel?>
            ) {
                Log.e("Add wishlist response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
                    val status: String = response.body()!!.status.toString()
                    var message: String? = response.body()!!.message
                    if (response.body()?.data!=null){
                        message = if (manager?.locale.equals("en")){ response.body()!!.data?.message } else{
                            response.body()!!.data?.messageAr
                        }
                    }

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
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

    private fun setupViewPager2(datum: List<CategoryProductModel.SubCategory>) {
        val adapter =
            com.example.rathaanelectronics.ui.Adapter.ViewpagerAdapter(childFragmentManager)
        if (manager?.locale.equals("ar")){
            datum?.forEach {
                adapter.addFragment(BestDealsFragment.newInstance(it.subcategory_id!!,1,min,max), it.subcategory_name_ar)
            }
            viewPager2.adapter = adapter
            viewPager2.currentItem = datum.size
        }else{
            datum?.forEach {
                adapter.addFragment(BestDealsFragment.newInstance(it.subcategory_id!!,1,min,max), it.subcategory_name)
            }
            viewPager2.adapter = adapter
        }

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
                        if (cartCount!! > 0) {
                            llBadge.visibility = View.VISIBLE
                            tvCount.text = "$cartCount"

                        } else {
                            llBadge.visibility = View.GONE
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