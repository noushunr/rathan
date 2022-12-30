package com.example.rathaanelectronics.Fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.example.rathaanelectronics.Adapter.BundleOfferAdapter
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.BundleItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.example.rathaanelectronics.databinding.FragmentBundleProductBinding
import com.example.rathaanelectronics.databinding.FragmentBundleProductDetailBinding
import kotlinx.android.synthetic.main.fragment_oder_cancel.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BundleProductDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BundleProductDetailFragment : Fragment(), BundleItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var manager: MyPreferenceManager? = null
    private lateinit var binding: FragmentBundleProductDetailBinding
    var productId = ""
    var productlist: List<BundleProductModel.Data> = mutableListOf()
    var offerId = ""
    var showToolBar : ShowToolBar?=null
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
        manager = MyPreferenceManager(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBundleProductDetailBinding.inflate(inflater, container, false)
        bundleOfferDetail(param1!!)
        handleCartQuantity()

        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.ivCart.setOnClickListener {
            changeFragemnt(Cart_Fragment())
        }
        binding?.btnAddToCart.setOnClickListener {
            val quantity = binding.cartQty.text.toString()
            addToCartRequest(param1, quantity)
//            if (manager?.getUserToken()!!.isNotEmpty()) {
//                addToCartRequest(productId, quantity)
//            } else {
//                startActivity(Intent(activity, Sign_in_Activity::class.java))
//            }

        }

        binding.cartIncrement.setOnClickListener {
            var quantity = Integer.parseInt(binding.cartQty.text.toString()) + 1
            binding.cartQty.setText(quantity.toString())
            handleCartQuantity()
        }

        binding.cartDecrement.setOnClickListener {
            var quantity = Integer.parseInt(binding.cartQty.text.toString()) - 1
            binding.cartQty.setText(quantity.toString())
            handleCartQuantity()
        }
        binding.addToWishListTxt.setOnClickListener {
            if (productId != null) {
                addToWishlist(param1!!)

            }
        }

        binding.removeWishListTxt.setOnClickListener {
            if (productId != null) {
                deleteWishListItem(param1!!)
            }
        }
        return binding.root
    }

    private fun handleCartQuantity() {
        val quantity = Integer.parseInt(binding.cartQty.text.toString())
        binding.cartDecrement.isEnabled = quantity > 1
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BundleProductDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BundleProductDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun bundleOfferDetail(offerId : String) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BundleDetailModel> = apiService.bundleProductDetails(
            ApiConstants.LG_APP_KEY,
            token,offerId)

        call.enqueue(object : Callback<BundleDetailModel?> {


            override fun onResponse(
                call: Call<BundleDetailModel?>?,
                response: Response<BundleDetailModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
                    val data = response.body()?.data

                    if (status == "true") {
                        if (data!=null) {

                            if (data?.bundleDetails!=null)
                                setDetails(data?.bundleDetails!!)
                            if (!data.relatedOffer.isNullOrEmpty()){
                                productlist = data?.relatedOffer!!
                                var adapter = BundleOfferAdapter(activity!!,data.relatedOffer!!,this@BundleProductDetailFragment,manager?.locale.equals("ar",ignoreCase = true))
                                binding.recyRealatedProduct.adapter = adapter
                            }else{
                                binding.tvRelated.visibility = View.GONE
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


            override fun onFailure(call: Call<BundleDetailModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
                Log.e("onFailure", t.toString())
            }
        })

    }
    fun setDetails(bundleDetails: BundleDetailModel.BundleDetails){

        productId = bundleDetails.offers_products_id!!
        if (bundleDetails.offers_hastimer.equals("yes")) {
            binding.llTimer.visibility = View.VISIBLE
            binding.tvLabel.visibility = View.VISIBLE
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            try {
                val date: Date = format.parse(bundleDetails.offers_to + " 23:59:59")
                val currentDate = Calendar.getInstance().time
                val difference = currentDate?.time?.let { date?.time?.minus(it) }
                var seconds = difference?.div(1000)
                var minutes = seconds?.div(60)
                var hours = minutes?.div(60)
                var days =
                    TimeUnit.HOURS.toDays(difference?.let { TimeUnit.MILLISECONDS.toDays(it) }!!)


                val countDownTimer = object : CountDownTimer(difference, 1000) {
                    override fun onTick(p0: Long) {
                        val millis: Long = p0
                        var diffInSec = TimeUnit.MILLISECONDS.toSeconds(millis)
                        seconds = diffInSec % 60
                        diffInSec /= 60
                        minutes = diffInSec % 60
                        diffInSec /= 60
                        hours = diffInSec % 24
                        diffInSec /= 24
                        days = diffInSec
//                        Log.d("CountDown", "$days days $hours Hours $minutes minutes $seconds Seconds")
                        binding?.tvDays.text = days?.toString()
                        binding?.tvHours.text = hours?.toString()
                        binding?.tvMins.text = minutes?.toString()
                        binding?.tvSeconds.text = seconds?.toString()

                    }

                    override fun onFinish() {
                        /*clearing all fields and displaying countdown finished message          */
//                binding.textView_TimeCountDown?.setText("Count down completed");
                        System.out.println("Time up")
                    }
                }
                countDownTimer.start()
            } catch (e: Exception) {

            }
        }else{
            binding.llTimer.visibility = View.GONE
            binding.tvLabel.visibility = View.GONE
        }
        Glide.with(requireContext()).load(ApiConstants.IMAGE_BASE_URL + bundleDetails.offers_image)
            .into(binding?.ivProduct)
        binding?.tvOfferName?.text = bundleDetails?.offers_text1
        binding?.tvProductName?.text = getString(R.string.bundle_product_name,bundleDetails?.offers_products_names)
        var amount = bundleDetails?.offers_bundle_price?.toDouble()?.times(100.0)?.let { Math.round(it) }?.div(100.0)
        binding?.tvAmount.text = "KD $amount"

        if (bundleDetails?.wishlist_exist  == 0){
            binding?.addToWishListTxt.visibility = View.VISIBLE
            binding?.removeWishListTxt.visibility = View.GONE
        }else{
            binding?.addToWishListTxt.visibility = View.GONE
            binding?.removeWishListTxt.visibility = View.VISIBLE
        }
    }

    override fun onItemClicked(position: Int, item: BundleProductModel.Data?) {
        changeFragemnt(BundleProductDetailFragment.newInstance(item?.offers_id!!,""))
    }

    override fun onAddToWishlistButtonClick(productId: String) {
        addToWishlist(productId)
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {
        deleteWishListItem(productId)
    }

    fun addToWishlist(productId: String) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.bundleAdToWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    if (status == "true") {
                        if (productId == param1) {
                            binding?.addToWishListTxt.visibility = View.GONE
                            binding?.removeWishListTxt.visibility = View.VISIBLE
                        }
                        var position = 0
                        productlist?.forEachIndexed { index, data ->
                            if (data?.offers_id == productId){
                                position = index
                                productlist[position].wishlist_exist = 1
                                return@forEachIndexed
                            }
                        }

                        binding.recyRealatedProduct.adapter?.notifyItemChanged(position)
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
//                    bundleOfferDetail(productId)

                } else {
                    LoadingDialog.cancelLoading()
                    Toast.makeText(
                        activity,
                        getString(R.string.wishlist_added_failed),
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
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CommonResponseModel> = apiService.bundleDeleteWishList(
            ApiConstants.LG_APP_KEY,
            token, productId
        )

        call.enqueue(object : Callback<CommonResponseModel?> {


            override fun onResponse(
                call: Call<CommonResponseModel?>?,
                response: Response<CommonResponseModel?>
            ) {
                LoadingDialog.cancelLoading()
                Log.e("Delete Response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        var position = 0
                        productlist?.forEachIndexed { index, data ->
                            if (data?.offers_id == productId){
                                position = index
                                productlist[position].wishlist_exist = 0
                                return@forEachIndexed
                            }
                        }

                        binding.recyRealatedProduct.adapter?.notifyItemChanged(position)
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                        if (productId == param1) {
                            binding?.addToWishListTxt.visibility = View.VISIBLE
                            binding?.removeWishListTxt.visibility = View.GONE
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {
                    LoadingDialog.cancelLoading()
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
    private fun addToCartRequest(productId: String?, quantity: String) {
        LoadingDialog.showLoadingDialog(requireContext(),"")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<CartResponseModel> =
            apiService.bundleAddToCart(
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
                LoadingDialog.cancelLoading()
                // something went completely south (like no internet connection)
                Log.e("onFailure", t.toString())
            }
        })
    }
    fun changeFragemnt(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.frame, fragment)
        transaction?.addToBackStack(null)
        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction?.commit()
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
                        showToolBar?.updateCartCount(cartCount!!)
                        showToolBar?.updateWalletCount(response.body()!!.data?.wishlistCount!!)
                        if (cartCount!! > 0) {
                            binding.llBadge.visibility = View.VISIBLE
                            binding.tvCount.text = "$cartCount"


                        } else {

                            binding.llBadge.visibility = View.GONE
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