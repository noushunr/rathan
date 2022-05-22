package com.example.rathaanelectronics.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.rathaanelectronics.R
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.Activity.MainActivity
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.google.android.material.textfield.TextInputEditText
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
    private var param1: String? = null
    private var param2: String? = null
    lateinit var firstNametxt: TextInputEditText
    lateinit var lastNametxt: TextInputEditText
    lateinit var governaratetxt: TextInputEditText
    lateinit var areatxt: TextInputEditText
    lateinit var blockEdttxt: TextInputEditText
    lateinit var streetEdttxt: TextInputEditText
    lateinit var houseBuildiingNoEdttxt: TextInputEditText
    lateinit var avenueEdttxt:TextInputEditText
    lateinit var mobileNoEdttxt: TextInputEditText
    lateinit var secondMobileNoEdttxt: TextInputEditText
    lateinit var emailEdttxt: TextInputEditText
    lateinit var saveBtn: Button
    lateinit var addrTitleEdttxt: TextInputEditText
    lateinit var cityEdttxt: TextInputEditText
    private var manager: MyPreferenceManager? = null

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
       val view = inflater.inflate(R.layout.fragment_add_address, container, false)

        initViews(view)


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

    private fun initViews(view: View){
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

        saveBtn.setOnClickListener{
            var isError = false
            if(!checkValues(firstNametxt)){
                isError = true
            }
            if(!checkValues(lastNametxt)){
                isError = true
            }
            if(!checkValues(governaratetxt)){
                isError = true
            }
            if(!checkValues(areatxt)){
                isError = true
            }
            if(!checkValues(blockEdttxt)){
                isError = true
            }
            if(!checkValues(streetEdttxt)){
                isError = true
            }
            if(!checkValues(houseBuildiingNoEdttxt)){
                isError = true
            }
            if(!checkValues(avenueEdttxt)){
                isError = true
            }
            if(!checkValues(mobileNoEdttxt)){
                isError = true
            }
            if(!checkValues(emailEdttxt) && isValidEmail(emailEdttxt.text.toString())){
                isError = true
            }
            if(!checkValues(addrTitleEdttxt)){
                isError = true
            }
            if(!checkValues(cityEdttxt)){
                isError = true
            }

            if(!isError){
                addAddress()
            }
        }
    }

    private fun checkValues(editText: TextInputEditText): Boolean{
        if (editText.text.toString().isEmpty()) {
            editText.setError("Field can't be empty")
            return false
        } else {
            return true
        }
    }

    fun isValidEmail(emailAddress: String?): Boolean {
        return !TextUtils.isEmpty(emailAddress) && Patterns.EMAIL_ADDRESS.matcher(emailAddress)
            .matches()
    }

    private fun addAddress(){
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.addAddress(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(),
            firstNametxt.text.toString(),
            lastNametxt.text.toString(),
            addrTitleEdttxt.text.toString(),
            emailEdttxt.text.toString(),
            mobileNoEdttxt.text.toString(),
            cityEdttxt.text.toString(),
            secondMobileNoEdttxt.text.toString(),
            governaratetxt.text.toString(),
            blockEdttxt.text.toString(),
            streetEdttxt.text.toString(),
            avenueEdttxt.text.toString(),
            houseBuildiingNoEdttxt.text.toString()
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("Add Address response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        changeFragemnt(AdressFragment())
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
                        "Add address failed",
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
         * @return A new instance of fragment AddAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}