package com.example.rathaanelectronics.Fragment.DealsCategory

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.BestDeal_list_Adapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Interface.BestItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.BestDealModel
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
class BestDealsFragment : Fragment(), BestItemClick {

    // TODO: Rename and change types of parameters

    private var categoryId: String? = null
    private var param2: String? = null
    lateinit var hot_deals: RecyclerView
    var BestDeal_data: List<BestDealModel.Datum> = ArrayList<BestDealModel.Datum>()
    private var manager: MyPreferenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

            categoryId = it.getString(ARG_PARAM1)

        }
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view = inflater!!.inflate(R.layout.fragment_hot_deals, container, false)


        hot_deals = view.findViewById<View>(R.id.hot_deals) as RecyclerView

        val numberOfColumns = 2
        hot_deals.layoutManager = GridLayoutManager(activity, numberOfColumns)
        hot_deals.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )

        Bestdeal()

        return view
    }


    fun Bestdeal() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealModel> = apiService.BestDealsCatWise(ApiConstants.LG_APP_KEY,manager?.guestToken,categoryId)

        call.enqueue(object : Callback<BestDealModel?> {


            override fun onResponse(call: Call<BestDealModel?>?, response: Response<BestDealModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        BestDeal_data = response.body()!!.data!!

                        Log.e("Hotdeals_data", BestDeal_data.size.toString())
                    }


                    hot_deals.adapter= BestDeal_list_Adapter(requireActivity(),BestDeal_data,this@BestDealsFragment)




                } else {
                }
            }


            override fun onFailure(call: Call<BestDealModel?>?, t: Throwable?) {
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
        fun newInstance(param1: String) =
            BestDealsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override fun onBestItemClicked(position: Int, item: BestDealModel.Datum?) {

    }

    override fun onAddToWishListButtonClicked(productId: String) {

    }


}