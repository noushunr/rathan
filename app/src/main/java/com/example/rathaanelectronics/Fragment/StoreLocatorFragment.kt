package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.PickUpShop_list_Adapter
import com.example.rathaanelectronics.Interface.PickUpStoreClickListener
import com.example.rathaanelectronics.Model.PickUpStoreModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StoreLocatorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StoreLocatorFragment : Fragment(), PickUpStoreClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recystorelocator: RecyclerView
    var PickUpStoreList: List<PickUpStoreModel.Detail> = ArrayList<PickUpStoreModel.Detail>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_store_locator, container, false)
        recystorelocator = view.findViewById(R.id.recystorelocator) as RecyclerView
        recystorelocator!!.layoutManager =
            LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        pickUpStoreList()
        return view

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


                    recystorelocator!!.adapter = PickUpShop_list_Adapter(
                        requireActivity(),
                        PickUpStoreList,
                        this@StoreLocatorFragment
                    )


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
         * @return A new instance of fragment StoreLocatorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StoreLocatorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onPickStoreClicked(position: Int, item: PickUpStoreModel.Detail) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(item.storeMap.toString())
            startActivity(intent)
        } catch (e: Exception) {
            Log.e("home",e.message,e)
        }
    }
}