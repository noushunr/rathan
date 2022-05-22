package com.example.rathaanelectronics.Fragment.DealsCategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.New_arrival_list_Adapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Interface.NewArrivalItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.NewArrivalModel
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
 * Use the [HotDealsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewArrivalsFragment : Fragment(), NewArrivalItemClick {

    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    lateinit var Rcy_newArrival: RecyclerView
    var NewArrival_data: List<NewArrivalModel.Datum> = ArrayList<NewArrivalModel.Datum>()
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


        val view = inflater!!.inflate(R.layout.fragment_hot_deals, container, false)

       // val top_deals_list_Adapter = Top_deals_list_Adapter(requireActivity())

        Rcy_newArrival = view.findViewById<View>(R.id.hot_deals) as RecyclerView

        val numberOfColumns = 2
        Rcy_newArrival.layoutManager = GridLayoutManager(activity, numberOfColumns)
        Rcy_newArrival.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )

        NewArrivals()

        return view
    }

    override fun onResume() {

        super.onResume()

    }

    fun NewArrivals() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<NewArrivalModel> = apiService.NewArrivals(ApiConstants.LG_APP_KEY,
            manager?.getGuestToken())

        call.enqueue(object : Callback<NewArrivalModel?> {


            override fun onResponse(call: Call<NewArrivalModel?>?, response: Response<NewArrivalModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        NewArrival_data = response.body()!!.data!!

                        Log.e("NewArrival_data", NewArrival_data.size.toString())
                    }


                    Rcy_newArrival.adapter= New_arrival_list_Adapter(requireActivity(),NewArrival_data,this@NewArrivalsFragment)




                } else {
                }
            }


            override fun onFailure(call: Call<NewArrivalModel?>?, t: Throwable?) {
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
         * @return A new instance of fragment HotDealsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewArrivalsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onNewArrivalClicked(position: Int, item: NewArrivalModel.Datum?) {
        TODO("Not yet implemented")
    }

    override fun onAddToWishListButtonClick(productId: String) {
        TODO("Not yet implemented")
    }


}