package com.example.rathaanelectronics.ui.Fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.ui.Activity.MainActivity
import android.content.Intent
import android.widget.ProgressBar
import android.widget.TextView
import com.example.rathaanelectronics.ui.Activity.IntroActivity
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.ProfileModel
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    private var toolbar: Toolbar? = null
    var ll_order_history: LinearLayout? = null
    var ll_adress: LinearLayout? = null
    var ll_notification: LinearLayout? = null
    var wishlist: LinearLayout? = null
    var ll_edit_profile: LinearLayout? = null
    var ll_logout: LinearLayout? = null
    var royltyCoin: LinearLayout? = null
    var MyWallet: LinearLayout? = null
    var Language: LinearLayout? = null
    var tvName:TextView?=null
    var tvMail:TextView?=null
    lateinit var llCompare : LinearLayout

    var cart_Fragment = Cart_Fragment()
    var address_fragment = AdressFragment()
    var notification_fragment = Notification_Fragment()
    var Wish_list_Fragment = Wish_list_Fragment()
    var AddAddressFragment =
        AddAddressFragment()

    var LoyalityCoinFragment = LoyalityCoinFragment()
    var MyWalletFragment = MyWalletFragment()
//    var EditProfileFragment = EditProfileFragment()

    private var manager: MyPreferenceManager? = null
    lateinit var progressBar : ProgressBar
    var profileData : ProfileModel.Data?=null

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


        val view = inflater.inflate(R.layout.fragment_profile_, container, false)
        setHasOptionsMenu(true);

        (activity as MainActivity?)?.settoolbar()
        progressBar = view.findViewById(R.id.progress_circular)
        ll_order_history = view.findViewById<LinearLayout>(R.id.order_history)
        ll_adress = view.findViewById<LinearLayout>(R.id.address)
        ll_notification = view.findViewById<LinearLayout>(R.id.ll_notification)
        wishlist = view.findViewById<LinearLayout>(R.id.wishlist)
        ll_edit_profile = view.findViewById<LinearLayout>(R.id.ll_edit_profile)
        ll_logout = view.findViewById<LinearLayout>(R.id.ll_logout)
        llCompare = view.findViewById(R.id.ll_compare)
        royltyCoin = view.findViewById<LinearLayout>(R.id.royltyCoin)
        MyWallet = view.findViewById<LinearLayout>(R.id.MyWallet)
        Language = view.findViewById<LinearLayout>(R.id.Language)
        tvName = view.findViewById(R.id.tv_name)
        tvMail = view.findViewById(R.id.tv_mail)


        ll_order_history?.setOnClickListener { view ->

            val Oder_history_Fragment = Oder_history_Fragment()
            changeFragemnt(Oder_history_Fragment)
        }
        ll_adress?.setOnClickListener { view ->
            changeFragemnt(address_fragment)
        }
        ll_notification?.setOnClickListener { view ->
            changeFragemnt(notification_fragment)
        }

        Language?.setOnClickListener{
            startActivity(Intent(activity, IntroActivity::class.java))
        }

        wishlist?.setOnClickListener { view ->

            changeFragemnt(Wish_list_Fragment)
        }

        llCompare?.setOnClickListener { view ->


            changeFragemnt(CompareFragment())
        }


        ll_edit_profile?.setOnClickListener { view ->


            changeFragemnt(EditProfileFragment.newInstance(profileData!!,""))
        }
        royltyCoin?.setOnClickListener { view ->
            changeFragemnt(LoyalityCoinFragment)
        }
//
        MyWallet?.setOnClickListener { view ->
            changeFragemnt(MyWalletFragment)
        }
//
//
//
//
        ll_logout?.setOnClickListener { view ->

            manager?.saveUserToken("")

            val intent = Intent(activity, MainActivity::class.java)
            startActivity(intent)
            activity?.finishAffinity()

        }

        return view
    }

    override fun onResume() {
        super.onResume()
        getPersonalInfo()
    }

    fun getPersonalInfo() {
        progressBar.visibility = View.VISIBLE
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<ProfileModel> = apiService.getPersonalInfo(ApiConstants.LG_APP_KEY,manager?.userToken)

        call.enqueue(object : Callback<ProfileModel?> {


            override fun onResponse(call: Call<ProfileModel?>?, response: Response<ProfileModel?>) {
                progressBar.visibility = View.GONE
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        if (response.body()!!.data!=null){
                            profileData = response.body()!!.data
                            tvName?.setText(response.body()?.data?.user_displayname)
                            tvMail?.setText(response.body()?.data?.usermail)
                        }

//                        Log.e("Hotdeals_data", BestDeal_data.size.toString())
                    }




                } else {
                }
            }


            override fun onFailure(call: Call<ProfileModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                progressBar.visibility = View.GONE
            }
        })
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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}