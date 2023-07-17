package com.example.rathaanelectronics.ui.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.ui.Activity.MainActivity
import com.example.rathaanelectronics.Utils.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.Product
import com.example.rathaanelectronics.Model.SearchProductModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.example.rathaanelectronics.ui.Adapter.SearchProductsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SearchProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchProductFragment : Fragment(), HotdealsItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recycle_products:RecyclerView
    lateinit var edtSearch: EditText
    lateinit var tvClose : TextView
    private var manager: MyPreferenceManager? = null
    var alSearch: MutableList<Product> = mutableListOf()
    var adapter : SearchProductsAdapter?=null
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
        val view = inflater.inflate(R.layout.fragment_search_product, container, false)

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()

        recycle_products = view.findViewById<RecyclerView>(R.id.recycle_products)
        edtSearch = view.findViewById(R.id.edt_home_keyword_srch)
        tvClose = view.findViewById(R.id.tv_close)
        tvClose.setOnClickListener {
            activity?.onBackPressed()
        }

        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                searchProduct(edtSearch.text.toString())
            }
        })

        edtSearch.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchProduct(edtSearch.text.toString())
                return@OnEditorActionListener true
            }
            false
        })

        val numberOfColumns = 2
        recycle_products.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        recycle_products.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )

        adapter = SearchProductsAdapter(
            requireActivity(),
            alSearch,this,manager?.locale.equals("ar")
        )
        recycle_products.adapter = adapter
        return view
    }

    fun searchProduct(searchText : String) {

        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
//        var loadingDialogue = LoadingDialogue()
//        loadingDialogue?.showLoadingDialog(context,"Loading..")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<SearchProductModel> = apiService.searchProducts(
            ApiConstants.LG_APP_KEY,
            token,
           searchText,"")
        call.enqueue(object : Callback<SearchProductModel?> {


            override fun onResponse(call: Call<SearchProductModel?>?, response: Response<SearchProductModel?>) {
//                loadingDialogue.cancelLoading()
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    alSearch.clear()
                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    var products = listOf<Product>()
                    if (status == "true") {
                        products = response.body()!!.data!!.data!!

                        alSearch.addAll(products)
                        Log.e("Hotdeals_data", products.size.toString())
                    }


                    adapter?.notifyDataSetChanged()



//                    adapter.notifyDataSetChanged()
                } else {
                    alSearch.clear()
                    adapter?.notifyDataSetChanged()
                }
            }


            override fun onFailure(call: Call<SearchProductModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
//                loadingDialogue?.ca
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
         * @return A new instance of fragment SearchProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onHotdealsClicked(position: Int, item: Product?) {

    }

    override fun onAddToWishlistButtonClick(productId: String) {
        val bundle = Bundle()
        bundle.putString("productId", productId)
        val subcategory = Product_Detail_view_Fragment()
        subcategory.arguments = bundle
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, subcategory)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {

    }

    override fun onAddToCart(productId: String) {

    }

}