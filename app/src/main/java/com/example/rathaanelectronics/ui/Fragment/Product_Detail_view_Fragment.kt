package com.example.rathaanelectronics.ui.Fragment

import android.content.Context
import android.content.Intent
import android.graphics.Paint
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
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.ui.Activity.MainActivity
import com.example.rathaanelectronics.ui.Activity.SignInActivity
import com.example.rathaanelectronics.ui.Adapter.Top_deals_list_Adapter
import com.example.rathaanelectronics.Utils.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Utils.LoadingDialog
import com.example.rathaanelectronics.Interface.HotdealsItemClick
import com.example.rathaanelectronics.Interface.RelatedItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Data.ApiConstants
import com.example.rathaanelectronics.Data.ApiInterface
import com.example.rathaanelectronics.Data.ServiceGenerator
import com.example.rathaanelectronics.ui.Adapter.ImageAdapter
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
    lateinit var recy_realated_product: RecyclerView
    lateinit var rvProductImages: ViewPager2
    private lateinit var ll_addto_compare: LinearLayout
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
    lateinit var productPricetxt: TextView
    lateinit var productPriceOffertxt: TextView
    lateinit var addToWishListTxt: TextView
    lateinit var removeWishListTxt: TextView
    lateinit var addToCompareTxt: TextView
    lateinit var llAtribute: LinearLayout
    lateinit var tvAttrName: TextView
    lateinit var tvAttrValue: TextView
    lateinit var tvNotAvailable: TextView
    lateinit var llQuantity: LinearLayout
    lateinit var llAddCart: LinearLayout
    private lateinit var llBadge: LinearLayout
    private lateinit var tvCount: TextView
    lateinit var ivCart: ImageView
    private lateinit var tvLoyaltyCoins: TextView
    lateinit var progressBar: ProgressBar

    val MINIMUM_CART_QUANTITY = 1

    var prodAttrs: List<ProductDetailsModel.ProdAttrs>? = null

    private var manager: MyPreferenceManager? = null
    var showToolBar: ShowToolBar? = null
    var relatedProductList: MutableList<Product> = mutableListOf()
    var images : MutableList<String> = mutableListOf()
    var adapter: Top_deals_list_Adapter? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)

        showToolBar = context as ShowToolBar
    }

    override fun onDetach() {
        super.onDetach()
        showToolBar = null
    }

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
        val view = inflater.inflate(R.layout.fragment_product__single_view, container, false)

        setHasOptionsMenu(true);
        (activity as MainActivity?)?.settoolbar()
        //(activity as MainActivity?)?.hideBottomnavigationView()

        var productId = arguments?.getString("productId")

        initViews(view, productId)

        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        recy_realated_product = view.findViewById<RecyclerView>(R.id.recy_realated_product)
        rvProductImages = view.findViewById(R.id.rv_images)
        ll_addto_compare = view.findViewById<LinearLayout>(R.id.ll_addto_compare)
        ivCart = view.findViewById(R.id.iv_cart)
        ivCart.setOnClickListener {
            changeFragemnt(Cart_Fragment())
        }
        /*ll_addto_compare.setOnClickListener {
            changeFragemnt(CompareFragment())
        }*/

//        val top_deals_list_Adapter = Top_deals_list_Adapter(
//            activity,
//            Hotdeals_data,
//            this@HotDealsFragment
//        )

        val numberOfColumns = 2
        recy_realated_product.layoutManager =
            GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
//        rvProductImages.layoutManager =
//            GridLayoutManager(activity, 1, GridLayoutManager.HORIZONTAL, false)
        recy_realated_product.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )
        //TopTwenty()


        Log.d("ProductId", productId!!)
        getProductDetails(productId)
        getCartCount()


        return view
    }

    private fun initViews(view: View, productId: String?) {
        incrementButton = view.findViewById(R.id.cart_increment)
        decrementButton = view.findViewById(R.id.cart_decrement)
        quantityTxtView = view.findViewById(R.id.cart_qty)
        addToCartButton = view.findViewById(R.id.btn_add_to_cart)
        quantityTxtView.setText("1")
        productImgview = view.findViewById(R.id.imageView)
        productName = view.findViewById(R.id.productName)
        productSKUtxt = view.findViewById(R.id.productSKUtxt)
        productAvailabilitytxt = view.findViewById(R.id.productAvailabilitytxt)
        brandImgView = view.findViewById(R.id.brandImgView)
        deliveryTxt = view.findViewById(R.id.deliveryTxt)
        storeDetailsTxt = view.findViewById(R.id.storeDetailsTxt)
        installationChargeTxt = view.findViewById(R.id.installationChargeTxt)
        productDescriptionTxt = view.findViewById(R.id.productDescriptionTxt)
        productPricetxt = view.findViewById(R.id.productPricetxt)
        productPriceOffertxt = view.findViewById(R.id.tv_amount)
        addToWishListTxt = view.findViewById(R.id.addToWishListTxt)
        removeWishListTxt = view.findViewById(R.id.removeWishListTxt)
        addToCompareTxt = view.findViewById(R.id.addToCompareTxt)
        llAtribute = view.findViewById(R.id.ll_attribute)
        tvAttrName = view.findViewById(R.id.attrName)
        tvAttrValue = view.findViewById(R.id.attrValue)
        tvNotAvailable = view.findViewById(R.id.tv_not_available)
        llAddCart = view.findViewById(R.id.ll_add_cart)
        llQuantity = view.findViewById(R.id.ll_qty)
        progressBar = view.findViewById(R.id.progress_circular)
        llBadge = view.findViewById(R.id.ll_badge)
        tvCount = view.findViewById(R.id.tv_count)
        tvLoyaltyCoins = view.findViewById(R.id.loyalty_points)
        handleCartQuantity()

        addToCartButton.setOnClickListener {
            val quantity = quantityTxtView.text.toString()
            addToCartRequest(productId, quantity)
//            if (manager?.getUserToken()!!.isNotEmpty()) {
//                addToCartRequest(productId, quantity)
//            } else {
//                startActivity(Intent(activity, Sign_in_Activity::class.java))
//            }

        }

        incrementButton.setOnClickListener {
            var quantity = Integer.parseInt(quantityTxtView.text.toString()) + 1
            quantityTxtView.setText(quantity.toString())
            handleCartQuantity()
        }

        decrementButton.setOnClickListener {
            var quantity = Integer.parseInt(quantityTxtView.text.toString()) - 1
            quantityTxtView.setText(quantity.toString())
            handleCartQuantity()
        }

        addToWishListTxt.setOnClickListener {
            if (productId != null) {
                addToWishlist(productId, false)

            }
        }

        removeWishListTxt.setOnClickListener {
            if (productId != null) {
                deleteWishListItem(productId, false)
            }
        }

        addToCompareTxt.setOnClickListener {
            // Handle compare functionalities
            if (productId != null) {
                //addToCompare(productId)
                if (manager?.getUserToken()!!.isNotEmpty()) {
                    listCompare(productId, true)
                } else {
                    startActivity(Intent(activity, SignInActivity::class.java))
                }

            }
        }
    }

    private fun setUIValues(productData: ProductDetailsModel.ProductData) {
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL + productData.prodFrondImg)
            .into(productImgview)

        if (manager?.locale.equals("ar")) {
            productName.text = productData.productNameArab
            deliveryTxt.text = productData.productDeliveryOrPickupTitleArab
            productAvailabilitytxt.text = productData.productAvailableArabic
        }
        else {
            productName.text = productData.productName
            deliveryTxt.text = productData.productDeliveryOrPickupTitle
            productAvailabilitytxt.text = productData.productAvailable
        }
        productSKUtxt.text = productData.productSKU
        if (productData.productAvailable.equals("out of stock", ignoreCase = true))
            productAvailabilitytxt.setTextColor(resources.getColor(R.color.red))
        else
            productAvailabilitytxt.setTextColor(resources.getColor(R.color.green))

        if (productData.loyaltyPoints!! > 0) {
            tvLoyaltyCoins.visibility = View.VISIBLE
            tvLoyaltyCoins.text =
                getString(R.string.loyalty_coins, productData.loyaltyPoints)
        } else {
            tvLoyaltyCoins.visibility = View.GONE
        }
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL + productData.brandPic)
            .into(brandImgView)

        storeDetailsTxt.text = productData.productPickupStore
        installationChargeTxt.text = productData.productInstallation
        manager?.saveSameDay(productData.productSamedayDelivery)
//        productDescriptionTxt.text = productData.productDesc
        if (!productData.productSpofferPrice?.isNullOrEmpty()!! && productData.productSpofferPrice!!.toDouble() > 0) {
            if (productData.productOldPriceShow != null && (productData.productOldPriceShow.equals("1") or productData.productOldPriceShow.equals(
                    "yes",
                    ignoreCase = true
                ))
            ) {
                productPriceOffertxt.visibility = View.VISIBLE
                productPricetxt.text = "KD ${productData.productSpofferPrice}"
                productPriceOffertxt.text = "KD ${productData.productSellPrice}"
                productPriceOffertxt.setPaintFlags(productPriceOffertxt.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            } else {
                productPriceOffertxt.visibility = View.GONE
                productPricetxt.text = "KD ${productData.productSpofferPrice}"
            }

        } else {
            productPriceOffertxt.visibility = View.GONE
            productPricetxt.text = "KD ${productData.productSellPrice}"
        }
        if (manager?.userToken.isNullOrEmpty()) {
            if (productData.productAvailableFor != null && productData.productAvailableFor == "1") {
                llAddCart.visibility = View.GONE
                llQuantity.visibility = View.GONE
                tvNotAvailable.visibility = View.VISIBLE
            } else {
                llAddCart.visibility = View.VISIBLE
                llQuantity.visibility = View.VISIBLE
                tvNotAvailable.visibility = View.GONE
            }
        } else {
            llAddCart.visibility = View.VISIBLE
            llQuantity.visibility = View.VISIBLE
            tvNotAvailable.visibility = View.GONE
        }

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
        var description = if(manager?.locale.equals("ar")) productData.productDescArab else productData.productDesc
        if (description?.isNullOrEmpty()!!){
            description = if(manager?.locale.equals("ar")) productData.productInstructionArab else productData.productInstruction
        }

        if (Build.VERSION.SDK_INT >= 24) {
            productDescriptionTxt.text =
                Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY)
                    .toString() // for 24 api and more
        } else {
            productDescriptionTxt.text =
                Html.fromHtml(description).toString() // or for older api
        }

        if (prodAttrs != null && prodAttrs?.isNotEmpty()!!) {
            llAtribute.visibility = View.VISIBLE
            tvAttrName.text = prodAttrs!![0].attributeName
            tvAttrValue.text = prodAttrs!![0].attributeValue
        }
        //productDescriptionTxt.text = Html.fromHtml(productData.productShortDesc,Html.FROM_HTML_MODE_LEGACY)

    }

    private fun setRelatedItemValues() {
        adapter =
            Top_deals_list_Adapter(
                requireActivity(),
                relatedProductList,
                this@Product_Detail_view_Fragment,
                manager?.locale.equals("ar", ignoreCase = true)
            )
        recy_realated_product.adapter = adapter


    }

    private fun handleCartQuantity() {
        val quantity = Integer.parseInt(quantityTxtView.text.toString())
        decrementButton.isEnabled = quantity > MINIMUM_CART_QUANTITY
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {


        inflater.inflate(R.menu.cart_menu, menu)
        // this.Menufilter = menu.findItem(R.id.filter).setVisible(true)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(false)
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

    override fun onHotdealsClicked(position: Int, item: Product?) {

        val bundle = Bundle()
        bundle.putString("productId", item?.productId)
        val subcategory = Product_Detail_view_Fragment()
        subcategory.arguments = bundle
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, subcategory)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
    }

    override fun onAddToWishlistButtonClick(productId: String) {
        addToWishlist(productId, true)
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {
        deleteWishListItem(productId, true)
    }

    override fun onAddToCart(productId: String) {

    }
    private fun addToCartRequest(productId: String?, quantity: String) {
        LoadingDialog.showLoadingDialog(requireContext(), "")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartResponseModel> =
            apiService.addToCart(
                ApiConstants.LG_APP_KEY,
                token,
                productId,
                quantity
            )

        call.enqueue(object : Callback<CartResponseModel?> {


            override fun onResponse(
                call: Call<CartResponseModel?>?,
                response: Response<CartResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Add to cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: Boolean = response.body()!!.status
                    val message: String? = if (manager?.locale.equals("en")){ response.body()!!.message } else{
                        response.body()!!.messageAr
                    }

                    Log.e("status", status.toString() + "")
                    Log.e("messege", message.toString() + "")

                    if (status) {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        getCartCount()
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
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    private fun getProductDetails(productId: String?) {
        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.userToken?.isNullOrEmpty()!!) {
            token = manager?.guestToken!!
        } else {
            token = manager?.userToken!!
        }
        Log.d("Token", manager?.getUserToken()!!)
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<ProductDetailsModel> =
            apiService.getProductDetails(
                ApiConstants.LG_APP_KEY,
                token,
                productId
            )

        call.enqueue(object : Callback<ProductDetailsModel?> {


            override fun onResponse(
                call: Call<ProductDetailsModel?>?,
                response: Response<ProductDetailsModel?>
            ) {
                Log.e("Add to cart Response", response.toString() + "")
                progressBar.visibility = View.GONE
                if (response.isSuccessful()) {

                    try {
                        val status: Boolean = response.body()!!.status
                        val message: String? = response.body()!!.message

                        Log.e("status", status.toString() + "")
                        Log.e("messege", message.toString() + "")

                        if (status) {
                            relatedProductList.clear()
                            prodAttrs = response.body()?.prodAttrs
                            setUIValues(response.body()!!.data!!)
                            relatedProductList.addAll(response.body()?.relatedProducts!!)
                            images.clear()
                            images.addAll(response.body()?.productPics!!)
                            Log.d("imageSize",images.size.toString())
                            var adapter = ImageAdapter(activity,images)
                            rvProductImages.adapter = adapter
                            setRelatedItemValues()

                        } else {
                            Toast.makeText(
                                activity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: Exception) {

                        e.printStackTrace()
                    }

                } else {
//                    Toast.makeText(
//                        activity,
//                        "Adding to Cart failed",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Log.e("Add to cart", "Adding to cart failed")
                }
            }

            override fun onFailure(call: Call<ProductDetailsModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                progressBar.visibility = View.GONE
                Log.e("onFailure", t.toString())
            }
        })
    }


    fun addToWishlist(productId: String, isRelated: Boolean) {
        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<WishlistModel> = apiService.addToWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<WishlistModel?> {


            override fun onResponse(
                call: Call<WishlistModel?>?,
                response: Response<WishlistModel?>
            ) {
                progressBar.visibility = View.GONE
                Log.e("Add wishlist response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    var message: String? = response.body()!!.message
                    if (response.body()?.data!=null){
                        message = if (manager?.locale.equals("en")){ response.body()!!.data?.message } else{
                            response.body()!!.data?.messageAr
                        }
                    }

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        getCartCount()
                        if (isRelated) {

                            var position = 0
                            relatedProductList?.forEachIndexed { index, product ->
                                if (product.productId == productId) {
                                    position = index
                                    relatedProductList[index].wishlistExist = 1
                                    return@forEachIndexed
                                }
                            }
                            adapter?.notifyItemChanged(position)

                        } else {
                            removeWishListTxt.visibility = View.VISIBLE
                            addToWishListTxt.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }


                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        activity,
                        getString(R.string.wishlist_added_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }


            override fun onFailure(call: Call<WishlistModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }

    fun deleteWishListItem(productId: String?, isRelated: Boolean) {
        progressBar.visibility = View.VISIBLE
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.deleteWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                progressBar.visibility = View.GONE
                Log.e("Delete Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    var message: String? = if (manager?.locale.equals("en")){ response.body()!!.message } else{
                        response.body()!!.messageAr
                    }

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        getCartCount()
                        if (isRelated) {

                            var position = 0
                            relatedProductList?.forEachIndexed { index, product ->
                                if (product.productId == productId) {
                                    position = index
                                    relatedProductList[index].wishlistExist = 0
                                    return@forEachIndexed
                                }
                            }
                            adapter?.notifyItemChanged(position)

                        } else {
                            removeWishListTxt.visibility = View.GONE
                            addToWishListTxt.visibility = View.VISIBLE
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {
                    progressBar.visibility = View.GONE
                    Toast.makeText(
                        activity,
                        getString(R.string.wishlist_removed_failed),
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
        addToWishlist(productId, true)
    }

    override fun onDeleteWishListButtonClicked(productId: String) {

    }

    override fun onItemClicked(productId: String) {

    }

    fun listCompare(productId: String, add: Boolean) {
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CompareListModel> = apiService.listCompare(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken()
        )

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

    fun addToCompare(productId: String) {
        LoadingDialog.showLoadingDialog(requireContext(), "")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AddToCompareResponseModel> = apiService.addToCompare(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), productId
        )

        call.enqueue(object : Callback<AddToCompareResponseModel?> {


            override fun onResponse(
                call: Call<AddToCompareResponseModel?>?,
                response: Response<AddToCompareResponseModel?>
            ) {
                Log.e("Add to compare response", response.toString() + "")
                LoadingDialog.cancelLoading()
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
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }

    fun removeCompare(compareId: String) {
        LoadingDialog.showLoadingDialog(requireContext(), "")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<AddToCompareResponseModel> = apiService.removeCompare(
            ApiConstants.LG_APP_KEY,
            manager?.getUserToken(), compareId
        )

        call.enqueue(object : Callback<AddToCompareResponseModel?> {


            override fun onResponse(
                call: Call<AddToCompareResponseModel?>?,
                response: Response<AddToCompareResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
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
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }

    fun getCartCount() {


        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartCountModel> = apiService.getCartCount(
            ApiConstants.LG_APP_KEY,
            token
        )

        call.enqueue(object : Callback<CartCountModel?> {


            override fun onResponse(
                call: Call<CartCountModel?>?,
                response: Response<CartCountModel?>
            ) {
                Log.e("Guest token response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        var cartCount = response.body()!!.data?.count
                        var wishListCount = response.body()!!.data?.wishlistCount
                        showToolBar?.updateCartCount(cartCount!!)
                        showToolBar?.updateWalletCount(response.body()!!.data?.wishlistCount!!)
                        if (cartCount!! > 0) {
                            llBadge.visibility = View.VISIBLE
                            tvCount.text = "$cartCount"


                        } else {

                            llBadge.visibility = View.GONE
                        }

                    } else {
                    }

                } else {

                }
            }


            override fun onFailure(call: Call<CartCountModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })

    }
}