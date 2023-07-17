package com.example.rathaanelectronics.ui.Fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.ProfileModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [EditProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EditProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: ProfileModel.Data? = null
    private var param2: String? = null
    lateinit var etFirstName: TextInputEditText
    lateinit var etLastName: TextInputEditText
    lateinit var etEmail: TextInputEditText
    lateinit var etPassword: TextInputEditText
    lateinit var etConfirmPassword: TextInputEditText
    lateinit var btnSave: Button
    private val PASSWORD_PATTERN = Pattern.compile(
        "^" +
                "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +  //at least 1 lower case letter
                "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=\\S+$)" +  //no white spaces
                ".{8,}" +  //at least 8 characters
                "$"
    )
    private var manager: MyPreferenceManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.get(ARG_PARAM1) as ProfileModel.Data?
            param2 = it.getString(ARG_PARAM2)
        }
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_edit_profile, container, false)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        etFirstName = view.findViewById(R.id.et_first_name)
        etLastName = view.findViewById(R.id.et_last_name)
        etEmail = view.findViewById(R.id.et_email)
        etPassword = view.findViewById(R.id.et_password)
        etConfirmPassword = view.findViewById(R.id.et_confrm_password)
        if (param1 != null) {
            etFirstName.setText(param1?.userFirstname)
            etLastName.setText(param1?.userLastname)
            etEmail.setText(param1?.usermail)
        }
        btnSave = view.findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            var isError = false
            if (!checkValues(etFirstName)) {
                isError = true
            }
            if (!checkValues(etLastName)) {
                isError = true
            }
            if (!checkValues(etEmail)) {
                isError = true
            }
//            if(!checkValues(etPassword)){
//                isError = true
//            }
//            if(!checkValues(etConfirmPassword)){
//                isError = true
//            }
            if (!isValidEmail(etEmail.text.toString())) {
                isError = true
                etEmail!!.setError(getString(R.string.invalid_email))
            }
            if (!isValidPass(etPassword.text.toString())) {
                isError = true
                etPassword!!.setError(getString(R.string.invalid_password))
            }
            if (!etPassword.text.toString().equals(etConfirmPassword.text.toString())) {
                isError = true
                etConfirmPassword!!.setError(getString(R.string.missmatch_password))
            }
            if (!isError) {

//                activity?.onBackPressed()
               editProfile(etFirstName.text.toString()+ " " +etLastName.text.toString(),etEmail.text.toString(),etPassword.text.toString())
            }
        }
        return view
    }

    fun isValidEmail(emailAddress: String?): Boolean {
        return !TextUtils.isEmpty(emailAddress) && Patterns.EMAIL_ADDRESS.matcher(emailAddress)
            .matches()
    }

    fun isValidPass(passwordInput: String?): Boolean {
        if (passwordInput?.length!! > 0)
            return !TextUtils.isEmpty(passwordInput) && PASSWORD_PATTERN.matcher(passwordInput)
                .matches()
        else
            return true
    }

    private fun checkValues(editText: TextInputEditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            editText.setError(getString(R.string.empty_field_error))
            return false
        } else {
            return true
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: ProfileModel.Data, param2: String) =
            EditProfileFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun editProfile(Name: String, email: String, pass: String) {
        LoadingDialog.showLoadingDialog(requireContext(), "Loading...")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> =
            apiService.editProfile(
                ApiConstants.LG_APP_KEY,

                manager?.userToken,
                etFirstName.text.toString(),
                etLastName.text.toString(),
                etFirstName.text.toString() + " "+
                etLastName.text.toString(),
                email,
                pass,
                pass,
                param1?.userPassword
            )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(call: Call<CommonResponseModel?>?, response: Response<CommonResponseModel?>) {
                Log.e("Signup Response", response.toString() + "")
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: Boolean = response.body()!!.status
                    val messege: String = response.body()!!.message!!


                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status ) {
                        Toast.makeText(
                            activity,
                            messege,
                            Toast.LENGTH_SHORT
                        ).show()
                        activity?.onBackPressed()


                    } else {

                        Toast.makeText(
                            activity,
                            messege,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {
                    try{
                        if (response?.body()?.error!=null){
                            val gson = Gson() // Or use new GsonBuilder().create();

                            val errorResponse: CommonResponseModel = gson.fromJson(response?.errorBody().toString(), CommonResponseModel::class.java)
                            var message = ""
                            if (errorResponse?.error?.firstname!=null)
                                message  += errorResponse?.error?.firstname + "\n"
                            if (errorResponse?.error?.lastname!=null)
                                message  += errorResponse?.error?.lastname+ "\n"
                            if (errorResponse?.error?.email!=null)
                                message  += errorResponse?.error?.email+ "\n"
                            if (errorResponse?.error?.displayName!=null)
                                message  += errorResponse?.error?.displayName+ "\n"
                            Toast.makeText(
                                activity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e:Exception){
                        Toast.makeText(
                            activity,
                            response?.message(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }
}