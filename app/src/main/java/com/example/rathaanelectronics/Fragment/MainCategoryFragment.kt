package com.example.rathaanelectronics.Fragment

import Filter_Fragment
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Activity.MainActivity
import com.example.rathaanelectronics.Adapter.MainCat_title_list_Adapter
import com.example.rathaanelectronics.Adapter.MainCategoryProductsAdapter
import com.example.rathaanelectronics.Adapter.Main_category_list_Adapter
import com.example.rathaanelectronics.Adapter.Top_deals_list_Adapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Interface.CategoriesTitleItemClick
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.AllCategoriesModel
import com.example.rathaanelectronics.Model.CategoryProductModel
import com.example.rathaanelectronics.Model.DealsModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"



class MainCategoryFragment : Fragment(), CategoriesTitleItemClick,
    MainCategoryProductsAdapter.MainCategoryProductsItemClick {

    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var filterFragment = Filter_Fragment()
    lateinit var recy_cat_title:RecyclerView
    lateinit var recycle_main_Cat:RecyclerView
    var Hotdeals_data: MutableList<CategoryProductModel.CategoryProduct> = mutableListOf()
    var allcategory_data: List<AllCategoriesModel.Datum> = ArrayList<AllCategoriesModel.Datum>()
    private var manager: MyPreferenceManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        manager = MyPreferenceManager(activity)
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_main_category, container, false)

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
       recy_cat_title = view.findViewById<RecyclerView>(R.id.recycle_titles)
        recycle_main_Cat = view.findViewById<RecyclerView>(R.id.recycle_main_Cat)

        recy_cat_title.layoutManager =
            LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)


        Allcategories()


        val numberOfColumns = 2
        recycle_main_Cat.layoutManager = GridLayoutManager(activity, numberOfColumns)
        recycle_main_Cat.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )
        //TopTwenty()

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(true)
        this.Menufilter = menu.findItem(R.id.filter).setVisible(true)
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
    fun Allcategories() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AllCategoriesModel> = apiService.AllCategories(ApiConstants.LG_APP_KEY)
        call.enqueue(object : Callback<AllCategoriesModel?> {


            override fun onResponse(call: Call<AllCategoriesModel?>?, response: Response<AllCategoriesModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        allcategory_data = response.body()!!.data!!

                        Log.e("Hotdeals_data", allcategory_data.size.toString())

                        catProducts(allcategory_data[0].categoryId.toString())
                    }


                    recy_cat_title.adapter = MainCat_title_list_Adapter(
                        requireActivity(),
                        allcategory_data,
                        this@MainCategoryFragment
                    )


                } else {
                }
            }


            override fun onFailure(call: Call<AllCategoriesModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }



    /*fun TopTwenty() {

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<DealsModel> = apiService.TopTwenty(ApiConstants.LG_APP_KEY,
            manager?.getUserToken())

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


                    recycle_main_Cat.adapter= Main_category_list_Adapter(requireActivity(),Hotdeals_data,this@MainCategoryFragment)




                } else {
                }
            }


            override fun onFailure(call: Call<DealsModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }*/



    fun catProducts(id: String) {

        Hotdeals_data = mutableListOf()

        recycle_main_Cat.adapter= MainCategoryProductsAdapter(requireActivity(),Hotdeals_data,this@MainCategoryFragment)

        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CategoryProductModel> = apiService.categoryProducts(ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), id)

        call.enqueue(object : Callback<CategoryProductModel?> {


            override fun onResponse(call: Call<CategoryProductModel?>?, response: Response<CategoryProductModel?>) {
                Log.e("Signin Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val messege: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", messege.toString() + "")

                    if (status == "true") {
                        Hotdeals_data = response.body()!!.data!!.products!!

                        Log.e("Toptwenty_data", Hotdeals_data.size.toString())
                    }


                    recycle_main_Cat.adapter= MainCategoryProductsAdapter(requireActivity(),Hotdeals_data,this@MainCategoryFragment)




                } else {
                }
            }


            override fun onFailure(call: Call<CategoryProductModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainCategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onTitleClicked(position: Int, item: AllCategoriesModel.Datum) {
        catProducts(item.categoryId.toString())
    }
    fun changeFragemnt(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    override fun onProductClicked(position: Int, item: CategoryProductModel.CategoryProduct) {
        val bundle = Bundle()
        bundle.putString("productId", item.productId)
        val subcategory = Product_Detail_view_Fragment()
        subcategory.arguments = bundle
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, subcategory)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }
}