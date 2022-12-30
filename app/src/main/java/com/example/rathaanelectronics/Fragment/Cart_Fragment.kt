package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
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
import android.view.View.GONE

import android.view.WindowManager
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.rathaanelectronics.Adapter.PickUpShop_list_Adapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.ManageCartItem
import com.example.rathaanelectronics.Interface.PickUpStoreClickListener
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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
    private var deliver_type: String = ""
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
    lateinit var tvTotal: TextView
    lateinit var rlTotal: RelativeLayout
    lateinit var installationChargeRow: TableRow
    lateinit var discountRow: TableRow
    lateinit var tvDiscount: TextView
    lateinit var etCoupon: EditText
    lateinit var tvApply: TextView
    var btn_checkout: Button? = null
    private lateinit var sheetBehavior: BottomSheetBehavior<RelativeLayout>
    lateinit var recycler_cart: RecyclerView
    lateinit var progressBar : ProgressBar
    lateinit var data: ShowCartResponseModel.Data
    var discount = 0.0
    var preCheckoutModel = PreCheckoutModel()
    lateinit var rbPickUp : RadioButton
    lateinit var rbDelivery : RadioButton
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
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_cart_, container, false)
        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        val bottomSheet : BottomSheetDialog = BottomSheetDialog(requireActivity())
        sheetBehavior = BottomSheetBehavior.from(view.findViewById(R.id.bottom_sheet))
        progressBar = view.findViewById(R.id.progress_circular)
        rbDelivery = view.findViewById(R.id.radioButtonhomedelivery)
        rbPickUp = view.findViewById(R.id.radioButtonpickup)

        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        val ll_coupon_code_fld = view.findViewById<LinearLayout>(R.id.ll_coupon_code_fld)
        val btn_checkout = view.findViewById<Button>(R.id.btn_checkout)
        val btnContinue = view.findViewById<Button>(R.id.btn_continue)
        etCoupon = view.findViewById<EditText>(R.id.et_coupon)
        tvApply = view.findViewById(R.id.tv_apply)
        recycler_cart = view.findViewById<RecyclerView>(R.id.recycler_cart)
        emptyText = view.findViewById(R.id.emptyText)
        cartTotalTxt = view.findViewById(R.id.cartTotalTxt)
        cartInstallationTxt = view.findViewById(R.id.cartInstallationTxt)
        cartDelivaryChargeTxt = view.findViewById(R.id.cartDelivaryChargeTxt)
        cartAllTotaltxt = view.findViewById(R.id.cartAllTotaltxt)
        tvTotal = view.findViewById(R.id.cartAllTotaltxt1)
        installationChargeRow = view.findViewById(R.id.installation)
        rlTotal = view.findViewById(R.id.ll_total)
        tvDiscount = view.findViewById(R.id.discount)
        discountRow = view.findViewById(R.id.discountRow)
        //val cart_list_Adapter = Cart_list_Adapter(activity)

        recycler_cart.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        //recycler_cart.adapter = cart_list_Adapter
//        if(manager?.getUserToken()!!.isNotEmpty()){
//            fetchCartDetails()
//        }else{
//            startActivity(Intent(activity, Sign_in_Activity::class.java))
//        }

        discount = 0.0
        etCoupon.setText("")
        tvTotal.setText("")
        tvApply.text = getString(R.string.apply)
        etCoupon.isFocusable = true
        etCoupon.isClickable = true
        etCoupon.isFocusableInTouchMode = true

        rbPickUp.isChecked = false
        rbDelivery.isChecked = false
        fetchCartDetails()
        val radioGroup = view.findViewById(R.id.radioGroup) as RadioGroup
//        radioGroup.clearCheck()

        rbPickUp?.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                selectlocationAlert()
                installationChargeRow.visibility = GONE
                deliver_type = "pickup"
                if (data != null) {
                    cartTotalTxt.text = "KD " + data.cartTotal

                    cartAllTotaltxt.text = "KD " + (data.cartTotal?.toDouble()
                        ?.minus(discount))
                    tvTotal.text = "KD " + (data.cartTotal?.toDouble()
                        ?.minus(discount))
                }
            }
        }
        rbDelivery.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                rbDelivery.isChecked = true
                installationChargeRow.visibility = View.VISIBLE
                deliver_type = "del"
                if (data != null) {
                    cartTotalTxt.text = "KD " + data.cartTotal

                    cartAllTotaltxt.text =
                        "KD " + (data.cartTotalWithInstallation?.toDouble()
                            ?.minus(discount))
                    tvTotal.text = "KD " + (data.cartTotalWithInstallation?.toDouble()
                        ?.minus(discount))
                }
            }
        }

//        radioGroup.setOnCheckedChangeListener { group, checkedId ->
//            when (checkedId) {
//
//                R.id.radioButtonpickup -> {
//                    if (rbPickUp.isChecked){
//
//                    }
//
//                }
//
//                R.id.radioButtonhomedelivery -> {
//                    if (rbDelivery.isChecked) {
//
//                    }
//                }
//            }
//            // checkedId is the RadioButton selected
//        }

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


        btn_checkout?.setOnClickListener {
            expandCloseSheet()
//            deliver_type = ""
            installationChargeRow.visibility = GONE
            if (data != null) {
                cartTotalTxt.text = "KD "+data.cartTotal

                cartAllTotaltxt.text = "KD "+(data.cartTotal?.toDouble()
                    ?.minus(discount))
                tvTotal.text = "KD "+(data.cartTotal?.toDouble()
                    ?.minus(discount))
            }
        }
        btnContinue?.setOnClickListener {

            if (deliver_type.isEmpty()){
                Toast.makeText(activity, getString(R.string.choose_del_type), Toast.LENGTH_LONG).show()
            }else {
                if (deliver_type.equals("pickup")) {
//                    selectlocationAlert()
                } else {
                    expandCloseSheet()
                    rbDelivery.isChecked = false
                    rbPickUp.isChecked = false
                    preCheckoutModel.pickStore = "0"
                    preCheckoutModel.total = tvTotal.text.toString()
                    preCheckoutModel.grandTotal = tvTotal.text.toString()
                    changeFragemnt(
                        Delivery_state_select_adress_Fragment.newInstance(
                            tvTotal.text.toString(),
                            preCheckoutModel
                        )
                    )
                }
            }
        }
        ll_coupon_code_fld.setOnClickListener { view ->

            if (etCoupon.text.toString().length>0){

                applyCoupon(etCoupon.text.toString(),tvTotal.text.toString())
            }else{
                etCoupon.setError(getString(R.string.empty_field_error))
                etCoupon.requestFocus()
            }
//            apllycouponAlert()


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
        dialog?.setCanceledOnTouchOutside(false)

        dialog?.setOnCancelListener {
            rbDelivery.isChecked = false
            rbPickUp.isChecked = false
            deliver_type = ""
        }

        val lp = WindowManager.LayoutParams()
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        lp.gravity = Gravity.CENTER
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

        LoadingDialog.showLoadingDialog(requireContext(),"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<PickUpStoreModel> = apiService.PickUpStore(ApiConstants.LG_APP_KEY)

        call.enqueue(object : Callback<PickUpStoreModel?> {


            override fun onResponse(
                call: Call<PickUpStoreModel?>?,
                response: Response<PickUpStoreModel?>
            ) {
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        PickUpStoreList = response.body()!!.data!!.details

                        Log.e("PickUpStoreList", PickUpStoreList.size.toString())

                        val url = response.body()!!.data!!.details[0].storeMap


                    }


                    recy_pickup_storelist!!.adapter= PickUpShop_list_Adapter(requireActivity(),PickUpStoreList,this@Cart_Fragment,manager?.locale.equals("ar"))


                } else {
                }
            }


            override fun onFailure(call: Call<PickUpStoreModel?>?, t: Throwable?) {
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
        rbPickUp.isChecked = false
        rbDelivery.isChecked = false
        dialog!!.dismiss()
        preCheckoutModel.pickStore = item?.storeId!!
        preCheckoutModel.pickStoreDetails = item
        preCheckoutModel.total = tvTotal.text.toString()
        preCheckoutModel.grandTotal = tvTotal.text.toString()
        changeFragemnt(Delivery_state_select_adress_Fragment.newInstance(tvTotal.text.toString(),preCheckoutModel))
//        val intent = Intent(activity, Thank_You_Activity::class.java)
//        startActivity(intent)
    }


    private fun fetchCartDetails(){
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<ShowCartResponseModel> = apiService.viewCart(ApiConstants.LG_APP_KEY,
            token)

        call.enqueue(object : Callback<ShowCartResponseModel?> {


            override fun onResponse(
                call: Call<ShowCartResponseModel?>?,
                response: Response<ShowCartResponseModel?>
            ) {
                Log.e("show cart Response", response.toString() + "")
                if (response.isSuccessful()) {
                    LoadingDialog.cancelLoading()

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status == "true") {
                         showCartData = response.body()?.data?.cartItems
                        data = response.body()?.data!!

                        Log.e("Show cart data", showCartData.toString())
                        showToolBar?.updateCartCount(showCartData?.size!!)
                        if (showCartData!!.size >0){
                            rlTotal.visibility = View.VISIBLE
                            recycler_cart.visibility = View.VISIBLE
                            recycler_cart.adapter = Cart_list_Adapter(activity,showCartData!!, this@Cart_Fragment,manager?.locale.equals("ar",ignoreCase = true))
                            if (data != null) {
                                cartTotalTxt.text = "KD "+data.cartTotal
                                cartInstallationTxt.text = "KD "+data.installationTotal
                                cartAllTotaltxt.text = "KD "+data.cartTotal
                                tvTotal.text = "KD "+data.cartTotal
                                if (data?.deliveryAvailable?.equals("yes",ignoreCase = true)!!){
                                    rbDelivery.visibility = View.VISIBLE
                                }else{
                                    rbDelivery.visibility = View.GONE
                                }
                                if (data?.pickupAailable?.equals("yes",ignoreCase = true)!!){
                                    rbPickUp.visibility = View.VISIBLE
                                }else{
                                    rbPickUp.visibility = View.GONE
                                }
                                //cartDelivaryChargeTxt.text = "KD "+data.
//                                if (deliver_type.equals("pickup")) {
//                                    cartAllTotaltxt.text = "KD "+data.cartTotal
//                                    tvTotal.text = "KD "+data.cartTotal
//                                } else {
//                                    cartAllTotaltxt.text = "KD "+data.cartTotalWithInstallation
//                                    tvTotal.text = "KD "+data.cartTotalWithInstallation
//                                }

                            }
                        }else {

                            recycler_cart.isGone = true
                            emptyText.isVisible = true
                            rlTotal.visibility = View.GONE
                        }

                    }else{
                        rlTotal.visibility = View.GONE
                        recycler_cart.isGone = true
                        emptyText.isVisible = true
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {

                }
            }


            override fun onFailure(call: Call<ShowCartResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                recycler_cart.isGone = true
                emptyText.isVisible = true
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }

    override fun onDeleteCartItem(cartId: String?) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.deleteCartItem(ApiConstants.LG_APP_KEY,
            token, cartId)

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
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
                        getString(R.string.delete_cart_failed),
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

    override fun onUpdateCartQuantity(cartId: String?, quantity: String) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.changeCartQuantity(ApiConstants.LG_APP_KEY,
            token, cartId, quantity)

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("show cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
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
                        getString(R.string.update_cart_failed),
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

    fun applyCoupon(couponCode: String, cartTotal:String) {

        LoadingDialog.showLoadingDialog(requireContext(),"")

        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CouponApplyModel> = apiService.applyCoupon(
            ApiConstants.LG_APP_KEY,
            token,
            couponCode,
            cartTotal?.replace("KD ","")
        )

        call.enqueue(object : Callback<CouponApplyModel?> {


            override fun onResponse(
                call: Call<CouponApplyModel?>?,
                response: Response<CouponApplyModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data!=null){
                            discountRow.visibility = View.VISIBLE
                            discount = response?.body()?.data?.discountAmount?.toDouble()!!
//                            etCoupon.setText("")
                            tvApply.setText(getString(R.string.applied))
                            etCoupon.isFocusable = false
                            etCoupon.isClickable = false
                            etCoupon.isFocusableInTouchMode = false
                            try {
                                tvTotal.setText("${tvTotal.text.toString().replace("KD ","").toDouble() - response?.body()?.data?.discountAmount?.toDouble()!!}")
                            }catch (e:Exception){

                            }

                            preCheckoutModel.coupon = couponCode
                            tvDiscount.setText(response?.body()?.data?.discountAmount)
                            Toast.makeText(
                                activity,
                                response?.body()?.data?.comments,
                                Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            Toast.makeText(
                                activity,
                                response?.body()!!.message!!,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Toast.makeText(
                            activity,
                            response?.body()!!.message!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {

                }
            }


            override fun onFailure(call: Call<CouponApplyModel?>?, t: Throwable?) {
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }
}