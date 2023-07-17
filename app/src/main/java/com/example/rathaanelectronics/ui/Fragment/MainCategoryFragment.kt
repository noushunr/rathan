package com.example.rathaanelectronics.ui.Fragment

import Filter_Fragment
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.ui.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.*
import com.example.rathaanelectronics.Utils.CustomPager
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.ui.Fragment.DealsCategory.BestDealsFragment
import com.example.rathaanelectronics.Interface.CategoriesTitleItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.example.rathaanelectronics.ui.Adapter.MainCategoryProductsAdapter
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class MainCategoryFragment : Fragment(), CategoriesTitleItemClick,
    MainCategoryProductsAdapter.MainCategoryProductsItemClick {

    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var filterFragment = Filter_Fragment()
    var Hotdeals_data: MutableList<Product> = mutableListOf()
    var allcategory_data: MutableList<AllCategoriesModel.Datum> = mutableListOf()
    private var manager: MyPreferenceManager? = null

    lateinit var viewPager2: CustomPager
    lateinit var tabLayoutCategories: TabLayout
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

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main_category, container, false)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        setHasOptionsMenu(true)

        (activity as MainActivity?)?.settoolbar()
        tabLayoutCategories = view.findViewById<TabLayout>(R.id.tabs2)
        viewPager2 = view.findViewById<View>(R.id.viewpager2) as CustomPager
        ivFilter = view.findViewById(R.id.iv_filter)
        ivCart = view.findViewById(R.id.iv_cart)
        llBadge = view.findViewById(R.id.ll_badge)
        tvCount = view.findViewById(R.id.tv_count)
        ivFilter.setOnClickListener {
            changeFragemnt(filterFragment)
        }
        ivCart.setOnClickListener {
            changeFragemnt(Cart_Fragment())
        }

        getCartCount()
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "124",
            viewLifecycleOwner
        ) { id, data ->
            min = data.getFloat("min")
            max = data.getFloat("max")

        }
        Allcategories()
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

    fun Allcategories() {

        LoadingDialog.showLoadingDialog(requireContext(),"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AllCategoriesModel> = apiService.AllCategories(ApiConstants.LG_APP_KEY)
        call.enqueue(object : Callback<AllCategoriesModel?> {


            override fun onResponse(
                call: Call<AllCategoriesModel?>?,
                response: Response<AllCategoriesModel?>
            ) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        allcategory_data = response.body()!!.data!!

                        Log.e("Hotdeals_data", allcategory_data.size.toString())

//                        catProducts(allcategory_data[0].categoryId.toString())
                    }

                    if (allcategory_data?.size > 0) {
                        if (manager?.locale?.equals("ar")!!) {
                            allcategory_data = allcategory_data?.reversed() as MutableList<AllCategoriesModel.Datum>
                        }
                        setupViewPager2(allcategory_data)
                        tabLayoutCategories.setupWithViewPager(viewPager2)
//                        allcategory_data.get(0).isCategorySelected = true
                    }


                } else {
                }
            }


            override fun onFailure(call: Call<AllCategoriesModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun setupViewPager2(datum: List<AllCategoriesModel.Datum>) {
        val adapter =
            com.example.rathaanelectronics.ui.Adapter.ViewpagerAdapter(childFragmentManager)
        if (manager?.locale?.equals("ar")!!){

            datum?.forEach {
                adapter.addFragment(
                    BestDealsFragment.newInstance(it.categoryId!!, 0, min, max),
                    it.categoryLabelAr
                )
            }
            viewPager2.adapter = adapter
            viewPager2.currentItem = datum?.size
        }else{
            datum?.forEach {
                adapter.addFragment(
                    BestDealsFragment.newInstance(it.categoryId!!, 0, min, max),
                    it.categoryLabel
                )
            }
            viewPager2.adapter = adapter
        }


    }


    fun catProducts(id: String) {

        Hotdeals_data = mutableListOf()

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CategoryProductModel> = apiService.categoryProducts(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), id
        )

        call.enqueue(object : Callback<CategoryProductModel?> {


            override fun onResponse(
                call: Call<CategoryProductModel?>?,
                response: Response<CategoryProductModel?>
            ) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        Hotdeals_data = response.body()!!.data!!.products!!

                        Log.e("Toptwenty_data", Hotdeals_data.size.toString())
                    }


                } else {
                }
            }


            override fun onFailure(call: Call<CategoryProductModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onTitleClicked(position: Int, item: AllCategoriesModel.Datum) {
        allcategory_data.forEachIndexed { index, datum ->
            item.isCategorySelected = position == index
            allcategory_data[index] = item
        }

        catProducts(item.categoryId.toString())
    }

    fun changeFragemnt(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
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

    override fun onAddToWishlistButtonClick(productId: String) {
        addToWishlist(productId)
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
                        getCartCount()
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
                        "Add to wishlist failed",
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