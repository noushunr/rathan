package com.example.rathaanelectronics.Activity


import Filter_Fragment
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.Common.Globalmetheds
import com.example.rathaanelectronics.Fragment.*
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CartCountModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class MainActivity : AppCompatActivity(), ShowToolBar {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var FragmentContainerView: FragmentContainerView
    private lateinit var llSearch: LinearLayout
    private lateinit var etSearch: EditText
    var bottomNavigationView: BottomNavigationView? = null
    var homeFragment = HomeFragment()
    var StoreLocatorFragment = StoreLocatorFragment()
    var wishlistfragment = Wish_list_Fragment()
    val cart_fragment = Cart_Fragment()
    val profile_fragment = Profile_Fragment()
    val contact_fragment = Contact_Fragment()
    val map_fragment = MapsFragment()
    val globalmetheds = Globalmetheds()
    private var mMenu: MenuItem? = null
    private var toolbar: Toolbar? = null
    var filterFragment = Filter_Fragment()

    private lateinit var ll_back: LinearLayout
    private lateinit var appBar: AppBarLayout
    private lateinit var llHeader: LinearLayout
    private lateinit var llBadge: LinearLayout
    private lateinit var tvCount: TextView
    lateinit var ivCart : ImageView

    private var manager: MyPreferenceManager? = null

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(getLanguageAwareContext(newBase!!))
    }
    private fun getLanguageAwareContext(context: Context): Context? {
        if (manager == null)
            manager = MyPreferenceManager(context)
        val configuration: Configuration = context.resources.configuration
        configuration.setLocale(Locale(manager?.locale))
        return context.createConfigurationContext(configuration)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (manager?.locale.equals("ar")) {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL;
        } else {
            window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR;
        }
        //  globalmetheds.setStatusbar(this)
        setContentView(R.layout.activity_main)
        manager = MyPreferenceManager(this)
        drawerLayout = findViewById(R.id.drawer_layout)
        llSearch = findViewById(R.id.ll_search)
        etSearch = findViewById(R.id.et_search)
        appBar = findViewById(R.id.app_bar)
        llHeader = findViewById(R.id.ll_header)
        llBadge = findViewById(R.id.ll_badge)
        tvCount = findViewById(R.id.tv_count)
        ivCart = findViewById(R.id.iv_cart)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        ivCart.setOnClickListener {
            changeFragemnt(cart_fragment)
        }
        var language = if (manager?.locale.equals("en")) "EN" else getString(R.string.arabic)
        findViewById<TextView>(R.id.tv_language).text = language

//
//       val  ll_edit_profile = findViewById<LinearLayout>(R.id.ll_edit_profile)
//        ll_edit_profile?.setOnClickListener { view ->
//
//
//            changeFragemnt(EditProfileFragment())
//        }

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()
        navView = findViewById(R.id.nav_view)

        if (manager?.locale.equals("ar")){
            navView.background = resources.getDrawable(R.drawable.bg_navigation_drawer_left)
        }else{
            navView.background = resources.getDrawable(R.drawable.bg_navigation_drawer)
        }

        findViewById<LinearLayout>(R.id.ll_All_category)?.setOnClickListener {
            manager?.min = -1f
            manager?.max = -1f
            changeFragemnt(MainCategoryFragment())
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_store_locator)?.setOnClickListener {
            changeFragemnt(StoreLocatorFragment)
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_Deals_Offer)?.setOnClickListener {
            changeFragemnt(Deals_offer_Fragment.newInstance("1",""))
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_installment_payment)?.setOnClickListener {
            var url = "https://online.rathaan.com/${manager?.locale}/pos/pay"
//            val intent = Intent(this,WebviewActivity::class.java)
//            intent.putExtra("installment_url",url)
//            startActivity(intent)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_terms_condition)?.setOnClickListener {
           changeFragemnt(TermsConditionFragment())
            drawerLayout.closeDrawers();
        }

        findViewById<LinearLayout>(R.id.ll_my_wishlist)?.setOnClickListener {
            changeFragemnt(Wish_list_Fragment())
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_return_policy)?.setOnClickListener {
            changeFragemnt(ReturnPolicyFragment())
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_logout)?.setOnClickListener {
            manager?.saveUserToken("")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
            drawerLayout.closeDrawers()
        }
        findViewById<LinearLayout>(R.id.ll_contact_us)?.setOnClickListener {
            changeFragemnt(Contact_Fragment())
            drawerLayout.closeDrawers()
        }
        findViewById<LinearLayout>(R.id.ll_Bundle_Offers)?.setOnClickListener {
            changeFragemnt(BundleProductFragment.newInstance("",""))
            drawerLayout.closeDrawers();
//            changeFragemnt(BundleProductFragment())
        }
        findViewById<LinearLayout>(R.id.ll_Language)?.setOnClickListener {
            startActivity(Intent(this@MainActivity,IntroActivity::class.java))
        }
        etSearch.setOnClickListener {
            showToolBar(false)
            changeFragemnt(SearchProductFragment())
        }



        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.back -> {
                    drawerLayout.closeDrawers();

                    true
                }
                R.id.all_Category -> {
                    //setCurrentFragment(StoreLocatorFragment)

                    true
                }
                R.id.store_locator -> {

                    //  setCurrentFragment(StoreLocatorFragment)
                    changeFragemnt(StoreLocatorFragment)
//                drawerLayout.closeDrawer(GravityCompat.START);
                    drawerLayout.closeDrawers();
                    true
                }
                R.id.deals_and_offer -> {
                    changeFragemnt(Deals_offer_Fragment.newInstance("1",""))
                    drawerLayout.closeDrawers();
                    true
                }
                R.id.installment_payment -> {
                    var url = "https://online.rathaan.com/${manager?.locale}/pos/pay"
//                    val intent = Intent(this,WebviewActivity::class.java)
//                    intent.putExtra("installment_url",url)
//                    startActivity(intent)
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                    drawerLayout.closeDrawers();
                    true
                }
                R.id.return_policy -> {
                    changeFragemnt(ReturnPolicyFragment.newInstance("",""))
                    drawerLayout.closeDrawers();
                    true
                }
                R.id.bundle_offer -> {
                    changeFragemnt(BundleProductFragment.newInstance("",""))
                    drawerLayout.closeDrawers();
                    //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.contact_us -> {
                    changeFragemnt(contact_fragment)
                    //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.my_wishlist -> {
//                    if (manager?.getUserToken()!!.isNotEmpty()) {
                        changeFragemnt(wishlistfragment)
//                    } else {
//                        startActivity(Intent(this, Sign_in_Activity::class.java))
//                    }

                    drawerLayout.closeDrawers();
                    true
                }
                R.id.language -> {

                    startActivity(Intent(this@MainActivity,IntroActivity::class.java))
                    true
                }
                R.id.terms_condition -> {
                    changeFragemnt(TermsConditionFragment())
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.return_policy -> {

                    changeFragemnt(ReturnPolicyFragment())
                    true
                }


                else -> {
                    false
                }
            }
        }


        settoolbar()


        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        changeFragemnt(homeFragment)

        val mOnNavigationItemSelectedListener =
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.bnv_home -> {
                        changeFragemnt(homeFragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.bnv_wishlist -> {
//                        if (manager?.getUserToken()!!.isNotEmpty()) {
                            changeFragemnt(wishlistfragment)
//                        } else {
//                            startActivity(Intent(this, Sign_in_Activity::class.java))
//                        }
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.bnv_Cart -> {
                        changeFragemnt(cart_fragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.bnv_profile -> {
                        if (manager?.getUserToken()!!.isNotEmpty()) {
                            changeFragemnt(profile_fragment)
                        } else {
                            startActivity(Intent(this, Sign_in_Activity::class.java))
                        }

                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.bnv_contact -> {

//                        setCurrentFragment(map_fragment)
                        changeFragemnt(contact_fragment)
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            }
        bottomNavigationView?.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

    }

    //    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home ->
//drawerLayout.openDrawer(Gravity.START)
//
//               true
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.cart_menu, menu)
//       var menu = menu
//        this.mMenu = menu.findItem(R.id.cart).setVisible(true)
//        this.mMenu = menu.findItem(R.id.filter).setVisible(true)
//        return super.onCreateOptionsMenu(menu)
//    }
    @SuppressLint("WrongConstant")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(Gravity.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun changeFragemnt(fragment: Fragment) {
        if (fragment is HomeFragment) {
            showToolBar(true)
        } else {
            showToolBar(false)
        }
        val transaction = supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    public fun hideBottomnavigationView() {

        bottomNavigationView!!.visibility = View.GONE
        FragmentContainerView = findViewById(R.id.frame);

//        val params = FragmentContainerView.getLayoutParams()
////left, top, right, bottom
////left, top, right, bottom
//        params.setMargins(10, 10, 10, 10)
//        frameLayout.setLayoutParams(params)
    }

    public fun showViewBottomnavigationView() {

        bottomNavigationView!!.visibility = View.VISIBLE
    }

    override fun onBackPressed() {

        var fragment = supportFragmentManager.findFragmentById(R.id.frame);
        if (fragment is HomeFragment)
            finishAffinity()
        else
            super.onBackPressed()
    }

    fun settoolbar() {

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayShowTitleEnabled(false)
        }

        this.getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        toolbar!!.setNavigationIcon(R.drawable.ic_nav_menu);
        // this.getSupportActionBar()?.setHomeAsUpIndicator(R.drawable.ic_nav_menu);
    }

//    private fun setCurrentFragment(fragment: Fragment) =
//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.frame, fragment)
//            commit()
//
//        }

    fun Dialog() {

//        val alertLayout: View = this@MainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE).inflate(R.layout.progress_bar_custom_style, null)
//        val progress = alertLayout.findViewById<View>(R.id.bar1) as ProgressBar
//        progress.indeterminateDrawable.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN)
//
//        val dialog = Dialog(this@MainActivity)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(alertLayout)
//        dialog.getWindow()?.setBackgroundDrawableResource(android.R.color.transparent)
//        dialog.show()
    }

    override fun showToolBar(show: Boolean) {
        if (show) {
            appBar.visibility = View.VISIBLE
            llHeader.visibility = View.VISIBLE
        } else {
            appBar.visibility = View.GONE
            llHeader.visibility = View.GONE
        }
    }

    override fun updateCartCount(count: Int) {
        if (count!!>0){
            llBadge.visibility = View.VISIBLE
            tvCount.text = "$count"
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.number = count
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.backgroundColor = resources.getColor(R.color.white)
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.badgeTextColor = resources.getColor(R.color.colorPrimary)

        }else{
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.clearNumber()
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.backgroundColor = resources.getColor(R.color.colorPrimary)
            llBadge.visibility = View.GONE
        }

    }

    override fun updateWalletCount(count: Int) {
        if (count!!>0){
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.backgroundColor = resources.getColor(R.color.white)
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.badgeTextColor = resources.getColor(R.color.colorPrimary)
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.number = count

        }else {
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.clearNumber()
            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.backgroundColor = resources.getColor(R.color.colorPrimary)
        }
    }

    override fun onResume() {
        super.onResume()
        getCartCount()
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
                        if (cartCount!!>0){
                            llBadge.visibility = View.VISIBLE
                            tvCount.text = "$cartCount"
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.number = cartCount
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.backgroundColor = resources.getColor(R.color.white)
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.badgeTextColor = resources.getColor(R.color.colorPrimary)

                        }else{
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.clearNumber()
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_Cart)?.backgroundColor = resources.getColor(R.color.colorPrimary)
                            llBadge.visibility = View.GONE
                        }
                        if (wishListCount!!>0){
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.backgroundColor = resources.getColor(R.color.white)
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.badgeTextColor = resources.getColor(R.color.colorPrimary)
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.number = wishListCount

                        }else {
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.clearNumber()
                            bottomNavigationView?.getOrCreateBadge(R.id.bnv_wishlist)?.backgroundColor = resources.getColor(R.color.colorPrimary)
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