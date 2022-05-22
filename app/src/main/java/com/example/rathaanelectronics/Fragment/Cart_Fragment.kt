package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.Cart_list_Adapter
import com.example.rathaanelectronics.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import android.view.Gravity

import android.view.WindowManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.rathaanelectronics.Activity.Sign_in_Activity
import com.example.rathaanelectronics.Adapter.PickUpShop_list_Adapter
import com.example.rathaanelectronics.Interface.ManageCartItem
import com.example.rathaanelectronics.Interface.PickUpStoreClickListener
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Cart_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Cart_Fragment : Fragment(), PickUpStoreClickListener, ManageCartItem {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var deliver_type: String = "pickup"
    private var Menufilter: MenuItem? = null
    var dialog: Dialog? = null
    lateinit var ll_coupon_code_fld:LinearLayout
    var PickUpStoreList: List<PickUpStoreModel.Detail> = ArrayList<PickUpStoreModel.Detail>()
    var showCartData: List<ShowCartResponseModel.Data.CartItems>? = null
    private var manager: MyPreferenceManager? = null
    private var recy_pickup_storelist: RecyclerView? = null
    lateinit var emptyText: TextView
    lateinit var cartTotalTxt: TextView
    lateinit var cartInstallationTxt: TextView
    lateinit var cartDelivaryChargeTxt: TextView
    lateinit var cartAllTotaltxt: TextView
    var btn_checkout: Button? = null
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    lateinit var recycler_cart: RecyclerView

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

        val view = inflater.inflate(R.layout.fragment_cart_, container, false)
        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        sheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet))


        val ll_coupon_code_fld = view.findViewById<LinearLayout>(R.id.ll_coupon_code_fld)
        val btn_checkout = view.findViewById<Button>(R.id.btn_checkout)
        recycler_cart = view.findViewById<RecyclerView>(R.id.recycler_cart)
        emptyText = view.findViewById(R.id.emptyText)
        cartTotalTxt = view.findViewById(R.id.cartTotalTxt)
        cartInstallationTxt = view.findViewById(R.id.cartInstallationTxt)
        cartDelivaryChargeTxt = view.findViewById(R.id.cartDelivaryChargeTxt)
        cartAllTotaltxt = view.findViewById(R.id.cartAllTotaltxt)
        //val cart_list_Adapter = Cart_list_Adapter(activity)

        recycler_cart.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        //recycler_cart.adapter = cart_list_Adapter
        if(manager?.getUserToken()!!.isNotEmpty()){
            fetchCartDetails()
        }else{
            startActivity(Intent(activity, Sign_in_Activity::class.java))
        }

        val radioGroup = view.findViewById(R.id.radioGroup) as RadioGroup

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {

                    R.id.radioButtonpickup -> {

                        deliver_type = "pickup"

                    }

                    R.id.radioButtonhomedelivery -> {

                        deliver_type = "Home_delivery"

                    }
                }
                // checkedId is the RadioButton selected
            }
        })

        /*sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    // btBottomSheet.text = "Close Bottom Sheet"
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    // btBottomSheet.text = "Expand Bottom Sheet"
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
            }
        })*/

//        btBottomSheet.setOnClickListener(View.OnClickListener {
//            expandCloseSheet()
//        })


        btn_checkout?.setOnClickListener { view ->


            if (deliver_type.equals("pickup")) {

                selectlocationAlert()
            } else {

                changeFragemnt(Delivery_state_select_adress_Fragment())

            }


        }
        ll_coupon_code_fld.setOnClickListener { view ->

            apllycouponAlert()


        }
        return view
    }

    @SuppressLint("WrongConstant")
    fun selectlocationAlert() {
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val alertLayout: View = inflater.inflate(R.layout.pickup_list_layout, null)
        recy_pickup_storelist = alertLayout.findViewById<RecyclerView>(R.id.recy_pickup_storelist)

        dialog = Dialog(requireActivity())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(alertLayout)

        val lp = WindowManager.LayoutParams()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog!!.show()
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.show()

        recy_pickup_storelist!!.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        pickUpStoreList()

    }

    fun apllycouponAlert() {
        val inflater =
            requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val alertLayout: View = inflater.inflate(R.layout.dialogue_box_apply_coupencode, null)


        dialog = Dialog(requireActivity())
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(alertLayout)

        val lp = WindowManager.LayoutParams()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
        dialog!!.show()
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        dialog!!.show()


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.cart -> {
//                changeFragemnt(cart_Fragment)
//                true
//            }
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

    private fun expandCloseSheet() {
        if (sheetBehavior!!.state != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
//            btBottomSheet.text = "Close Bottom Sheet"
        } else {
            sheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
//            btBottomSheet.text = "Expand Bottom Sheet"
        }
    }

    fun pickUpStoreList() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<PickUpStoreModel> = apiService.PickUpStore(ApiConstants.LG_APP_KEY)

        call.enqueue(object : Callback<PickUpStoreModel?> {


            override fun onResponse(
                call: Call<PickUpStoreModel?>?,
                response: Response<PickUpStoreModel?>
            ) {
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        PickUpStoreList = response.body()!!.data!!.details

                        Log.e("PickUpStoreList", PickUpStoreList.size.toString())

                        val url = response.body()!!.data!!.details[0].storeMap


                    }


                    recy_pickup_storelist!!.adapter= PickUpShop_list_Adapter(requireActivity(),PickUpStoreList,this@Cart_Fragment)


                } else {
                }
            }


            override fun onFailure(call: Call<PickUpStoreModel?>?, t: Throwable?) {
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
         * @return A new instance of fragment Cart_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Cart_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPickStoreClicked(position: Int, item: PickUpStoreModel.Detail?) {
        changeFragemnt(MapsFragment())
        dialog!!.dismiss()
    }

    private fun fetchCartDetails(){

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<ShowCartResponseModel> = apiService.viewCart(ApiConstants.LG_APP_KEY,
            manager?.getUserToken())

        call.enqueue(object : Callback<ShowCartResponseModel?> {


            override fun onResponse(
                call: Call<ShowCartResponseModel?>?,
                response: Response<ShowCartResponseModel?>
            ) {
                Log.e("show cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status == "true") {
                         showCartData = response.body()?.data?.cartItems
                        val data = response.body()?.data

                        Log.e("Show cart data", showCartData.toString())
                        if (showCartData!!.size >0){
                            recycler_cart.adapter = Cart_list_Adapter(activity,showCartData!!, this@Cart_Fragment)
                            if (data != null) {
                                cartTotalTxt.text = "KD "+data.cartTotal
                                cartInstallationTxt.text = "KD "+data.installationTotal
                                //cartDelivaryChargeTxt.text = "KD "+data.
                                cartAllTotaltxt.text = "KD "+data.cartTotalWithInstallation
                            }
                        }else {

                        }

                    }else{
                        recycler_cart.isGone = true
                        emptyText.isVisible = true
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        activity,
                        "Cart fetching failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<ShowCartResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                recycler_cart.isGone = true
                emptyText.isVisible = true
                Log.e("onFailure", t.toString())
            }
        })
    }

    override fun onDeleteCartItem(cartId: String?) {
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.deleteCartItem(ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), cartId)

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("show cart Response", response.toString() + "")
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
                        fetchCartDetails()
                    }else{
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        activity,
                        "Cart deleting failed",
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

    override fun onUpdateCartQuantity(cartId: String?, quantity: String) {
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.changeCartQuantity(ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), cartId, quantity)

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("show cart Response", response.toString() + "")
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
                        fetchCartDetails()
                    }else{
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        activity,
                        "Cart Updating failed",
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
}