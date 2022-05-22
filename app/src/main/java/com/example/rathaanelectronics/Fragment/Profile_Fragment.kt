package com.example.rathaanelectronics.Fragment

import Filter_Fragment
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Activity.MainActivity
import android.content.Intent
import com.example.rathaanelectronics.Activity.Sign_in_Activity


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

    var cart_Fragment = Cart_Fragment()
    var address_fragment = AdressFragment()
    var notification_fragment = Notification_Fragment()
    var Wish_list_Fragment = Wish_list_Fragment()
    var AddAddressFragment = AddAddressFragment()

    var LoyalityCoinFragment = LoyalityCoinFragment()
    var MyWalletFragment = MyWalletFragment()
    var EditProfileFragment = EditProfileFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val view = inflater.inflate(R.layout.fragment_profile_, container, false)
        setHasOptionsMenu(true);

        (activity as MainActivity?)?.settoolbar()

        ll_order_history = view.findViewById<LinearLayout>(R.id.order_history)
        ll_adress = view.findViewById<LinearLayout>(R.id.address)
        ll_notification = view.findViewById<LinearLayout>(R.id.ll_notification)
        wishlist = view.findViewById<LinearLayout>(R.id.wishlist)
        ll_edit_profile = view.findViewById<LinearLayout>(R.id.ll_edit_profile)
        ll_logout = view.findViewById<LinearLayout>(R.id.ll_logout)
        royltyCoin = view.findViewById<LinearLayout>(R.id.royltyCoin)
        MyWallet = view.findViewById<LinearLayout>(R.id.MyWallet)
        Language = view.findViewById<LinearLayout>(R.id.Language)

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


        wishlist?.setOnClickListener { view ->


            changeFragemnt(Wish_list_Fragment)
        }


        ll_edit_profile?.setOnClickListener { view ->


            changeFragemnt(EditProfileFragment)
        }
        royltyCoin?.setOnClickListener { view ->


            changeFragemnt(LoyalityCoinFragment)
        }

        MyWallet?.setOnClickListener { view ->


            changeFragemnt(MyWalletFragment)
        }




        ll_logout?.setOnClickListener { view ->


            val intent = Intent(activity, Sign_in_Activity::class.java)
            startActivity(intent)

        }

        return view
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