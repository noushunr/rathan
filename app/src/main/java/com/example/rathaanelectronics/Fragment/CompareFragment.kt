package com.example.rathaanelectronics.Fragment

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.CartCountModel
import com.example.rathaanelectronics.Model.CartResponseModel
import com.example.rathaanelectronics.Model.CompareListModel
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.example.rathaanelectronics.databinding.FragmentCompareBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CompareFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null
    private var manager: MyPreferenceManager? = null
    private lateinit var binding: FragmentCompareBinding
    var showToolBar : ShowToolBar?=null
    override fun onAttach(context: Context) {
        super.onAttach(context)

        showToolBar = context as ShowToolBar
    }

    override fun onDetach() {
        super.onDetach()
        showToolBar = null
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
        binding = FragmentCompareBinding.inflate(inflater, container, false)
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CompareFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listCompare()
    }

    private fun listCompare(){
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CompareListModel> = apiService.listCompare(
            ApiConstants.LG_APP_KEY,
            token)

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
                            for (i in 0 until data.size) {
                                val item = data[i]
                                if (i.equals(0)) {
                                    setUpProd1(item)
                                } else if (i.equals(1)) {
                                    setUpProd2(item)
                                }
                            }
                        } else {
                            binding.compareProd1Layout.visibility = View.GONE
                            binding.compareProd2Layout.visibility = View.GONE
                            binding.compareProd3Layout.visibility = View.GONE
                            binding.compareProd4Layout.visibility = View.GONE
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
                        getString(R.string.something_went),
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

    private fun setUpProd1(productData: CompareListModel.Data) {
        var productImage = ""
        if (productData.prodFrondImg!=null){
            productImage = productData.productImage?.split(",")?.toTypedArray()?.get(0)!!
        }
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL + productImage)
            .into(binding.compareProd1Img)
        if (manager?.locale?.equals("ar")!!){
            binding.compareProd1NameTxt.text = productData.productNameArab
            binding.compareProd1StockTxt.text = productData.productAvailableArabic
            binding.compareProd1DeliveryTxt.text = productData.productDeliveryOrPickupTitleArab
            binding.compareProd1DescriptionTxt.text = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(productData.productDescArab, Html.FROM_HTML_MODE_LEGACY).toString() // for 24 api and more
            } else {
                Html.fromHtml(productData.productDescArab).toString() // or for older api
            }
        }else{
            binding.compareProd1NameTxt.text = productData.productName
            binding.compareProd1StockTxt.text = productData.productAvailable
            binding.compareProd1DeliveryTxt.text = productData.productDeliveryOrPickupTitle
            binding.compareProd1DescriptionTxt.text = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(productData.productDesc, Html.FROM_HTML_MODE_LEGACY).toString() // for 24 api and more
            } else {
                Html.fromHtml(productData.productDesc).toString() // or for older api
            }
        }

        binding.compareProd1PriceTxt.text = productData.productSellPrice

        binding.compareProd1ChargeTxt.text = productData.productInstallation



        binding.compareProd1Layout.visibility = View.VISIBLE
        binding.compareProd3Layout.visibility = View.VISIBLE

        binding.compareProd1AddCartBtn.setOnClickListener {
            addToCartRequest(productData.productId, "1")
        }
    }

    private fun setUpProd2(productData: CompareListModel.Data) {
        var productImage = ""
        if (productData.prodFrondImg!=null){
            productImage = productData.productImage?.split(",")?.toTypedArray()?.get(0)!!
        }
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL + productImage)
            .into(binding.compareProd2Img)
        binding.compareProd2PriceTxt.text = productData.productSellPrice
        binding.compareProd2ChargeTxt.text = productData.productInstallation

        if (manager?.locale?.equals("ar")!!){
            binding.compareProd2NameTxt.text = productData.productNameArab
            binding.compareProd2StockTxt.text = productData.productAvailableArabic
            binding.compareProd2DeliveryTxt.text = productData.productDeliveryOrPickupTitleArab
            binding.compareProd2DescriptionTxt.text = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(productData.productDescArab, Html.FROM_HTML_MODE_LEGACY).toString() // for 24 api and more
            } else {
                Html.fromHtml(productData.productDescArab).toString() // or for older api
            }
        }else{
            binding.compareProd2NameTxt.text = productData.productName
            binding.compareProd2StockTxt.text = productData.productAvailable
            binding.compareProd2DeliveryTxt.text = productData.productDeliveryOrPickupTitle
            binding.compareProd2DescriptionTxt.text = if (Build.VERSION.SDK_INT >= 24) {
                Html.fromHtml(productData.productDesc, Html.FROM_HTML_MODE_LEGACY).toString() // for 24 api and more
            } else {
                Html.fromHtml(productData.productDesc).toString() // or for older api
            }
        }
        binding.compareProd2Layout.visibility = View.VISIBLE
        binding.compareProd4Layout.visibility = View.VISIBLE

        binding.compareProd2AddCartBtn.setOnClickListener {
            addToCartRequest(productData.productId, "1")
        }
    }

    private fun addToCartRequest(productId: String?, quantity: String){
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        LoadingDialog.showLoadingDialog(requireContext(),"")
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartResponseModel> =
            apiService.addToCart(ApiConstants.LG_APP_KEY,
                token,
                productId,
                quantity)

        call.enqueue(object : Callback<CartResponseModel?> {


            override fun onResponse(call: Call<CartResponseModel?>?, response: Response<CartResponseModel?>) {
                Log.e("Add to cart Response", response.toString() + "")
                if (response.isSuccessful()) {

                    LoadingDialog.cancelLoading()
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
                        getString(R.string.add_cart_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("Add to cart", "Adding to cart failed")
                }
            }

            override fun onFailure(call: Call<CartResponseModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })
    }
}