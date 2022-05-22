package com.example.rathaanelectronics.Fragment

import Filter_Fragment
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.BestDeal_list_Adapter
import com.example.rathaanelectronics.Adapter.Subcat_list_Adapter

import com.example.rathaanelectronics.Adapter.Top_deals_list_Adapter
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
 * Use the [SubCategory_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Deals_offer_Fragment : Fragment(), BestItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var filterFragment = Filter_Fragment()
    lateinit var recycle_subcat:RecyclerView
    var BestDeal_data: List<BestDealModel.Datum> = ArrayList<BestDealModel.Datum>()
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

        val view = inflater.inflate(R.layout.fragment_deals_offer, container, false)

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()

        recycle_subcat = view.findViewById<RecyclerView>(R.id.recycle_subcat)


        val numberOfColumns = 2
        recycle_subcat.layoutManager = GridLayoutManager(activity, numberOfColumns)
        recycle_subcat.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )
       recycle_subcat.adapter


        Bestdeal()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.filter).setVisible(true)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(true)
        super.onCreateOptionsMenu(menu, inflater)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter -> {
                changeFragemnt(filterFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun changeFragemnt(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    fun Bestdeal() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BestDealModel> = apiService.dealsAndOffers(ApiConstants.LG_APP_KEY, manager?.getUserToken())

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


                    recycle_subcat.adapter= Subcat_list_Adapter(requireActivity(),BestDeal_data,this@Deals_offer_Fragment)




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
         * @return A new instance of fragment SubCategory_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Deals_offer_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onBestItemClicked(position: Int, item: BestDealModel.Datum?) {
        val bundle = Bundle()
        bundle.putString("productId", item?.productId)
        val productDetailFragment = Product_Detail_view_Fragment()
        productDetailFragment.arguments = bundle
        changeFragemnt(productDetailFragment)

    }

    override fun onAddToWishListButtonClicked(productId: String) {
        TODO("Not yet implemented")
    }
}