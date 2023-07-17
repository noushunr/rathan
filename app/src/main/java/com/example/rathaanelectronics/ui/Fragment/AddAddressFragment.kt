package com.example.rathaanelectronics.ui.Fragment

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.rathaanelectronics.ui.Activity.MainActivity
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.AddressResponseModel
import com.example.rathaanelectronics.Model.AreaModel
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.GovernarateModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: AddressResponseModel.Details? = null
    private var param2: String? = null
    lateinit var firstNametxt: TextInputEditText
    lateinit var lastNametxt: TextInputEditText
    lateinit var governaratetxt: TextInputEditText
    lateinit var areatxt: TextInputEditText
    lateinit var blockEdttxt: TextInputEditText
    lateinit var streetEdttxt: TextInputEditText
    lateinit var houseBuildiingNoEdttxt: TextInputEditText
    lateinit var avenueEdttxt: TextInputEditText
    lateinit var mobileNoEdttxt: TextInputEditText
    lateinit var secondMobileNoEdttxt: TextInputEditText
    lateinit var emailEdttxt: TextInputEditText
    lateinit var saveBtn: Button
    lateinit var addrTitleEdttxt: TextInputEditText
    lateinit var cityEdttxt: TextInputEditText
    private var manager: MyPreferenceManager? = null
    lateinit var progressBar: ProgressBar
    lateinit var spnrGovernarate: Spinner
    lateinit var spnrArea: Spinner
    private lateinit var llBack: LinearLayout
    var goveranarate = ""
    var cityId = ""
    var alGovernarateList: List<GovernarateModel.Data> = listOf()
    var alGovernarate: MutableList<String> = mutableListOf()
    var alGovernarateArabic: MutableList<String> = mutableListOf()
    var alAreaList: List<AreaModel.Data> = listOf()
    var alArea: MutableList<String> = mutableListOf()
    var alAreaArabic: MutableList<String> = mutableListOf()
    var isCityGovernarate = false
    var isAllCity = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            param1 = it.get(com.example.rathaanelectronics.ui.Fragment.ARG_PARAM1) as AddressResponseModel.Details?
            param2 = it.getString(com.example.rathaanelectronics.ui.Fragment.ARG_PARAM2)
        }
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_address, container, false)

        initViews(view)

        getGovernarateList()
//        if (param1==null)
//            getAllAreaList()
        spnrGovernarate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position == 0){
                    goveranarate = ""
                    getAllAreaList()
                    isCityGovernarate = false
                }else{


                    goveranarate = alGovernarate[position]
                    Log.d("CityGov",goveranarate)
                    if (!isCityGovernarate)
                        getAreaList(goveranarate)
                    isCityGovernarate = false
                }

            }

        }
        spnrArea.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("PositionI",(position-1).toString())
                if (position!=0){
                    cityId = alAreaList[position-1].cityId!!
                    if (isAllCity)
                        getCityGovernarate(cityId)
                }

            }

        }

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        return view
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!
    }

    override fun onStop() {
        super.onStop()
        (activity as AppCompatActivity?)!!.supportActionBar!!
    }

    private fun initViews(view: View) {
        firstNametxt = view.findViewById(R.id.firstNametxt)
        lastNametxt = view.findViewById(R.id.lastNametxt)
        governaratetxt = view.findViewById(R.id.governaratetxt)
        areatxt = view.findViewById(R.id.areatxt)
        blockEdttxt = view.findViewById(R.id.blockEdttxt)
        streetEdttxt = view.findViewById(R.id.streetEdttxt)
        houseBuildiingNoEdttxt = view.findViewById(R.id.houseBuildiingNoEdttxt)
        avenueEdttxt = view.findViewById(R.id.avenueEdttxt)
        mobileNoEdttxt = view.findViewById(R.id.mobileNoEdttxt)
        secondMobileNoEdttxt = view.findViewById(R.id.secondMobileNoEdttxt)
        emailEdttxt = view.findViewById(R.id.emaiEdttxt)
        saveBtn = view.findViewById(R.id.btn_sign_in)
        addrTitleEdttxt = view.findViewById(R.id.addrTitleEdttxt)
        cityEdttxt = view.findViewById(R.id.cityEdttxt)
        progressBar = view.findViewById(R.id.progress_circular)
        spnrGovernarate = view.findViewById(R.id.spnr_governarate)
        spnrArea = view.findViewById(R.id.spnr_area)
        val pos: Int = spnrGovernarate.selectedItemPosition
        spnrGovernarate.setSelection(pos, false)
        val position: Int = spnrArea.selectedItemPosition
        spnrArea.setSelection(position, false)
        llBack = view.findViewById(R.id.ll_back)
        llBack.setOnClickListener {
            activity?.onBackPressed()
        }
        if (param1 != null) {
            firstNametxt.setText(param1?.addressFname!!)
            lastNametxt.setText(param1?.addressLname!!)
            governaratetxt.setText(param1?.addressGovernarate!!)
            houseBuildiingNoEdttxt.setText(param1?.address_houseBuilding!!)
            avenueEdttxt.setText(param1?.addressAvanue)
            mobileNoEdttxt.setText(param1?.addressMobile1)
            secondMobileNoEdttxt.setText(param1?.addressMobile2)
            emailEdttxt.setText(param1?.addressMail)
            addrTitleEdttxt.setText(param1?.addressTitle)
            cityEdttxt.setText(param1?.cityGovernarate)
            blockEdttxt.setText(param1?.addressBlock)
            streetEdttxt.setText(param1?.addressStreet)
//            Log.d("Governarate",param1?.addressGovernarate!!)
            Log.d("Governarate",param1?.cityGovernarate!!)
            Log.d("Governarate",param1?.cityId!!)
//            areatxt.setText(param1?.cityName!!)
        }

        saveBtn.setOnClickListener {
            var isError = false
            if (!checkValues(firstNametxt)) {
                isError = true
            }
            if (!checkValues(lastNametxt)) {
                isError = true
            }
            if (goveranarate.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.choose_governarate),
                    Toast.LENGTH_LONG
                )
                    .show()
                isError = true
            }
            if (cityId.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.choose_area), Toast.LENGTH_LONG)
                    .show()
                isError = true
            }
            if (!checkValues(blockEdttxt)) {
                isError = true
            }
            if (!checkValues(streetEdttxt)) {
                isError = true
            }
            if (!checkValues(houseBuildiingNoEdttxt)) {
                isError = true
            }
//            if (!checkValues(avenueEdttxt)) {
//                isError = true
//            }
            if (!isValidMobile(mobileNoEdttxt)) {
                isError = true
            }
            if (secondMobileNoEdttxt.text.toString().isNotEmpty()) {
                if (secondMobileNoEdttxt.text.toString().length!=8) {
                    secondMobileNoEdttxt.setError(getString(R.string.invalid_mobile))
                    isError = true
                }
            }
            if (!checkValues(emailEdttxt) && isValidEmail(emailEdttxt.text.toString())) {
                isError = true
            }
            if (!checkValues(addrTitleEdttxt)) {
                isError = true
            }
//            if (!checkValues(cityEdttxt)) {
//                isError = true
//            }

            if (!isError) {
                if (param1 == null)
                    addAddress()
                else
                    updateAddress()
            }
        }
    }

    private fun checkValues(editText: TextInputEditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            editText.setError(getString(R.string.empty_field_error))
            return false
        } else {
            return true
        }
    }


    private fun isValidMobile(editText: TextInputEditText): Boolean {
        if (editText.text.toString().length!=8) {
            editText.setError(getString(R.string.invalid_mobile))
            return false
        } else {
            return true
        }
    }
    fun isValidEmail(emailAddress: String?): Boolean {
        return !TextUtils.isEmpty(emailAddress) && Patterns.EMAIL_ADDRESS.matcher(emailAddress)
            .matches()
    }

    private fun getGovernarateList() {
        try {
            progressBar.visibility = View.VISIBLE
            val apiService = ServiceGenerator.createService(ApiInterface::class.java)
            val call: Call<GovernarateModel> = apiService.getGovernarateList(
                ApiConstants.LG_APP_KEY
            )
            call.enqueue(object : Callback<GovernarateModel?> {


                override fun onResponse(
                    call: Call<GovernarateModel?>?,
                    response: Response<GovernarateModel?>
                ) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful()) {

                        val status: String = response.body()!!.status.toString()
                        val message: String? = response.body()!!.message



                        if (status == "true") {
                            if (response.body()?.data != null) {
                                alGovernarateList = response.body()?.data!!
                                alGovernarate.add(0, "Select Governarate")
                                alGovernarateArabic.add(0, "حدد محافظة")
                                var position = 0
                                alGovernarateList.let {
                                    it.forEachIndexed { index, data ->
                                        if (param1 != null) {
                                            if (param1?.cityGovernarate != null) {
                                                if (param1?.cityGovernarate == data.cityGovernarate) {
                                                    position = index + 1
                                                }
                                            }
                                        }
                                        alGovernarate.add(data.cityGovernarate!!)
                                        alGovernarateArabic.add(data.cityGovernarateAr!!)
                                    }
                                }
                                if (manager?.locale?.equals("ar")!!) {
                                    spnrGovernarate.adapter = ArrayAdapter(
                                        context!!,
                                        android.R.layout.simple_list_item_1,
                                        alGovernarateArabic
                                    )
                                } else {
                                    spnrGovernarate.adapter = ArrayAdapter(
                                        context!!,
                                        android.R.layout.simple_list_item_1,
                                        alGovernarate
                                    )
                                }
                                isCityGovernarate = false
                                spnrGovernarate.setSelection(position)

                            }
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


                override fun onFailure(call: Call<GovernarateModel?>?, t: Throwable?) {
                    // something went completely south (like no internet connection)
                    Log.e("onFailure", t.toString())
                    progressBar.visibility = View.GONE
                }
            })
        }catch (e:Exception){

        }


    }

    private fun getAllAreaList() {
        try {
            progressBar.visibility = View.VISIBLE
            cityId = ""
            isAllCity = true
            alArea.clear()
            alAreaArabic.clear()
            val apiService = ServiceGenerator.createService(ApiInterface::class.java)
            val call: Call<AreaModel> = apiService.getAllAreaList(
                ApiConstants.LG_APP_KEY
            )
            call.enqueue(object : Callback<AreaModel?> {


                override fun onResponse(
                    call: Call<AreaModel?>?,
                    response: Response<AreaModel?>
                ) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful()) {

                        val status: String = response.body()!!.status.toString()
                        val message: String? = response.body()!!.message



                        if (status == "true") {
                            if (response.body()?.data != null) {
                                alAreaList = response.body()?.data!!
                                Log.d("CitySize",alAreaList.size.toString())
                                alArea.clear()
                                alAreaArabic.clear()
                                var position = 0
                                alArea.add(0, "Select Area")
                                alAreaArabic.add(0, "حدد المنطقة")
                                alAreaList.let {

                                    it.forEachIndexed { index, data ->
                                        if (param1 != null) {
                                            if (param1?.cityId != null) {
                                                if (param1?.cityId == data.cityId) {
                                                    position = index + 1
                                                }
                                            }
                                        }
                                        alArea.add(data.cityName!!)
                                        alAreaArabic.add(data.cityNameAr!!)
                                    }
                                }
                                if (manager?.locale.equals("ar")) {
                                    val spinnerAdapter = object : ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item, alAreaArabic) {

                                        override fun isEnabled(position: Int): Boolean {
                                            // Disable the first item from Spinner
                                            // First item will be used for hint
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int,
                                            convertView: View?,
                                            parent: ViewGroup
                                        ): View {
                                            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                                            //set the color of first item in the drop down list to gray
                                            if(position == 0) {
                                                view.setTextColor(Color.GRAY)
                                            } else {
                                                view.setTextColor(Color.BLACK)
                                                //here it is possible to define color for other items by
                                                //view.setTextColor(Color.RED)
                                            }
                                            return view
                                        }

                                    }
                                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                                    spnrArea.adapter =spinnerAdapter
                                    spnrArea.setSelection(position)
                                } else {
                                    val spinnerAdapter = object : ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item, alArea) {

                                        override fun isEnabled(position: Int): Boolean {
                                            // Disable the first item from Spinner
                                            // First item will be used for hint
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int,
                                            convertView: View?,
                                            parent: ViewGroup
                                        ): View {
                                            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                                            //set the color of first item in the drop down list to gray
                                            if(position == 0) {
                                                view.setTextColor(Color.GRAY)
                                            } else {
                                                view.setTextColor(Color.BLACK)
                                                //here it is possible to define color for other items by
                                                //view.setTextColor(Color.RED)
                                            }
                                            return view
                                        }

                                    }
                                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                                    spnrArea.adapter =spinnerAdapter
                                    spnrArea.setSelection(position)
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

                    }
                }


                override fun onFailure(call: Call<AreaModel?>?, t: Throwable?) {
                    // something went completely south (like no internet connection)
                    Log.e("onFailure", t.toString())
                    progressBar.visibility = View.GONE
                }
            })
        }catch (e:Exception){

        }


    }

    private fun getAreaList(goveranarate: String) {
        try {
            isAllCity = false
            alArea.clear()
            alAreaArabic.clear()
            cityId = ""
            progressBar.visibility = View.VISIBLE
            val apiService = ServiceGenerator.createService(ApiInterface::class.java)
            val call: Call<AreaModel> = apiService.getAreaList(
                ApiConstants.LG_APP_KEY,
                goveranarate
            )
            call.enqueue(object : Callback<AreaModel?> {


                override fun onResponse(
                    call: Call<AreaModel?>?,
                    response: Response<AreaModel?>
                ) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful()) {

                        val status: String = response.body()!!.status.toString()
                        val message: String? = response.body()!!.message



                        if (status == "true") {
                            if (response.body()?.data != null) {
                                alAreaList = response.body()?.data!!
                                alArea.clear()
                                alAreaArabic.clear()
                                Log.d("CitySize1",alAreaList.size.toString())
                                var position = 0
                                alArea.add(0, "Select Area")
                                alAreaArabic.add(0, "حدد المنطقة")
                                alAreaList.let {

                                    it.forEachIndexed { index, data ->
                                        if (param1 != null) {
                                            if (param1?.cityId != null) {
                                                if (param1?.cityId == data.cityId) {
                                                    position = index +1
                                                }
                                            }
                                        }
                                        alArea.add(data.cityName!!)
                                        alAreaArabic.add(data.cityNameAr!!)
                                    }
                                }
                                if (manager?.locale.equals("ar")) {
                                    val spinnerAdapter = object : ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item, alAreaArabic) {

                                        override fun isEnabled(position: Int): Boolean {
                                            // Disable the first item from Spinner
                                            // First item will be used for hint
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int,
                                            convertView: View?,
                                            parent: ViewGroup
                                        ): View {
                                            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                                            //set the color of first item in the drop down list to gray
                                            if(position == 0) {
                                                view.setTextColor(Color.GRAY)
                                            } else {
                                                view.setTextColor(Color.BLACK)
                                                //here it is possible to define color for other items by
                                                //view.setTextColor(Color.RED)
                                            }
                                            return view
                                        }

                                    }
                                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                                    spnrArea.adapter =spinnerAdapter

                                    spnrArea.setSelection(position)
                                } else {
                                    val spinnerAdapter = object : ArrayAdapter<String>(requireContext(),android.R.layout.simple_spinner_item, alArea) {

                                        override fun isEnabled(position: Int): Boolean {
                                            // Disable the first item from Spinner
                                            // First item will be used for hint
                                            return position != 0
                                        }

                                        override fun getDropDownView(
                                            position: Int,
                                            convertView: View?,
                                            parent: ViewGroup
                                        ): View {
                                            val view: TextView = super.getDropDownView(position, convertView, parent) as TextView
                                            //set the color of first item in the drop down list to gray
                                            if(position == 0) {
                                                view.setTextColor(Color.GRAY)
                                            } else {
                                                view.setTextColor(Color.BLACK)
                                                //here it is possible to define color for other items by
                                                //view.setTextColor(Color.RED)
                                            }
                                            return view
                                        }

                                    }
                                    spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                                    spnrArea.adapter =spinnerAdapter

                                    spnrArea.setSelection(position)
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

                    }
                }


                override fun onFailure(call: Call<AreaModel?>?, t: Throwable?) {
                    // something went completely south (like no internet connection)
                    Log.e("onFailure", t.toString())
                    progressBar.visibility = View.GONE
                }
            })
        }catch (e:Exception){

        }


    }

    private fun getCityGovernarate(cityId: String) {
        try {
            Log.d("CityIdv",cityId!!)
            progressBar.visibility = View.VISIBLE
            val apiService = ServiceGenerator.createService(ApiInterface::class.java)
            val call: Call<GovernarateModel> = apiService.getCityGovernarate(
                ApiConstants.LG_APP_KEY,
                cityId
            )
            call.enqueue(object : Callback<GovernarateModel?> {


                override fun onResponse(
                    call: Call<GovernarateModel?>?,
                    response: Response<GovernarateModel?>
                ) {
                    progressBar.visibility = View.GONE
                    if (response.isSuccessful()) {

                        val status: String = response.body()!!.status.toString()
                        val message: String? = response.body()!!.message



                        if (status == "true") {
                            if (response.body()?.data != null) {
                                val cityGovernarate = response.body()?.data!!
                                Log.d("CityGov",cityGovernarate.get(0).cityGovernarate!!)
                                var position = -1
                                alGovernarateList.let {
                                    it.forEachIndexed { index, data ->
                                        cityGovernarate.let {
                                            if (data.cityGovernarate.equals(it.get(0).cityGovernarate)) {
                                                position = index + 1;
                                                return@forEachIndexed
                                            }
                                        }
                                    }
                                }
                                Log.d("Position",position.toString())
                                isCityGovernarate = true
                                spnrGovernarate.setSelection(position)

                            }
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


                override fun onFailure(call: Call<GovernarateModel?>?, t: Throwable?) {
                    // something went completely south (like no internet connection)
                    Log.e("onFailure", t.toString())
                    progressBar.visibility = View.GONE
                }
            })
        }catch (e:Exception){

        }


    }

    private fun addAddress() {
        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.addAddress(
            ApiConstants.LG_APP_KEY,
            token,
            firstNametxt.text.toString().trim(),
            lastNametxt.text.toString().trim(),
            addrTitleEdttxt.text.toString().trim(),
            emailEdttxt.text.toString().trim(),
            mobileNoEdttxt.text.toString().trim(),
            cityId,
            secondMobileNoEdttxt.text.toString().trim(),
            goveranarate,
            blockEdttxt.text.toString().trim(),
            streetEdttxt.text.toString().trim(),
            avenueEdttxt.text.toString().trim(),
            houseBuildiingNoEdttxt.text.toString().trim()
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = if (manager?.locale.equals("en")) {
                        response.body()!!.message
                    } else {
                        response.body()!!.messageAr
                    }
                    if (status == "true") {
                        activity?.onBackPressed()
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        if (response?.errorBody() != null) {
                            val gson = Gson() // Or use new GsonBuilder().create();

                            val errorResponse: CommonResponseModel = gson.fromJson(
                                response?.errorBody().toString(),
                                CommonResponseModel::class.java
                            )
                            var message = ""
                            if (errorResponse?.error?.fname != null)
                                message += errorResponse?.error?.fname + "\n"
                            if (errorResponse?.error?.lname != null)
                                message += errorResponse?.error?.lname + "\n"
                            if (errorResponse?.error?.adrtitle != null)
                                message += errorResponse?.error?.adrtitle + "\n"
                            if (errorResponse?.error?.city != null)
                                message += errorResponse?.error?.city + "\n"
                            if (errorResponse?.error?.mail != null)
                                message += errorResponse?.error?.mail + "\n"
                            if (errorResponse?.error?.mobile1 != null)
                                message += errorResponse?.error?.mobile1 + "\n"
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
                    }

                } else {
                    try {
                        if (response?.body()?.error != null) {
                            val gson = Gson() // Or use new GsonBuilder().create();

                            val errorResponse: CommonResponseModel = gson.fromJson(
                                response?.errorBody().toString(),
                                CommonResponseModel::class.java
                            )
                            var message = ""
                            if (errorResponse?.error?.fname != null)
                                message += errorResponse?.error?.fname + "\n"
                            if (errorResponse?.error?.lname != null)
                                message += errorResponse?.error?.lname + "\n"
                            if (errorResponse?.error?.adrtitle != null)
                                message += errorResponse?.error?.adrtitle + "\n"
                            if (errorResponse?.error?.city != null)
                                message += errorResponse?.error?.city + "\n"
                            if (errorResponse?.error?.mail != null)
                                message += errorResponse?.error?.mail + "\n"
                            if (errorResponse?.error?.mobile1 != null)
                                message += errorResponse?.error?.mobile1 + "\n"
                            Toast.makeText(
                                activity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {

                    }


                }
            }


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
                progressBar.visibility = View.GONE
            }
        })

    }

    //------------------------------------------------------------------------------------------
    private fun updateAddress() {
        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        Log.d("Token", token)
        Log.d("AddressId", param1?.addressId!!)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.updateAddress(
            ApiConstants.LG_APP_KEY,
            token,
            param1?.addressId,
            cityId,
            mobileNoEdttxt.text.toString().trim(),
            secondMobileNoEdttxt.text.toString().trim(),

            addrTitleEdttxt.text.toString().trim(),
            firstNametxt.text.toString().trim(),
            lastNametxt.text.toString().trim(),
            goveranarate,
            blockEdttxt.text.toString().trim(),
            streetEdttxt.text.toString().trim(),
            avenueEdttxt.text.toString().trim(),
            houseBuildiingNoEdttxt.text.toString().trim(),
            emailEdttxt.text.toString().trim()
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                progressBar.visibility = View.GONE
                Log.e("Add Address response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = if (manager?.locale.equals("en")) {
                        response.body()!!.message
                    } else {
                        response.body()!!.messageAr
                    }

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        changeFragemnt(com.example.rathaanelectronics.ui.Fragment.AdressFragment())
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
                    try {
                        if (response?.body()?.error != null) {
                            val gson = Gson() // Or use new GsonBuilder().create();

                            val errorResponse: CommonResponseModel = gson.fromJson(
                                response?.errorBody().toString(),
                                CommonResponseModel::class.java
                            )
                            var message = ""
                            if (errorResponse?.error?.fname != null)
                                message += errorResponse?.error?.fname + "\n"
                            if (errorResponse?.error?.lname != null)
                                message += errorResponse?.error?.lname + "\n"
                            if (errorResponse?.error?.adrtitle != null)
                                message += errorResponse?.error?.adrtitle + "\n"
                            if (errorResponse?.error?.city != null)
                                message += errorResponse?.error?.city + "\n"
                            if (errorResponse?.error?.mail != null)
                                message += errorResponse?.error?.mail + "\n"
                            if (errorResponse?.error?.mobile1 != null)
                                message += errorResponse?.error?.mobile1 + "\n"
                            Toast.makeText(
                                activity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {

                    }
                }
            }


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())

            }
        })

    }

    fun changeFragemnt(fragment: Fragment) {
        activity?.onBackPressed()
//        val transaction = activity?.supportFragmentManager?.beginTransaction()
//        transaction?.replace(R.id.frame, fragment)
//        transaction?.addToBackStack(null)
//        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction?.commit()

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: AddressResponseModel.Details, param2: String) =
            com.example.rathaanelectronics.ui.Fragment.AddAddressFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(com.example.rathaanelectronics.ui.Fragment.ARG_PARAM1, param1)
                    putString(com.example.rathaanelectronics.ui.Fragment.ARG_PARAM2, param2)
                }
            }
    }
}