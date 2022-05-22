package com.example.rathaanelectronics.Fragment.DealsCategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.Top_deals_list_Adapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.DealsModel
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
class TopTwentyFragment : Fragment(), HotdealsItemClick {

    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null
    lateinit var hot_deals: RecyclerView
    var Hotdeals_data: List<DealsModel.Datum> = ArrayList<DealsModel.Datum>()
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

        hot_deals = view.findViewById<View>(R.id.hot_deals) as RecyclerView

        val numberOfColumns = 2
        hot_deals.layoutManager = GridLayoutManager(activity, numberOfColumns)
        hot_deals.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )
       // hot_deals.adapter = top_deals_list_Adapter

        hot_deals.setOnClickListener { view ->
            Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()

//            val subcategory = SubCategory_Fragment()
//            val transaction = activity?.supportFragmentManager?.beginTransaction()
//            transaction?.replace(R.id.home_fragment, subcategory)
//            transaction?.addToBackStack(null)
//            transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//            transaction?.commit()
        }
        TopTwenty()

        return view
    }


    fun TopTwenty() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DealsModel> = apiService.TopTwenty(ApiConstants.LG_APP_KEY,
            manager?.getGuestToken())

        call.enqueue(object : Callback<DealsModel?> {


            override fun onResponse(call: Call<DealsModel?>?, response: Response<DealsModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        Hotdeals_data = response.body()!!.data!!

                        Log.e("Toptwenty_data", Hotdeals_data.size.toString())
                    }


                    hot_deals.adapter= Top_deals_list_Adapter(requireActivity(),Hotdeals_data,this@TopTwentyFragment)




                } else {
                }
            }


            override fun onFailure(call: Call<DealsModel?>?, t: Throwable?) {
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
            TopTwentyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onHotdealsClicked(position: Int, item: DealsModel.Datum?) {



    }

    override fun onAddToWishlistButtonClick(productId: String) {
        TODO("Not yet implemented")
    }


}