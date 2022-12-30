package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.R
import android.util.Log
import android.view.*

import android.widget.*
import androidx.fragment.app.FragmentTransaction


import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration

import androidx.recyclerview.widget.GridLayoutManager
import com.asksira.loopingviewpager.LoopingViewPager

import com.example.rathaanelectronics.Adapter.*
import com.example.rathaanelectronics.Fragment.DealsCategory.BestDealsFragment
import com.example.rathaanelectronics.Fragment.DealsCategory.HotDealsFragment

import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import com.example.rathaanelectronics.Common.CustomPager
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*

import com.example.rathaanelectronics.Interface.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), SliderClickListener, BestItemClick, HotdealsItemClick,
        NewArrivalItemClick, CategoriesTitleItemClick,
        HomeOfferBannerAdapter.HomeOfferBannerClickListener,TimerItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var Hotdeals_data: List<Product> = ArrayList<Product>()
    var alTimeOffers: List<Data> = ArrayList<Data>()
    var alDealsCategories: List<BestDealCategory.Datum> = ArrayList<BestDealCategory.Datum>()
    lateinit var recycler_deals_offer: RecyclerView
    lateinit var recy_deals_: RecyclerView
    lateinit var recy_time_deals: RecyclerView
    lateinit var recy_offer_deals: RecyclerView
    lateinit var recycler_top_level: RecyclerView
    lateinit var recyclerOfferBanner: RecyclerView
    lateinit var viewPager2: CustomPager
    lateinit var tabLayoutCategories: TabLayout
    lateinit var progressBar : ProgressBar
    private var SliderimagArrayList = ArrayList<String>()
    var slider_data: List<SliderModel.Datum> = ArrayList<SliderModel.Datum>()
    var offerSliderData: MutableList<HomeOfferBannerModel.Data> = mutableListOf()
    private lateinit var lvp_new_slider: LoopingViewPager
    private lateinit var ll_deal_offer_view_all: LinearLayout
    private lateinit var viewPager: CustomPager
    private var manager: MyPreferenceManager? = null
    var allcategory_data: MutableList<AllCategoriesModel.Datum> = mutableListOf()
    private lateinit var tabLayout: TabLayout
    var BestDeal_data: List<Product> = ArrayList<Product>()
    var cart_Fragment = Cart_Fragment()

    var showToolBar : ShowToolBar?=null
    private var Menufilter: MenuItem? = null
//    private var textView_TimeCountDown: TextView? = null

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


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        setHasOptionsMenu(true);


        // countDown()
//        textView_TimeCountDown = view.findViewById<View>(R.id.textView_TimeCountDown) as TextViewll

        tabLayout = view.findViewById<TabLayout>(R.id.tabs)

        viewPager = view.findViewById<View>(R.id.viewpager) as CustomPager


        tabLayoutCategories = view.findViewById<TabLayout>(R.id.tabs2)
        viewPager2 = view.findViewById<View>(R.id.viewpager2) as CustomPager

        lvp_new_slider = view.findViewById<LoopingViewPager>(R.id.lvp_new_slider)
        val fragmenthome = view.findViewById<LinearLayout>(R.id.fragmenthome)
        recycler_top_level = view.findViewById<RecyclerView>(R.id.recycler_main_cat)
        recycler_deals_offer = view.findViewById<RecyclerView>(R.id.recycler_deals_offer)
        recyclerOfferBanner = view.findViewById<RecyclerView>(R.id.recycler_offer_banner)
        ll_deal_offer_view_all = view.findViewById<LinearLayout>(R.id.ll_deal_offer_view_all)
        recy_deals_ = view.findViewById<RecyclerView>(R.id.recy_deals_)
        recy_time_deals = view.findViewById<RecyclerView>(R.id.recy_time_deals)
        recy_offer_deals = view.findViewById<RecyclerView>(R.id.recy_offer_deals)
        progressBar = view.findViewById(R.id.progress_circular)

        val searchProductLayout = view.findViewById<FrameLayout>(R.id.search_layout)
        searchProductLayout.setOnClickListener {
            changeFragemnt(SearchProductFragment())
        }


        val numberOfColumns = 2
        recycler_deals_offer.layoutManager = GridLayoutManager(activity, numberOfColumns)
        recycler_deals_offer.addItemDecoration(
                EqualSpacingItemDecoration(
                        15,
                        EqualSpacingItemDecoration.GRID
                )
        )
        recy_deals_.layoutManager = GridLayoutManager(activity, numberOfColumns)
        recy_deals_.addItemDecoration(
                EqualSpacingItemDecoration(
                        15,
                        EqualSpacingItemDecoration.GRID
                )
        )


        recycler_top_level.layoutManager =
                LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
        //recycler_top_level.adapter = toplevel_cat_list_Adapter

        recy_time_deals.layoutManager =
                LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)

        recy_offer_deals.layoutManager =
                LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
        recy_offer_deals.adapter = Offer_deal_Adapter(activity)

        recyclerOfferBanner.layoutManager = GridLayoutManager(activity, numberOfColumns)

//
        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)

        Allcategories()
        slider()
        offerBanner()
        getBestDealCategories()
        Bestdeal()
        getTimeOffers()
        getNonTimeOffers()

        return view
    }


    override fun onResume() {

        super.onResume()

        if (showToolBar!=null){
            showToolBar?.showToolBar(true)
        }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        showToolBar = context as ShowToolBar
    }

    override fun onDetach() {
        super.onDetach()
        showToolBar = null
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
        showToolBar?.showToolBar(false)
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }


    private fun setupViewPager(viewPager: CustomPager) {
        val adapter = ViewpagerAdapter(childFragmentManager)
        if (manager?.locale?.equals("ar")!!){

            adapter.addFragment(HotDealsFragment.newInstance("3",""), getString(R.string.top20));
            adapter.addFragment(HotDealsFragment.newInstance("2",""), getString(R.string.new_arrival));
            adapter.addFragment(HotDealsFragment.newInstance("1",""), getString(R.string.hotdeals))
            viewPager.adapter = adapter
            viewPager.currentItem = 2
        }else{
            adapter.addFragment(HotDealsFragment.newInstance("1",""), getString(R.string.hotdeals))
            adapter.addFragment(HotDealsFragment.newInstance("2",""), getString(R.string.new_arrival));
            adapter.addFragment(HotDealsFragment.newInstance("3",""), getString(R.string.top20));
            viewPager.adapter = adapter
        }


    }

    private fun setupViewPager2(datum: List<BestDealCategory.Datum>) {
        val adapter = ViewpagerAdapter(childFragmentManager)
        datum?.forEach {
            if (manager?.locale.equals("ar"))
                adapter.addFragment(BestDealsFragment.newInstance(it.categoryId!!,0,-1f,-1f), it.categoryLabelAr)
            else
                adapter.addFragment(BestDealsFragment.newInstance(it.categoryId!!,0,-1f,-1f), it.categoryLabel)
        }
        viewPager2.adapter = adapter
        if (manager?.locale.equals("ar"))
            viewPager2.currentItem = datum.size
    }

    fun slider() {

        progressBar.visibility = View.GONE
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<SliderModel> =
                apiService.Slider(ApiConstants.LG_APP_KEY)

        call.enqueue(object : Callback<SliderModel?> {


            override fun onResponse(call: Call<SliderModel?>?, response: Response<SliderModel?>) {
                Log.e("Signin Response", response.toString() + "")
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true" && messege == "Success") {
                        slider_data = response.body()!!.data

                        for (i in slider_data.indices) {


                            SliderimagArrayList.add(slider_data.get(i).bannerImage)
//
                            Log.e("myads_list", SliderimagArrayList.size.toString() + "")
                            Log.e("image_url", slider_data.get(i).bannerImage)
                        }

                        lvp_new_slider.setAdapter(
                                HomeSliderAdapter(
                                        requireContext(), SliderimagArrayList, true, this@HomeFragment,
                                        slider_data as ArrayList<SliderModel.Datum>
                                )
                        )
//


                    } else {

//                        Toast.makeText(
//                            this@Sign_in_Activity,
//                            messege,
//                            Toast.LENGTH_SHORT
//                        ).show()


                    }
                } else {

                }
            }

            override fun onFailure(call: Call<SliderModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                progressBar.visibility = View.GONE
            }
        })
    }

    fun offerBanner() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<HomeOfferBannerModel> =
                apiService.homeOfferBanner(ApiConstants.LG_APP_KEY)

        call.enqueue(object : Callback<HomeOfferBannerModel?> {


            override fun onResponse(call: Call<HomeOfferBannerModel?>?, response: Response<HomeOfferBannerModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String = response.body()!!.message.toString()

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        offerSliderData = response.body()!!.data!!

                        Log.e("homeBannerImage", offerSliderData[0].homeBannerImage + "")

                        offerSliderData.sortBy {
                            it.homeBannerPrio.toString().toInt()
                        }

                        recyclerOfferBanner.adapter = HomeOfferBannerAdapter(
                                requireActivity(),
                                offerSliderData,
                                this@HomeFragment,
                            manager?.locale.equals("ar",ignoreCase = true)
                        )
//


                    } else {

//                        Toast.makeText(
//                            this@Sign_in_Activity,
//                            messege,
//                            Toast.LENGTH_SHORT
//                        ).show()


                    }
                } else {

                }
            }

            override fun onFailure(call: Call<HomeOfferBannerModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun Bestdeal() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealModel> = apiService.BestDeals(ApiConstants.LG_APP_KEY,token)

        call.enqueue(object : Callback<BestDealModel?> {


            override fun onResponse(
                    call: Call<BestDealModel?>?,
                    response: Response<BestDealModel?>
            ) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        BestDeal_data = response.body()!!.data!!

                        Log.e("Hotdeals_data", BestDeal_data.size.toString())
                    }


                    recycler_deals_offer.adapter =
                            Top_deals_list_Adapter(requireActivity(), BestDeal_data, this@HomeFragment,manager?.locale.equals("ar",ignoreCase = true))


                } else {
                }
            }


            override fun onFailure(call: Call<BestDealModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun getTimeOffers() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<TimerOfferModel> = apiService.timerOffers(ApiConstants.LG_APP_KEY,
                token)
        Log.e("Token", manager?.getGuestToken() + "")
        call.enqueue(object : Callback<TimerOfferModel?> {


            override fun onResponse(call: Call<TimerOfferModel?>?, response: Response<TimerOfferModel?>) {
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    if (status == "true") {
                        alTimeOffers = response.body()!!.data!!
                    }
                    recy_time_deals.adapter = Time_deal_Adapter(
                            requireActivity(),
                            alTimeOffers, 0,this@HomeFragment,manager?.locale.equals("ar")
                    )

                } else {
                }
            }


            override fun onFailure(call: Call<TimerOfferModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun getNonTimeOffers() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<TimerOfferModel> = apiService.nonTimerOffers(ApiConstants.LG_APP_KEY,
                token)
        Log.e("Token", manager?.getGuestToken() + "")
        call.enqueue(object : Callback<TimerOfferModel?> {


            override fun onResponse(call: Call<TimerOfferModel?>?, response: Response<TimerOfferModel?>) {
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    if (status == "true") {
                        alTimeOffers = response.body()!!.data!!
                    }
                    recy_offer_deals.adapter = Time_deal_Adapter(
                            requireActivity(),
                            alTimeOffers,1,this@HomeFragment,manager?.locale.equals("ar")
                    )

                } else {
                }
            }


            override fun onFailure(call: Call<TimerOfferModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun getBestDealCategories() {
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealCategory> = apiService.getBestDealCategories(ApiConstants.LG_APP_KEY,
            token)
        Log.e("Token", manager?.getGuestToken() + "")
        call.enqueue(object : Callback<BestDealCategory?> {


            override fun onResponse(call: Call<BestDealCategory?>?, response: Response<BestDealCategory?>) {
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    if (status == "true") {
                        alDealsCategories = response.body()!!.data!!
                        if (manager?.locale.equals("ar"))
                            alDealsCategories = alDealsCategories.reversed()
                        manager?.min = -1f
                        manager?.max = -1f
                        setupViewPager2(alDealsCategories)
                        tabLayoutCategories.setupWithViewPager(viewPager2)

                    }


                } else {
                }
            }


            override fun onFailure(call: Call<BestDealCategory?>?, t: Throwable?) {
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
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                HomeFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onSliderClicked(position: Int, item: SliderModel.Datum) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.bannerUrl.toString())
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("home", e.message, e)
        }
    }

    override fun onBestItemClicked(position: Int, item: Product?) {
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

    override fun onAddToWishListButtonClicked(productId: String) {
        addToWishlist(productId)

    }

    override fun onHotdealsClicked(position: Int, item: Product?) {
        showToolBar?.showToolBar(false)
        //val subcategory = MainCategoryFragment()
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

    override fun onHotdealsClicked(position: Int, item: Data?) {
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

    override fun onNewArrivalClicked(position: Int, item: NewArrivalModel.Datum?) {

    }

    override fun onAddToWishListButtonClick(productId: String) {
        addToWishlist(productId)
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {

        removeFromWishlist(productId)
    }

    fun Allcategories() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AllCategoriesModel> = apiService.AllCategories(ApiConstants.LG_APP_KEY)
        call.enqueue(object : Callback<AllCategoriesModel?> {


            override fun onResponse(call: Call<AllCategoriesModel?>?, response: Response<AllCategoriesModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        allcategory_data = response.body()!!.data!!

                        Log.e("Hotdeals_data", allcategory_data.size.toString())
                    }


                    /*recy_cat_title.adapter = MainCat_title_list_Adapter(
                        requireActivity(),
                        allcategory_data,
                        this@MainCategoryFragment
                    )*/

                    allcategory_data.sortBy {
                        it.categoryPrio.toString().toInt()
                    }

                    recycler_top_level.adapter = toplevel_cat_list_Adapter(
                            requireActivity(),
                            allcategory_data,
                            this@HomeFragment,
                        manager?.locale.equals("ar")
                    )


                } else {
                }
            }


            override fun onFailure(call: Call<AllCategoriesModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    override fun onTitleClicked(position: Int, item: AllCategoriesModel.Datum) {
        showToolBar?.showToolBar(false)
        manager?.min = -1f
        manager?.max = -1f
        val subcategory = SubCategory_Fragment()
        val args = Bundle()
        if (manager?.locale.equals("ar"))
            args.putString("name", item.categoryLabelAr.toString())
        else
            args.putString("name", item.categoryLabel.toString())
        args.putString("id", item.categoryId.toString())
        subcategory.setArguments(args)
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, subcategory)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    override fun onOfferBannerClick(position: Int, item: HomeOfferBannerModel.Data) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.homeBannerUrl.toString())
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("home", e.message, e)
        }
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
        val call: Call<CommonResponseModel> = apiService.addToWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
//                progressBar.visibility = View.GONE
                Log.e("Add wishlist response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        val toast = Toast.makeText(context, "Wish list added successfully", Toast.LENGTH_LONG)
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
                            recycler_deals_offer.adapter?.notifyItemChanged(position)
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
                        "Add to wishlist failed",
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
                        val toast = Toast.makeText(context, "Wish list removed successfully", Toast.LENGTH_LONG)
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
                            recycler_deals_offer.adapter?.notifyItemChanged(position)
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
                        "Add to wishlist failed",
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