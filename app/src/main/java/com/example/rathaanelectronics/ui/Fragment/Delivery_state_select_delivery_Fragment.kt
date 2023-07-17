package com.example.rathaanelectronics.ui.Fragment


import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.Utils.DateValidatorWeekdays
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.kofigyan.stateprogressbar.StateProgressBar
import kotlinx.android.synthetic.main.bottom_sheet_cart.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Delivery_state_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Delivery_state_select_delivery_Fragment : Fragment(), DatePickerDialog.OnDateSetListener {
    // TODO: Rename and change types of parameters
    private var param1: AddressResponseModel.Details? = null
    private var param2: PreCheckoutModel? = null
    var btn_continue: Button? = null
    lateinit var tvTotal: TextView
    lateinit var etOrderNote: EditText
    private var manager: MyPreferenceManager? = null
    private lateinit var rbSameDay: RadioButton
    private lateinit var rbNextDay: RadioButton
    private lateinit var rbChooseDate: RadioButton
    private lateinit var rbMorning : RadioButton
    private lateinit var rbEvening : RadioButton
    lateinit var llPickUp : LinearLayout
    lateinit var llDel : LinearLayout
    lateinit var tvStoreName: TextView
    lateinit var tvStoreAddress: TextView
    lateinit var tvStoreLocation: TextView
    lateinit var tvCheckoutUnavailable : TextView
    lateinit var llTotal : LinearLayout
    lateinit var llContinue : LinearLayout
    var deliveryTime = ""
    var deliveryOn = ""
    var deliveryDate = ""
    var orderNote = ""
    var list : MutableList<Int> = mutableListOf()

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
        val view = inflater.inflate(R.layout.fragment_delivery_state_delivery, container, false)
        btn_continue = view.findViewById(R.id.btn_continue)
        etOrderNote = view.findViewById(R.id.et_order_note)
        llDel = view.findViewById(R.id.ll_del_type)
        llPickUp = view.findViewById(R.id.ll_pickup)
        tvStoreName = view.findViewById(R.id.txt_store_name)
        tvStoreAddress = view.findViewById(R.id.txt_store_add)
        tvStoreLocation = view.findViewById(R.id.tv_location)
        tvTotal = view.findViewById(R.id.tv_total)
        rbSameDay = view.findViewById(R.id.rb_same)
        rbNextDay = view.findViewById(R.id.rb_next)
        rbChooseDate = view.findViewById(R.id.rb_date)
        rbMorning = view.findViewById(R.id.rb_mor)
        rbEvening = view.findViewById(R.id.rb_eve)
        llTotal = view.findViewById(R.id.ll_total)
        llContinue = view.findViewById(R.id.ll_conitune)
        tvCheckoutUnavailable = view.findViewById(R.id.tv_checkout_unavailable)
//        if (manager?.sameDay != null && manager?.sameDay.equals("yes", ignoreCase = true)) {
//            rbSameDay.visibility = View.VISIBLE
//            rbSameDay.isChecked = true
//            deliveryOn = "1"
//            val c = Calendar.getInstance()
//            val year = c[Calendar.YEAR]
//            val month = c[Calendar.MONTH]
//            val day = c[Calendar.DAY_OF_MONTH]
//            deliveryDate = "${year}-${month + 1}-${day}"
//            Log.d("Date", deliveryDate)
//        } else {
//            rbSameDay.visibility = View.GONE
//            rbNextDay.isChecked = true
//            deliveryOn = "2"
//            val calendar = Calendar.getInstance()
//            calendar.add(Calendar.DAY_OF_YEAR, 1)
//            val tomorrow = calendar.time
//            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
//            deliveryDate = dateFormat.format(tomorrow)
//
//        }
        tvTotal.text = preCheckoutModel.total
        if (preCheckoutModel.deliveryType.equals("del")){
            llDel.visibility = View.VISIBLE
            llPickUp.visibility = View.GONE
            checkOutAvailabilityCheck()
        }else{
            llDel.visibility = View.GONE
            llPickUp.visibility = View.VISIBLE
            tvStoreName.text = preCheckoutModel.pickStoreDetails?.storeName
            tvStoreAddress.text = preCheckoutModel.pickStoreDetails?.storeAddress
        }
        tvStoreLocation.setOnClickListener {
            try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(preCheckoutModel.pickStoreDetails?.storeMap.toString())
                startActivity(intent)
            } catch (e: Exception) {
                Log.e("home",e.message,e)
            }
        }
        btn_continue?.setOnClickListener { view ->

            if (preCheckoutModel.deliveryType.equals("del")) {
                if (deliveryOn.isEmpty()) {

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.choose_del_day),
                        Toast.LENGTH_LONG
                    ).show()
                }else if (deliveryDate.isEmpty()) {

                    Toast.makeText(
                        requireContext(),
                        getString(R.string.choose_del_day),
                        Toast.LENGTH_LONG
                    ).show()
                } else if (deliveryTime.isEmpty()) {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.choose_del_time),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    var sameDay = "0"
                    var nextDay = "0"
                    if (deliveryOn.equals("1"))
                        sameDay = "1"
                    else if (deliveryOn.equals("2"))
                        nextDay = "1"
                    delDateCheck(sameDay, nextDay)
                }
            }else{
                deliveryDate = etOrderNote.text.toString()
                changeFragemnt(
                    Delivery_state_select_payment_Fragment.newInstance(
                        param1!!,
                        preCheckoutModel!!
                    )
                )
            }

        }



        val radioGroup = view.findViewById(R.id.day_radioGroup) as RadioGroup

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rb_next -> {
                        rbChooseDate.text = getString(R.string.choose_date)
                        deliveryOn = "2"
                        val calendar = Calendar.getInstance()
                        calendar.add(Calendar.DAY_OF_YEAR, 1)
                        val tomorrow = calendar.time
                        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

                        deliveryDate = dateFormat.format(tomorrow)
                        Log.d("Date", deliveryDate)
                    }
                    R.id.rb_same -> {
                        deliveryOn = "1"
                        rbChooseDate.text = getString(R.string.choose_date)
                        val calendar = Calendar.getInstance()
                        val today = calendar.time
                        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

                        deliveryDate = dateFormat.format(today)
                        Log.d("Date", deliveryDate)
                    }


                }
                // checkedId is the RadioButton selected
            }
        })
        rbChooseDate.setOnClickListener {
//            rbSameDay.isChecked = false
//            rbNextDay.isChecked = false
//            rbChooseDate.isChecked = true
            deliveryOn = ""
            showCalendar()

        }
        val radioTime = view.findViewById(R.id.radio_time) as RadioGroup

        radioTime.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
                when (checkedId) {
                    R.id.rb_mor -> {
                        deliveryTime = "0"
                    }
                    R.id.rb_eve -> {
                        deliveryTime = "1"
                    }

                }
                // checkedId is the RadioButton selected
            }
        })

        var descriptionData = arrayOf(getString(R.string.address_), getString(R.string.delivery), getString(R.string.payment))
        val stateProgressBar = view.findViewById(R.id.state_progress) as StateProgressBar
        stateProgressBar.setStateDescriptionData(descriptionData)
        stateProgressBar.enableAnimationToCurrentState(true)
//        val typeface:String = activity?.let { ResourcesCompat.getFont(it, R.font.roboto) }
//        stateProgressBar.setStateDescriptionTypeface(typeface);

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
            Delivery_state_select_delivery_Fragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putSerializable(ARG_PARAM2, param2)
                }
            }
    }

    private fun showCalendar() {
        val constraintBuilder =
            CalendarConstraints.Builder()
//                .setStart(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
                .setValidator(DateValidatorWeekdays(list))
        val datePicker = MaterialDatePicker.Builder.datePicker()
//            .setSelection(System.currentTimeMillis() + 24 * 2 * 60 * 60 * 1000)
            .setCalendarConstraints(constraintBuilder.build())
            .setTitleText(getString(R.string.select_date))
            .build()

        datePicker.show(childFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            rbChooseDate.isChecked = true
            deliveryOn = "3"
            deliveryDate = dateFormat.format(it)
            rbChooseDate.text = deliveryDate
            datePicker.dismiss()
        }
    }

    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        deliveryOn = "3"
        deliveryDate = "${p1}-${p2 + 1}-${p3}"
        Log.d("Date", "${p1},${p2},${p3}")
    }

    private fun delDateCheck(sameDay: String, nextDay: String) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        Log.d("token", token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DeliveryDateCheckModel> = apiService.deliveryDateCheck(
            ApiConstants.LG_APP_KEY,
            token,
            "del",
            deliveryDate,
            sameDay,
            nextDay
        )
        call.enqueue(object : Callback<DeliveryDateCheckModel?> {


            override fun onResponse(
                call: Call<DeliveryDateCheckModel?>?,
                response: Response<DeliveryDateCheckModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Add Address response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {

                            if (response.body()?.data?.options!=null){
                                if (response.body()?.data?.options?.dateStatus == 1){
                                    preCheckoutModel.delOn = deliveryOn
                                    preCheckoutModel.delTime = deliveryTime
                                    preCheckoutModel.delDate = deliveryDate
                                    deliveryDate = etOrderNote.text.toString()
                                    changeFragemnt(
                                        Delivery_state_select_payment_Fragment.newInstance(
                                            param1!!,
                                            preCheckoutModel!!
                                        )
                                    )

                                }else{
                                    deliveryOn = ""
//                                    rbNextDay.isChecked = false
//                                    rbSameDay.isChecked = false
//                                    rbChooseDate.isChecked = false
                                    if (response.body()?.data?.message!=null){
                                        Toast.makeText(
                                            activity,
                                            response.body()?.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }else {
                                        Toast.makeText(
                                            activity,
                                            getString(R.string.choose_del_other_date),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                            }
                        }

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
                        getString(R.string.choose_del_other_date),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<DeliveryDateCheckModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }

    private fun checkOutAvailabilityCheck()     {

        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        Log.d("token", token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CheckoutCheckModel> = apiService.checkoutAvailabilityCheck(
            ApiConstants.LG_APP_KEY,
            token
        )
        call.enqueue(object : Callback<CheckoutCheckModel?> {


            override fun onResponse(
                call: Call<CheckoutCheckModel?>?,
                response: Response<CheckoutCheckModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {

                            if (response?.body()?.data?.availabilityCheck == 1){
                                deliveryAvailabilityCheck()
                                deliveryDaysAvailabilityCheck(preCheckoutModel.area)
                            }else{
                                rbSameDay.isEnabled = false
                                rbNextDay.isEnabled = false
                                rbChooseDate.isEnabled = false
                                rbMorning.isEnabled = false
                                rbEvening.isEnabled = false
                                llContinue.visibility = View.GONE
                                llTotal.visibility = View.GONE
                                tvCheckoutUnavailable.visibility = View.VISIBLE

                            }
                        }

                    } else {
                        if (response?.body()?.data != null) {

                            if (response?.body()?.data?.availabilityCheck == 1){
                                deliveryAvailabilityCheck()
                                deliveryDaysAvailabilityCheck(preCheckoutModel.area)
                            }else{
                                rbSameDay.isEnabled = false
                                rbNextDay.isEnabled = false
                                rbChooseDate.isEnabled = false
                                rbMorning.isEnabled = false
                                rbEvening.isEnabled = false
                                llContinue.visibility = View.GONE
                                llTotal.visibility = View.GONE
                                tvCheckoutUnavailable.visibility = View.VISIBLE

                            }
                        }
                    }

                } else {
                    Toast.makeText(
                        activity,
                        getString(R.string.something_went),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<CheckoutCheckModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }
    private fun deliveryAvailabilityCheck()     {

        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        Log.d("token", token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DeliveryDateCheckModel> = apiService.deliveryAvailabilityCheck(
            ApiConstants.LG_APP_KEY,
            token,
            "del"
        )
        call.enqueue(object : Callback<DeliveryDateCheckModel?> {


            override fun onResponse(
                call: Call<DeliveryDateCheckModel?>?,
                response: Response<DeliveryDateCheckModel?>
            ) {
                Log.e("Add Address response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {

                            if (response.body()?.data?.options!=null){
                                rbSameDay.isEnabled = response.body()?.data?.options?.sameDay == 1
                                rbNextDay.isEnabled = response.body()?.data?.options?.nextDay == 1
                                rbChooseDate.isEnabled = response.body()?.data?.options?.chooseDate == 1
                            }
                        }

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
                        response?.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<DeliveryDateCheckModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }

    private fun deliveryDaysAvailabilityCheck(area:String) {

        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        Log.d("token", token)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DeliveryDaysCheckModel> = apiService.deliveryAvailabilityDaysCheck(
            ApiConstants.LG_APP_KEY,
            token,
            area
        )
        call.enqueue(object : Callback<DeliveryDaysCheckModel?> {


            override fun onResponse(
                call: Call<DeliveryDaysCheckModel?>?,
                response: Response<DeliveryDaysCheckModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Add Address response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {

                            var dayTime = response.body()?.data?.deliveryTime
                            if (dayTime?.contains(",")!!){
                                rbMorning.isEnabled = true
                                rbEvening.isEnabled = true
                            }else{
                                if (dayTime.equals("0")){
                                    rbMorning.isEnabled = true
                                    rbEvening.isEnabled = false
                                }else{
                                    rbMorning.isEnabled = false
                                    rbEvening.isEnabled = true
                                }
                            }
                            var deliveryDays = response.body()?.data?.deliveryDays
                            if (deliveryDays?.contains(",")!!){
                                var days = deliveryDays?.split(",").toTypedArray()
                                for (i in 0..6){
                                    if (!days.contains(i.toString())){
                                        list.add(i)
                                    }
                                }
                            }else{
                                for (i in 0..6){
                                    if (deliveryDays.toInt()!=i){
                                        list.add(i)
                                    }
                                }
                            }

                        }

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
                        getString(R.string.something_went),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<DeliveryDaysCheckModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }
}