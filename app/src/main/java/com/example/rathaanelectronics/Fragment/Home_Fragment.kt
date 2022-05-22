package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.R
import android.provider.Settings
import android.util.Log
import android.view.*

import android.widget.*
import androidx.fragment.app.FragmentTransaction


import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration

import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager
import com.asksira.loopingviewpager.LoopingViewPager

import com.example.rathaanelectronics.Adapter.*
import com.example.rathaanelectronics.Common.WrapViewPager
import com.example.rathaanelectronics.Fragment.DealsCategory.BestDealsFragment
import com.example.rathaanelectronics.Fragment.DealsCategory.HotDealsFragment
import com.example.rathaanelectronics.Fragment.DealsCategory.NewArrivalsFragment
import com.example.rathaanelectronics.Fragment.DealsCategory.TopTwentyFragment

import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList
import android.widget.RadioGroup
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
        HomeOfferBannerAdapter.HomeOfferBannerClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var Hotdeals_data: List<DealsModel.Datum> = ArrayList<DealsModel.Datum>()
    var alTimeOffers: List<Data> = ArrayList<Data>()
    var alDealsCategories: List<BestDealCategory.Datum> = ArrayList<BestDealCategory.Datum>()
    lateinit var recycler_deals_offer: RecyclerView
    lateinit var recy_deals_: RecyclerView
    lateinit var recy_time_deals: RecyclerView
    lateinit var recy_offer_deals: RecyclerView
    lateinit var recycler_top_level: RecyclerView
    lateinit var recyclerOfferBanner: RecyclerView
    lateinit var viewPager2: WrapViewPager
    lateinit var tabLayoutCategories: TabLayout
    private var SliderimagArrayList = ArrayList<String>()
    var slider_data: List<SliderModel.Datum> = ArrayList<SliderModel.Datum>()
    var offerSliderData: MutableList<HomeOfferBannerModel.Data> = mutableListOf()
    private lateinit var lvp_new_slider: LoopingViewPager
    private lateinit var ll_deal_offer_view_all: LinearLayout
    private lateinit var viewPager: WrapViewPager
    private lateinit var deals_radiogroup: RadioGroup
    private lateinit var HotDeals: RadioButton
    private lateinit var New_Arrival: RadioButton
    private lateinit var Top20: RadioButton
    var NewArrival_data: List<NewArrivalModel.Datum> = ArrayList<NewArrivalModel.Datum>()
    private var manager: MyPreferenceManager? = null
    var allcategory_data: MutableList<AllCategoriesModel.Datum> = mutableListOf()


    private lateinit var tabLayout: TabLayout
    var BestDeal_data: List<BestDealModel.Datum> = ArrayList<BestDealModel.Datum>()
    var cart_Fragment = Cart_Fragment()

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

        if (manager?.getGuestToken()!!.isEmpty()) {
            getGuestToken()
        }
        // countDown()
//        textView_TimeCountDown = view.findViewById<View>(R.id.textView_TimeCountDown) as TextViewll

        tabLayout = view.findViewById<TabLayout>(R.id.tabs)
        Top20 = view.findViewById<RadioButton>(R.id.Top20)
        New_Arrival = view.findViewById<RadioButton>(R.id.New_Arrival)
        HotDeals = view.findViewById<RadioButton>(R.id.HotDeals)

        viewPager = view.findViewById<View>(R.id.viewpager) as WrapViewPager
        deals_radiogroup = view.findViewById<View>(R.id.deals_radiogroup) as RadioGroup


        tabLayoutCategories = view.findViewById<TabLayout>(R.id.tabs2)
        viewPager2 = view.findViewById<View>(R.id.viewpager2) as WrapViewPager

        lvp_new_slider = view.findViewById<LoopingViewPager>(R.id.lvp_new_slider)
        val fragmenthome = view.findViewById<LinearLayout>(R.id.fragmenthome)
        recycler_top_level = view.findViewById<RecyclerView>(R.id.recycler_main_cat)
        recycler_deals_offer = view.findViewById<RecyclerView>(R.id.recycler_deals_offer)
        recyclerOfferBanner = view.findViewById<RecyclerView>(R.id.recycler_offer_banner)
        ll_deal_offer_view_all = view.findViewById<LinearLayout>(R.id.ll_deal_offer_view_all)
        recy_deals_ = view.findViewById<RecyclerView>(R.id.recy_deals_)
        recy_time_deals = view.findViewById<RecyclerView>(R.id.recy_time_deals)
        recy_offer_deals = view.findViewById<RecyclerView>(R.id.recy_offer_deals)
        HotDeals.isChecked = true

        //val toplevel_cat_list_Adapter = toplevel_cat_list_Adapter(activity)


//        val top_deals_list_Adapter = Top_deals_list_Adapter(
//            activity,
//            Hotdeals_data,
//            this@HotDealsFragment
//        )
        //  val best_deals_list_Adapter = Best_deals_list_Adapter(activity)

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


        setupViewPager(viewPager)
        tabLayout.setupWithViewPager(viewPager)



        Allcategories()
        slider()
        offerBanner()
        getBestDealCategories()
        Bestdeal()

        HotDeals.setTextColor(Color.parseColor("#D62737"))
        New_Arrival.setTextColor(Color.BLACK)
        Top20.setTextColor(Color.BLACK)
        Hotdeal()
        getTimeOffers()
        getNonTimeOffers()


        ll_deal_offer_view_all.setOnClickListener {
            changeFragemnt(Deals_offer_Fragment())
        }



        deals_radiogroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.HotDeals -> {


                    HotDeals.setTextColor(Color.parseColor("#D62737"))
                    New_Arrival.setTextColor(Color.BLACK)
                    Top20.setTextColor(Color.BLACK)
                    Hotdeal()
                }
                R.id.New_Arrival -> {

                    New_Arrival.setTextColor(Color.parseColor("#D62737"))
                    HotDeals.setTextColor(Color.BLACK)
                    Top20.setTextColor(Color.BLACK)
                    NewArrivals()
                }
                R.id.Top20 -> {
                    Top20.setTextColor(Color.parseColor("#D62737"))
                    New_Arrival.setTextColor(Color.BLACK)
                    HotDeals.setTextColor(Color.BLACK)
                    TopTwenty()
                }
            }
        }
        return view
    }

    override fun onResume() {

        super.onResume()


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(true)
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


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewpagerAdapter(childFragmentManager)

        adapter.addFragment(HotDealsFragment(), "Hot deals")
        adapter.addFragment(NewArrivalsFragment(), "New Arrival");
        adapter.addFragment(TopTwentyFragment(), "Top 20");
        viewPager.adapter = adapter
    }

    private fun setupViewPager2(datum: List<BestDealCategory.Datum>) {
        val adapter = ViewpagerAdapter(childFragmentManager)
        datum?.forEach {
            adapter.addFragment(BestDealsFragment.newInstance(it.categoryId!!), it.categoryLabel)
        }
        viewPager2.adapter = adapter
    }


//    private fun countDown() {
//        val countDownTimer = object : CountDownTimer(1584700200, 1000) {
//            override fun onTick(p0: Long) {
//                val millis: Long = p0
//                val hms = String.format(
//                    "%02d:%02d:%02d:%02d",
//                    TimeUnit.HOURS.toDays(TimeUnit.MILLISECONDS.toDays(millis)),
//                    (TimeUnit.MILLISECONDS.toHours(millis) - TimeUnit.DAYS.toHours(
//                        TimeUnit.MILLISECONDS.toDays(
//                            millis
//                        )
//                    )),
//                    (TimeUnit.MILLISECONDS.toMinutes(millis) -
//                            TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))),
//                    (TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
//                        TimeUnit.MILLISECONDS.toMinutes(millis)
//                    ))
//                )
//
//                System.out.println("Time : " + hms)
//                textView_TimeCountDown?.setText(hms);//set text
//            }
//
//            override fun onFinish() {
//                /*clearing all fields and displaying countdown finished message          */
//                textView_TimeCountDown?.setText("Count down completed");
//                System.out.println("Time up")
//            }
//        }
//        countDownTimer.start()
//    }


    fun slider() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<SliderModel> =
                apiService.Slider(ApiConstants.LG_APP_KEY)

        call.enqueue(object : Callback<SliderModel?> {


            override fun onResponse(call: Call<SliderModel?>?, response: Response<SliderModel?>) {
                Log.e("Signin Response", response.toString() + "")
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
                                this@HomeFragment
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

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealModel> = apiService.dealsAndOffers(ApiConstants.LG_APP_KEY,manager?.guestToken)

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
                            BestDeal_list_Adapter(requireActivity(), BestDeal_data, this@HomeFragment)


                } else {
                }
            }


            override fun onFailure(call: Call<BestDealModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun Hotdeal() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DealsModel> = apiService.HotDeals(ApiConstants.LG_APP_KEY,
                manager?.getGuestToken())
        Log.e("Token", manager?.getGuestToken() + "")
        call.enqueue(object : Callback<DealsModel?> {


            override fun onResponse(call: Call<DealsModel?>?, response: Response<DealsModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        Hotdeals_data = response.body()!!.data!!

                        Log.e("Hotdeals_data", Hotdeals_data.size.toString())
                    }


                    recy_deals_.adapter = Top_deals_list_Adapter(
                            requireActivity(),
                            Hotdeals_data,
                            this@HomeFragment
                    )


                } else {
                }
            }


            override fun onFailure(call: Call<DealsModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }


    fun TopTwenty() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DealsModel> = apiService.TopTwenty(ApiConstants.LG_APP_KEY,
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
                        Hotdeals_data = response.body()!!.data!!

                        Log.e("Toptwenty_data", Hotdeals_data.size.toString())
                    }

                    recy_deals_.adapter = Top_deals_list_Adapter(requireActivity(), Hotdeals_data, this@HomeFragment)

                } else {
                }
            }


            override fun onFailure(call: Call<DealsModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }


    fun NewArrivals() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<NewArrivalModel> = apiService.NewArrivals(ApiConstants.LG_APP_KEY,
                manager?.getGuestToken())

        call.enqueue(object : Callback<NewArrivalModel?> {


            override fun onResponse(call: Call<NewArrivalModel?>?, response: Response<NewArrivalModel?>) {
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

                    recy_deals_.adapter = New_arrival_list_Adapter(requireActivity(), NewArrival_data, this@HomeFragment)

                } else {
                }
            }

            override fun onFailure(call: Call<NewArrivalModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun getTimeOffers() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<TimerOfferModel> = apiService.timerOffers(ApiConstants.LG_APP_KEY,
                manager?.getGuestToken())
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
                            alTimeOffers, 0
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

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<TimerOfferModel> = apiService.nonTimerOffers(ApiConstants.LG_APP_KEY,
                manager?.getGuestToken())
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
                            alTimeOffers,1
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

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealCategory> = apiService.getBestDealCategories(ApiConstants.LG_APP_KEY,
            manager?.getGuestToken())
        Log.e("Token", manager?.getGuestToken() + "")
        call.enqueue(object : Callback<BestDealCategory?> {


            override fun onResponse(call: Call<BestDealCategory?>?, response: Response<BestDealCategory?>) {
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    if (status == "true") {
                        alDealsCategories = response.body()!!.data!!
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

    override fun onBestItemClicked(position: Int, item: BestDealModel.Datum?) {

    }

    override fun onAddToWishListButtonClicked(productId: String) {
        addToWishlist(productId)

    }

    override fun onHotdealsClicked(position: Int, item: DealsModel.Datum?) {
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

    override fun onAddToWishlistButtonClick(productId: String) {
        addToWishlist(productId)
    }

    override fun onNewArrivalClicked(position: Int, item: NewArrivalModel.Datum?) {

    }

    override fun onAddToWishListButtonClick(productId: String) {
        addToWishlist(productId)
    }

    fun addToWishlist(productId: String) {
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.addToWishList(
                ApiConstants.LG_APP_KEY,
                manager?.getUserToken(), productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                    call: Call<CommonResponseModel?>?,
                    response: Response<CommonResponseModel?>
            ) {
                Log.e("Add wishlist response", response.toString() + "")
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
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun getGuestToken() {


        // val deviceid: String = Settings.Secure.ANDROID_ID
        val deviceid = Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<GuestTokenModel> = apiService.getGuestToken(ApiConstants.LG_APP_KEY, deviceid)

        call.enqueue(object : Callback<GuestTokenModel?> {


            override fun onResponse(
                    call: Call<GuestTokenModel?>?,
                    response: Response<GuestTokenModel?>
            ) {
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        manager?.saveGuestToken(response.body()!!.data?.guesttokenAccessToken)
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


            override fun onFailure(call: Call<GuestTokenModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

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
                            this@HomeFragment
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
        val subcategory = SubCategory_Fragment()
        val args = Bundle()
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


}