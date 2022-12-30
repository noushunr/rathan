package com.example.rathaanelectronics.Fragment

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rathaanelectronics.Common.DateValidatorWeekdays
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CommonResponseModel
import com.example.rathaanelectronics.Model.DeliveryDateCheckModel
import com.example.rathaanelectronics.Model.ReturnPolicyModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.example.rathaanelectronics.databinding.FragmentBundleProductBinding
import com.example.rathaanelectronics.databinding.FragmentReturnPolicyBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReturnPolicyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReturnPolicyFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentReturnPolicyBinding
    lateinit var manager : MyPreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        manager = MyPreferenceManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReturnPolicyBinding.inflate(inflater, container, false)
        binding?.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.tvPurchaseDate.setOnClickListener {
            showCalendar()
        }
        binding?.btnSave?.setOnClickListener {
            if (binding?.etName?.text?.toString()?.isEmpty()!!) {
                binding.etName.setError(getString(R.string.empty_field_error))
                binding.etName.requestFocus()
            } else if (binding?.etMobile?.text?.toString()?.isEmpty()!!) {
                binding.etMobile.setError(getString(R.string.empty_field_error))
                binding.etMobile.requestFocus()
            } else if (binding?.etEmail?.text?.toString()?.isEmpty()!!) {
                binding.etEmail.setError(getString(R.string.empty_field_error))
                binding.etEmail.requestFocus()
            } else if (!isValidEmail(binding?.etEmail?.text?.toString()!!)) {
                binding.etEmail.setError(getString(R.string.invalid_email))
                binding.etEmail.requestFocus()
            } else {

                submit()
            }
        }
        returnPolicy()
        return binding.root
    }

    fun isValidEmail(emailAddress: String?): Boolean {
        return !TextUtils.isEmpty(emailAddress) && Patterns.EMAIL_ADDRESS.matcher(emailAddress)
            .matches()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReturnPolicyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReturnPolicyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun returnPolicy() {

        LoadingDialog.showLoadingDialog(requireContext(), "")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<ReturnPolicyModel> = apiService.getReturnPolicy(
            ApiConstants.LG_APP_KEY,
            ""
        )
        call.enqueue(object : Callback<ReturnPolicyModel?> {


            override fun onResponse(
                call: Call<ReturnPolicyModel?>?,
                response: Response<ReturnPolicyModel?>
            ) {
                Log.e("Add Address response", response.toString() + "")
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {

                        if (response?.body()?.data != null) {
                            var content = ""
                            content = if (manager?.locale.equals("ar"))
                                response.body()?.data?.returnPolicyContent_ar!!
                            else
                                response.body()?.data?.returnPolicyContent!!
                            binding.tvReturnPolicy.text = if (Build.VERSION.SDK_INT >= 24) {
                                Html.fromHtml(
                                    content,
                                    Html.FROM_HTML_MODE_LEGACY
                                ).toString() // for 24 api and more
                            } else {
                                Html.fromHtml(content)
                                    .toString() // or for older api
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


            override fun onFailure(call: Call<ReturnPolicyModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }

    private fun showCalendar() {
        val constraintBuilder =
            CalendarConstraints.Builder().setStart(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
            .setCalendarConstraints(constraintBuilder.build())
            .setTitleText("Select Date")
            .build()

        datePicker.show(childFragmentManager, "DATE_PICKER")
        datePicker.addOnPositiveButtonClickListener {
            val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")

            var deliveryDate = dateFormat.format(it)
            binding?.tvPurchaseDate.text = deliveryDate
            datePicker.dismiss()
        }
    }


    private fun submit() {

        LoadingDialog.showLoadingDialog(requireContext(), "")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.submitReturnPolicy(
            ApiConstants.LG_APP_KEY,
            "",
            binding.etName.text.toString(),
            binding.etEmail.text.toString(),
            binding.etMobile.text.toString(),
            binding.etInvoice.text.toString(),
            binding.tvPurchaseDate.text.toString(),
            binding.etReason.text.toString(),
            )
        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("Add Address response", response.toString() + "")
                LoadingDialog.cancelLoading()
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
                        activity?.onBackPressed()

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


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }
}