package com.example.rathaanelectronics.Activity


import Filter_Fragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.Common.Globalmetheds
import com.example.rathaanelectronics.Fragment.*
import com.example.rathaanelectronics.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    private lateinit var FragmentContainerView: FragmentContainerView
    var bottomNavigationView: BottomNavigationView? = null
    var homeFragment = HomeFragment()
    var StoreLocatorFragment = StoreLocatorFragment()
    var Deals_offer_Fragment = Deals_offer_Fragment()
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //  globalmetheds.setStatusbar(this)
        setContentView(R.layout.activity_main)
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

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

        findViewById<LinearLayout>(R.id.ll_All_category)?.setOnClickListener {
            changeFragemnt(MainCategoryFragment())
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_store_locator)?.setOnClickListener {
            changeFragemnt(StoreLocatorFragment)
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_Deals_Offer)?.setOnClickListener {
            changeFragemnt(Deals_offer_Fragment())
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_my_wishlist)?.setOnClickListener {
            changeFragemnt(Wish_list_Fragment())
            drawerLayout.closeDrawers()
        }

        findViewById<LinearLayout>(R.id.ll_Bundle_Offers)?.setOnClickListener {
            changeFragemnt(BundleProductFragment())
            drawerLayout.closeDrawers()
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
                    changeFragemnt(Deals_offer_Fragment)
                    drawerLayout.closeDrawers();
                    true
                }
                R.id.bundle_offer -> {
                    //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.contact_us -> {
                    //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.installment_payment -> {
                    //Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.my_wishlist -> {
                    changeFragemnt(wishlistfragment)
                    drawerLayout.closeDrawers();
                    true
                }
                R.id.language -> {

                    true
                }
                R.id.terms_condition -> {

                    true
                }
                R.id.return_policy -> {

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
                        changeFragemnt(wishlistfragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.bnv_Cart -> {
                        changeFragemnt(cart_fragment)
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.bnv_profile -> {
                        changeFragemnt(profile_fragment)
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
}