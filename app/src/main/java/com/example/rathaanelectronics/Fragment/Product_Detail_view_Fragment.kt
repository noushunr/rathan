package com.example.rathaanelectronics.Fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Activity.MainActivity
import com.example.rathaanelectronics.Activity.Sign_in_Activity
import com.example.rathaanelectronics.Adapter.RelatedItemsAdapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Interface.RelatedItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Product_Single_view_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Product_Detail_view_Fragment : Fragment(), HotdealsItemClick, RelatedItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    //var Hotdeals_data: List<DealsModel.Datum> = ArrayList<DealsModel.Datum>()
    private var Menufilter: MenuItem? = null
    lateinit var recy_realated_product:RecyclerView
   private lateinit var ll_addto_compare:LinearLayout
   lateinit var incrementButton: TextView
   lateinit var decrementButton: TextView
   lateinit var quantityTxtView: TextView
   lateinit var addToCartButton: Button
   lateinit var productImgview: ImageView
   lateinit var productName: TextView
   lateinit var productSKUtxt: TextView
   lateinit var productAvailabilitytxt: TextView
   lateinit var brandImgView: ImageView
   lateinit var deliveryTxt: TextView
   lateinit var storeDetailsTxt: TextView
   lateinit var installationChargeTxt: TextView
   lateinit var productDescriptionTxt: TextView
   lateinit var productPricetxt:TextView
   lateinit var addToWishListTxt: TextView
    lateinit var removeWishListTxt: TextView
   lateinit var addToCompareTxt: TextView
   val MINIMUM_CART_QUANTITY = 1
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
        val view =inflater.inflate(R.layout.fragment_product__single_view, container, false)

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        //(activity as MainActivity?)?.hideBottomnavigationView()

        var productId = arguments?.getString("productId")

        initViews(view, productId)


         recy_realated_product = view.findViewById<RecyclerView>(R.id.recy_realated_product)
     ll_addto_compare = view.findViewById<LinearLayout>(R.id.ll_addto_compare)

        /*ll_addto_compare.setOnClickListener {
            changeFragemnt(CompareFragment())
        }*/

//        val top_deals_list_Adapter = Top_deals_list_Adapter(
//            activity,
//            Hotdeals_data,
//            this@HotDealsFragment
//        )

        val numberOfColumns = 2
        recy_realated_product.layoutManager = GridLayoutManager(activity, numberOfColumns)
        recy_realated_product.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )
        //TopTwenty()


        getProductDetails(productId)


        return view
    }

    private fun initViews(view: View, productId: String?){
        incrementButton = view.findViewById(R.id.cart_increment)
        decrementButton = view.findViewById(R.id.cart_decrement)
        quantityTxtView = view.findViewById(R.id.cart_qty)
        addToCartButton = view.findViewById(R.id.btn_add_to_cart)
        quantityTxtView.setText("1")
        productImgview = view.findViewById(R.id.imageView)
        productName = view.findViewById(R.id.productName)
        productSKUtxt  = view.findViewById(R.id.productSKUtxt)
        productAvailabilitytxt = view.findViewById(R.id.productAvailabilitytxt)
        brandImgView = view.findViewById(R.id.brandImgView)
        deliveryTxt = view.findViewById(R.id.deliveryTxt)
        storeDetailsTxt = view.findViewById(R.id.storeDetailsTxt)
        installationChargeTxt = view.findViewById(R.id.installationChargeTxt)
        productDescriptionTxt = view.findViewById(R.id.productDescriptionTxt)
        productPricetxt = view.findViewById(R.id.productPricetxt)
        addToWishListTxt = view.findViewById(R.id.addToWishListTxt)
        removeWishListTxt = view.findViewById(R.id.removeWishListTxt)
        addToCompareTxt = view.findViewById(R.id.addToCompareTxt)


        handleCartQuantity()

        addToCartButton.setOnClickListener{
            val quantity = quantityTxtView.text.toString()
            if(manager?.getUserToken()!!.isNotEmpty()){
                addToCartRequest(productId, quantity)
            }else{
                startActivity(Intent(activity, Sign_in_Activity::class.java))
            }

        }

        incrementButton.setOnClickListener{
            var quantity = Integer.parseInt(quantityTxtView.text.toString()) + 1
            quantityTxtView.setText(quantity.toString())
            handleCartQuantity()
        }

        decrementButton.setOnClickListener{
            var quantity = Integer.parseInt(quantityTxtView.text.toString()) - 1
            quantityTxtView.setText(quantity.toString())
            handleCartQuantity()
        }

        addToWishListTxt.setOnClickListener{
            if (productId != null) {
                addToWishlist(productId)
            }
        }

        removeWishListTxt.setOnClickListener{
            if (productId != null) {
                deleteWishListItem(productId)
            }
        }

        addToCompareTxt.setOnClickListener{
            // Handle compare functionalities
            if (productId != null) {
                //addToCompare(productId)
                listCompare(productId, true)
            }
        }
    }

    private fun setUIValues(productData: ProductDetailsModel.ProductData) {
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL + productData.productImage)
            .into(productImgview)
        productName.text = productData.productName
        productSKUtxt.text = productData.productSKU
        productAvailabilitytxt.text = productData.productAvailable
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL + productData.brandPic)
            .into(brandImgView)
        deliveryTxt.text = productData.productDeliveryOrPickupTitle
        storeDetailsTxt.text = productData.productPickupStore
        installationChargeTxt.text = productData.productInstallation
        productDescriptionTxt.text = productData.productShortDesc
        productPricetxt.text = productData.productSellPrice
        when (productData.wishlist_exist) {
            "1" -> {
                removeWishListTxt.visibility = View.VISIBLE
                addToWishListTxt.visibility = View.GONE
            }
            else -> {
            removeWishListTxt.visibility = View.GONE
            addToWishListTxt.visibility = View.VISIBLE
            }
        }
        lateinit var text:String
        if (Build.VERSION.SDK_INT >= 24) {
            productDescriptionTxt.text = Html.fromHtml(productData.productShortDesc, Html.FROM_HTML_MODE_LEGACY).toString() // for 24 api and more
        } else {
            productDescriptionTxt.text = Html.fromHtml(productData.productShortDesc).toString() // or for older api
        }

       //productDescriptionTxt.text = Html.fromHtml(productData.productShortDesc,Html.FROM_HTML_MODE_LEGACY)

    }

    private fun setRelatedItemValues(productList: List<ProductDetailsModel.RelatedProducts>){
        recy_realated_product.adapter= RelatedItemsAdapter(requireActivity(),productList, this@Product_Detail_view_Fragment)


    }

    private fun handleCartQuantity(){
        val quantity = Integer.parseInt(quantityTxtView.text.toString())
        if(quantity <= MINIMUM_CART_QUANTITY){
            decrementButton.isEnabled  = false
        } else{
            decrementButton.isEnabled = true
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.cart_menu, menu)
       // this.Menufilter = menu.findItem(R.id.filter).setVisible(true)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(true)
        super.onCreateOptionsMenu(menu, inflater)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart -> {
               // changeFragemnt(filterFragment)
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
    fun TopTwenty() {

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
//                        Hotdeals_data = response.body()!!.data!!
//
//                        Log.e("Toptwenty_data", Hotdeals_data.size.toString())
                    }


                   // recy_realated_product.adapter= Top_deals_list_Adapter(requireActivity(),Hotdeals_data,this@Product_Detail_view_Fragment)




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
         * @return A new instance of fragment Product_Single_view_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Product_Detail_view_Fragment().apply {
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

    private fun addToCartRequest(productId: String?, quantity: String){
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartResponseModel> =
            apiService.addToCart(ApiConstants.LG_APP_KEY,
                manager?.getUserToken(),
                productId,
                quantity)

        call.enqueue(object : Callback<CartResponseModel?> {


            override fun onResponse(call: Call<CartResponseModel?>?, response: Response<CartResponseModel?>) {
                Log.e("Add to cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: Boolean = response.body()!!.status
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status) {
                        Toast.makeText(
                            activity,
                            message,
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
                        "Adding to Cart failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Add to cart", "Adding to cart failed")
                }
            }

            override fun onFailure(call: Call<CartResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun getProductDetails(productId: String?){
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<ProductDetailsModel> =
            apiService.getProductDetails(ApiConstants.LG_APP_KEY,
                manager?.getUserToken(),
                productId)

        call.enqueue(object : Callback<ProductDetailsModel?> {


            override fun onResponse(call: Call<ProductDetailsModel?>?, response: Response<ProductDetailsModel?>) {
                Log.e("Add to cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    try {
                        val status: Boolean = response.body()!!.status
                        val message: String? = response.body()!!.message

                        Log.e("status", status.toString() + "")
                        Log.e("messege", message.toString() + "")

                        if (status) {
                            setUIValues(response.body()!!.data!!)
                            setRelatedItemValues(response.body()?.relatedProducts!!)
                        } else {
                            Toast.makeText(
                                    activity,
                                    message,
                                    Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e:Exception){

                    }

                } else {
                    Toast.makeText(
                        activity,
                        "Adding to Cart failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Add to cart", "Adding to cart failed")
                }
            }

            override fun onFailure(call: Call<ProductDetailsModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }


    fun addToWishlist(productId: String) {
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.addToWishList(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("Add wishlist response", response.toString() + "")
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
                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    getProductDetails(productId)

                } else {
                    Toast.makeText(
                        activity,
                        "Add to wishlist failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun deleteWishListItem(productId: String?) {
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.deleteWishList(ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), productId)

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                Log.e("Delete Response", response.toString() + "")
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
                        getProductDetails(productId)
                    }else{
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {
                    Toast.makeText(
                        activity,
                        "Deleting failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<CommonResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    override fun onAddToWishListButtonClicked(productId: String) {
        addToWishlist(productId)
    }

    fun listCompare(productId: String, add: Boolean){
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CompareListModel> = apiService.listCompare(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken())

        call.enqueue(object : Callback<CompareListModel?> {


            override fun onResponse(
                call: Call<CompareListModel?>?,
                response: Response<CompareListModel?>
            ) {
                Log.e("Add to compare response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
                    val data = response.body()?.data

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        if (!data.isNullOrEmpty()) {
                            if (add) {
                                if (data.size > 1) {
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setTitle("Alert")
                                    builder.setMessage("Compare list is full. Do you want to clear the list")
                                    builder.setPositiveButton("Clear") { dialog1, _ ->
                                        dialog1.dismiss()
                                        for (item in data) {
                                            removeCompare(item.compareId.toString())
                                        }
                                    }
                                    builder.setNegativeButton("Cancel") { dialog1, _ ->
                                        dialog1.dismiss()
                                    }
                                    builder.setCancelable(true)
                                    builder.show()
                                } else {
                                    addToCompare(productId)
                                }
                            } else {
                                for (item in data) {
                                    removeCompare(item.compareId.toString())
                                }
                            }
                        } else {
                            if (add)
                                addToCompare(productId)
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
                        "Add to compare list failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<CompareListModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }

    fun addToCompare(productId: String){
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AddToCompareResponseModel> = apiService.addToCompare(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), productId)

        call.enqueue(object : Callback<AddToCompareResponseModel?> {


            override fun onResponse(
                call: Call<AddToCompareResponseModel?>?,
                response: Response<AddToCompareResponseModel?>
            ) {
                Log.e("Add to compare response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
                    val data = response.body()?.data

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        if (data != null) {
                            when (data.flag) {
                                1 -> {
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setTitle("Alert")
                                    builder.setMessage(message)
                                    builder.setPositiveButton("Clear") { dialog1, _ ->
                                        dialog1.dismiss()
                                        listCompare(productId, false)
                                    }
                                    builder.setNegativeButton("Cancel") { dialog1, _ ->
                                        dialog1.dismiss()
                                    }
                                    builder.setCancelable(true)
                                    builder.show()
                                }
                                else -> {
                                    Toast.makeText(
                                        activity,
                                        message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    changeFragemnt(CompareFragment())
                                }
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
                        "Add to compare list failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<AddToCompareResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }

    fun removeCompare(compareId: String){
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AddToCompareResponseModel> = apiService.removeCompare(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), compareId)

        call.enqueue(object : Callback<AddToCompareResponseModel?> {


            override fun onResponse(
                call: Call<AddToCompareResponseModel?>?,
                response: Response<AddToCompareResponseModel?>
            ) {
                Log.e("Add to compare response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
                    val data = response.body()?.data

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        Toast.makeText(
                            activity,
                            message,
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
                        "Add to compare list failed",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<AddToCompareResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }
}