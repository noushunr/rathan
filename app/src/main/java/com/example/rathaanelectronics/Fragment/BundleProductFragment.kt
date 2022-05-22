package com.example.rathaanelectronics.Fragment

import Filter_Fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.BestDealModel
import com.example.rathaanelectronics.Model.BundleProductModel
import com.example.rathaanelectronics.Model.CompareListModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.example.rathaanelectronics.databinding.FragmentBundleProductBinding
import com.example.rathaanelectronics.databinding.FragmentCompareBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BundleProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BundleProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var filterFragment = Filter_Fragment()
    var productlist: MutableList<BundleProductModel.Data> = mutableListOf()
    private var manager: MyPreferenceManager? = null
    private lateinit var binding: FragmentBundleProductBinding

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
        binding = FragmentBundleProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BundleProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BundleProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listBundleProducts()
    }

    private fun listBundleProducts() {
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BundleProductModel> = apiService.listBundleProducts(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken())

        call.enqueue(object : Callback<BundleProductModel?> {


            override fun onResponse(
                call: Call<BundleProductModel?>?,
                response: Response<BundleProductModel?>
            ) {
                Log.e("bundle response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
                    val data = response.body()?.data

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        if (!data.isNullOrEmpty()) {
                            Toast.makeText(
                                activity,
                                "List Size ${data.size}",
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
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } else {
                    Toast.makeText(
                        activity,
                        "Something went wrong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<BundleProductModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }
}