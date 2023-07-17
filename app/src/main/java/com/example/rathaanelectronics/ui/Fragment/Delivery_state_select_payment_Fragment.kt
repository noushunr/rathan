package com.example.rathaanelectronics.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.ui.Activity.ThankYouActivity
import com.example.rathaanelectronics.ui.Activity.WebviewActivity
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator


import com.kofigyan.stateprogressbar.StateProgressBar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Delivery_state_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Delivery_state_select_payment_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: AddressResponseModel.Details? = null
    private var param2: PreCheckoutModel? = null
    var btn_continue: Button? = null
    lateinit var llDelivery : LinearLayout
    lateinit var tvTotal: TextView
    lateinit var tvDelivery: TextView
    lateinit var llLoyalty: LinearLayout
    lateinit var tvLoyaltyBalance: TextView
    lateinit var llWallet: LinearLayout
    lateinit var tvWalletBalance: TextView
    lateinit var walletSwitch: Switch
    lateinit var loyaltySwitch: Switch
    var walletApplied = 0.0
    var loyaltyApplied = 0.0

    lateinit var progressBar: ProgressBar
    private var manager: MyPreferenceManager? = null
    var loyaltyAmount = 0.0
    var walletBalance = 0.0
    var totalAmount = 0.0

    var walletTotalBalance = 0.0
    var loyaltyTotal = 0.0
    var deliveryCharge = 0
    var sameDayCharge = 0
    lateinit var preCheckoutModel: PreCheckoutModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.get(ARG_PARAM1) as AddressResponseModel.Details?
            preCheckoutModel = it.get(ARG_PARAM2) as PreCheckoutModel
        }
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_delivery_state_payment, container, false)
        var username = view.findViewById<TextView>(R.id.usernameTxt)
        var addresstxt = view.findViewById<TextView>(R.id.addresstxt)
        var addressDetailtxt = view.findViewById<TextView>(R.id.addressDetailtxt)
        var mobileTxt = view.findViewById<TextView>(R.id.mobileTxt)
        var emailTxt = view.findViewById<TextView>(R.id.emailTxt)
        tvTotal = view.findViewById(R.id.tv_total)
        tvDelivery = view.findViewById(R.id.tv_del_charge)
        llDelivery = view.findViewById(R.id.ll_delivery)
        btn_continue = view.findViewById(R.id.btn_continue)
        tvLoyaltyBalance = view.findViewById(R.id.loyalty_balance)
        tvWalletBalance = view.findViewById(R.id.wallet_balance)
        llLoyalty = view.findViewById(R.id.ll_loyalty)
        llWallet = view.findViewById(R.id.ll_wallet)
        progressBar = view.findViewById(R.id.progress_circular)
        walletSwitch = view.findViewById(R.id.wallet_switch)
        loyaltySwitch = view.findViewById(R.id.loyalty_switch)
        walletSwitch?.setOnCheckedChangeListener { compoundButton, b ->
            if (b){
                if (preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge > 0 ){
                    walletBalance = walletTotalBalance
                    if (preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge < walletTotalBalance){
                        walletBalance = preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge
                    }
                    walletApplied = walletBalance
                    applyWallet(walletBalance.toString(),"${preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge}")
                }else{

                    walletSwitch.isChecked = false
                    Toast.makeText(requireContext(),"Total amount is 0",Toast.LENGTH_LONG).show()
                }

            }else{
                walletApplied = 0.0
                preCheckoutModel.grandTotal = "KD ${preCheckoutModel.grandTotal?.replace("KD ","").toDouble() + walletBalance }"
                tvTotal.text = preCheckoutModel.grandTotal
            }
        }
        loyaltySwitch?.setOnCheckedChangeListener { compoundButton, b ->

            if (b){
                if (preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge > 0){
                    loyaltyAmount = loyaltyTotal
                    if (preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge < loyaltyTotal){
                        loyaltyAmount = preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge
                    }
                    loyaltyApplied = loyaltyAmount
                    applyLoyalty(loyaltyAmount.toString(),"${preCheckoutModel.grandTotal?.replace("KD ","").toDouble() - sameDayCharge - deliveryCharge}")
                }else{
                    loyaltySwitch.isChecked = false
                    Toast.makeText(requireContext(),"Total amount is 0",Toast.LENGTH_LONG).show()
                }

            }else{
                loyaltyApplied = 0.0
                preCheckoutModel.grandTotal = "KD ${preCheckoutModel.grandTotal?.replace("KD ","").toDouble() + loyaltyAmount }"
                tvTotal.text = preCheckoutModel.grandTotal
            }
        }
        if (param1 != null) {
            username.text = param1?.addressFname + " " + param1?.addressLname
            addresstxt.text = param1?.addressTitle + ", " +
                    param1?.address_houseBuilding + ", " +
                    param1?.addressStreet + ", " +
                    param1?.addressCity
            addressDetailtxt.text = param1?.addressBlock + ", " +
                    param1?.addressAvanue + ", " +
                    param1?.addressGovernarate
            mobileTxt.text = getString(R.string.mob,param1?.addressMobile1)
            emailTxt.text =getString(R.string.email_,param1?.addressMail)
        }
        tvTotal.text = preCheckoutModel.total
        totalAmount = preCheckoutModel.total?.replace("KD ","").toDouble()
        val stateProgressBar =
            view.findViewById(com.example.rathaanelectronics.R.id.state_progress) as StateProgressBar
        stateProgressBar.enableAnimationToCurrentState(true)
        var descriptionData = arrayOf(getString(R.string.address_), getString(R.string.delivery), getString(R.string.payment))
        stateProgressBar.setStateDescriptionData(descriptionData)
//        val typeface:String = activity?.let { ResourcesCompat.getFont(it, R.font.roboto) }
//        stateProgressBar.setStateDescriptionTypeface(typeface);


        getPoints()
        if (preCheckoutModel.pickStore.equals("0")){
            llDelivery.visibility = View.VISIBLE
            getDelCharge(preCheckoutModel.total.replace("KD ",""),preCheckoutModel.area)
        }else{
            llDelivery.visibility = View.GONE
        }

        btn_continue?.setOnClickListener { view ->
            if (preCheckoutModel.pickStore.equals("0"))
                preCheckoutModel.deliveryType = "del"
            else
                preCheckoutModel.deliveryType = "pick"

            preCheckoutModel.loyalty = loyaltyApplied?.toString()
            preCheckoutModel.wallet = walletApplied?.toString()
            preCheckout(preCheckoutModel)

        }
        return view
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
         * @return A new instance of fragment Delivery_state_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AddressResponseModel.Details, param2: PreCheckoutModel) =
            Delivery_state_select_payment_Fragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }
    private fun getDelCharge(total:String,area:String) {

        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        var isSameDay = "0"
        if (preCheckoutModel.delOn.equals("1")){
            isSameDay = "1"
        }
        Log.d("token",token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DeliveryChargeModel> = apiService.getDeliveryCharge(
            ApiConstants.LG_APP_KEY,
            token,
            area,
            total,
            isSameDay
        )
        call.enqueue(object : Callback<DeliveryChargeModel?> {


            override fun onResponse(
                call: Call<DeliveryChargeModel?>?,
                response: Response<DeliveryChargeModel?>
            ) {
                progressBar.visibility = View.GONE
                Log.e("Add Address response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data!=null){
                            preCheckoutModel.shippingCharge = response.body()?.data?.delCharge.toString()

                            if (preCheckoutModel.delOn.equals("1")){
                                preCheckoutModel.sameDayCharge = response.body()?.data?.sameDayCharge.toString()
                                sameDayCharge = response.body()?.data?.sameDayCharge!!
                            }else{
                                preCheckoutModel.sameDayCharge = "0"
                                sameDayCharge = 0
                            }
                            var delCharge = (response.body()?.data?.delCharge!! + sameDayCharge).toString()
                            deliveryCharge = response.body()?.data?.delCharge!!
                            var grandTotal = (total?.toDouble() + response.body()?.data?.delCharge!! + sameDayCharge).toString()
                            tvDelivery.text = "KD $delCharge"
                            tvTotal.text = "KD $grandTotal"
                            preCheckoutModel.grandTotal = "KD $grandTotal"
                        }

                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    progressBar.visibility = View.GONE

                }
            }


            override fun onFailure(call: Call<DeliveryChargeModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }

    private fun preCheckout(preCheckoutModel: PreCheckoutModel) {

        var total = preCheckoutModel.total.replace("KD ","").toDouble()


        progressBar.visibility = View.VISIBLE

        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<PreCheckoutSuccessModel> = apiService.preCheckout(
            ApiConstants.LG_APP_KEY,
            token,
            preCheckoutModel.addressId,
            preCheckoutModel.deliveryType,
            preCheckoutModel.shippingCharge,
            preCheckoutModel.coupon,
            total.toString(),
            preCheckoutModel.sameDayCharge,
            preCheckoutModel.wallet,
            preCheckoutModel.loyalty,
            preCheckoutModel.orderNote,
            preCheckoutModel.delDate,
            preCheckoutModel.delTime,
            preCheckoutModel.pickStore,
            preCheckoutModel.delOn,
            "android"
        )
        call.enqueue(object : Callback<PreCheckoutSuccessModel?> {


            override fun onResponse(
                call: Call<PreCheckoutSuccessModel?>?,
                response: Response<PreCheckoutSuccessModel?>
            ) {
                progressBar.visibility = View.GONE
                Log.e("Add Address response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data!=null){
                            if (response.body()?.data?.paymentUrl!=null){
                                var paymentUrl = response.body()?.data?.paymentUrl
                                val intent = Intent(activity, WebviewActivity::class.java)
                                intent.putExtra("payment_url",paymentUrl)
                                startActivity(intent)
                            }else{
                                val intent = Intent(activity, ThankYouActivity::class.java)
                                startActivity(intent)
                                activity?.finish()
                            }

                        }else{
                            val intent = Intent(activity, ThankYouActivity::class.java)
                            startActivity(intent)
                            activity?.finish()
                        }

                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    progressBar.visibility = View.GONE

                }
            }


            override fun onFailure(call: Call<PreCheckoutSuccessModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun getPoints() {

        progressBar.visibility = View.VISIBLE

        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<PointsModel> = apiService.getPoints(
            ApiConstants.LG_APP_KEY,
            token
        )

        call.enqueue(object : Callback<PointsModel?> {


            override fun onResponse(
                call: Call<PointsModel?>?,
                response: Response<PointsModel?>
            ) {
                progressBar.visibility = View.GONE
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {
                            loyaltyTotal = response.body()?.data?.loyaltyAmount!!.toDouble()
                            walletTotalBalance = response.body()?.data?.walletAmount!!.toDouble()
//
//                            loyaltyAmount = 10.0
//                            walletBalance = 10.0
                            if (loyaltyTotal > 0) {
                                llLoyalty.visibility = View.VISIBLE
                                tvLoyaltyBalance.text = "$loyaltyTotal"
                            }
                            if (walletTotalBalance > 0) {
                                llWallet.visibility = View.VISIBLE
                                tvWalletBalance.text = "$walletTotalBalance"
                            }
                        } else {
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


            override fun onFailure(call: Call<PointsModel?>?, t: Throwable?) {
                progressBar.visibility = View.GONE
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }

    fun applyLoyalty(loyaltyApplied:String,total:String) {

        Log.d("LoyaltyApplied",loyaltyApplied)
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CoinsAppliedModel> = apiService.loyaltyAppliedTotal(
            ApiConstants.LG_APP_KEY,
            token,
            total,
            loyaltyApplied
        )

        call.enqueue(object : Callback<CoinsAppliedModel?> {


            override fun onResponse(
                call: Call<CoinsAppliedModel?>?,
                response: Response<CoinsAppliedModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {
                            preCheckoutModel.grandTotal = "KD ${response?.body()?.data?.loyaltyAppliedTotal!!.toDouble() + sameDayCharge + deliveryCharge}"
                            tvTotal.setText(preCheckoutModel.grandTotal)
                        } else {
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


            override fun onFailure(call: Call<CoinsAppliedModel?>?, t: Throwable?) {
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }

    fun applyWallet(walletApplied:String,total:String) {
        Log.d("WalletaltyApplied",walletApplied)
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CoinsAppliedModel> = apiService.walletAppliedTotal(
            ApiConstants.LG_APP_KEY,
            token,
            total,
            walletApplied
        )

        call.enqueue(object : Callback<CoinsAppliedModel?> {


            override fun onResponse(
                call: Call<CoinsAppliedModel?>?,
                response: Response<CoinsAppliedModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {
                            preCheckoutModel.grandTotal = "KD ${response?.body()?.data?.walletAppliedTotal!!.toDouble() + sameDayCharge + deliveryCharge}"
                            tvTotal.setText(preCheckoutModel.grandTotal)
                        } else {
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


            override fun onFailure(call: Call<CoinsAppliedModel?>?, t: Throwable?) {
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }
}