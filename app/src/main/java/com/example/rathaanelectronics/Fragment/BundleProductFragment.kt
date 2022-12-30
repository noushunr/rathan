package com.example.rathaanelectronics.Fragment

import Filter_Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.BundleOfferAdapter
import com.example.rathaanelectronics.Common.EqualSpacingItemDecoration
import com.example.rathaanelectronics.Common.LoadingDialog
import com.example.rathaanelectronics.Interface.BundleItemClick
import com.example.rathaanelectronics.Interface.ShowToolBar
import com.example.rathaanelectronics.Interface.TimerItemClick
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.Model.*
import com.example.rathaanelectronics.R
import com.example.rathaanelectronics.Rest.ApiConstants
import com.example.rathaanelectronics.Rest.ApiInterface
import com.example.rathaanelectronics.Rest.ServiceGenerator
import com.example.rathaanelectronics.databinding.FragmentBundleProductBinding
import com.example.rathaanelectronics.databinding.FragmentCompareBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BundleProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BundleProductFragment : Fragment(), BundleItemClick {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var filterFragment = Filter_Fragment()
    var productlist: List<BundleProductModel.Data> = mutableListOf()
    var filtredList: MutableList<BundleProductModel.Data> = mutableListOf()
    private var manager: MyPreferenceManager? = null
    private lateinit var binding: FragmentBundleProductBinding

    var min = -1f
    var max = -1f
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
        manager = MyPreferenceManager(activity)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBundleProductBinding.inflate(inflater, container, false)
        val numberOfColumns = 2
        binding.recycleBundle.layoutManager = GridLayoutManager(activity, numberOfColumns)
        binding.ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        binding.ivCart.setOnClickListener {
            changeFragemnt(Cart_Fragment())
        }
        binding.recycleBundle.addItemDecoration(
            EqualSpacingItemDecoration(
                15,
                EqualSpacingItemDecoration.GRID
            )
        )
        listBundleProducts()
        binding?.ivFilter.setOnClickListener {
            val fragment = Filter_Fragment.newInstance("", "")
            changeFragemnt(fragment)
        }
        requireActivity().supportFragmentManager.setFragmentResultListener(
            "124",
            viewLifecycleOwner
        ) { id, data ->
            min = data.getFloat("min")
            max = data.getFloat("max")

        }
        getCartCount()
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BundleProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BundleProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun listBundleProducts() {
        LoadingDialog.showLoadingDialog(requireContext(), "")
        var token = ""
        if (manager?.getUserToken().isNullOrEmpty())
            token = manager?.guestToken!!
        else
            token = manager?.userToken!!
        val apiService = ServiceGenerator.createService(ApiInterface::class.java)
        val call: Call<BundleProductModel> = apiService.listBundleProducts(
            ApiConstants.LG_APP_KEY,
            token
        )

        call.enqueue(object : Callback<BundleProductModel?> {


            override fun onResponse(
                call: Call<BundleProductModel?>?,
                response: Response<BundleProductModel?>
            ) {
                LoadingDialog.cancelLoading()
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message
                    val data = response.body()?.data

                    if (status == "true") {
                        if (!data.isNullOrEmpty()) {

                            productlist = data!!
                            filtredList.clear()
                            if (min != -1f && max != -1f) {
                                productlist?.forEach {
                                    if (!it.offers_bundle_price.isNullOrEmpty()) {
                                        if (it.offers_bundle_price!!.toFloat() in min..max) {
                                            filtredList.add(it)
                                        }
                                    }
                                }
                            } else {
                                filtredList.addAll(productlist)
                            }

                            val adapter = BundleOfferAdapter(
                                activity,
                                filtredList,
                                this@BundleProductFragment,
                                manager?.locale.equals("ar",ignoreCase = true)
                            )
                            binding.recycleBundle.adapter = adapter
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


            override fun onFailure(call: Call<BundleProductModel?>?, t: Throwable?) {
                // something went completely south (like no internet connection)
                LoadingDialog.cancelLoading()
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

    override fun onItemClicked(position: Int, item: BundleProductModel.Data?) {
        changeFragemnt(BundleProductDetailFragment.newInstance(item?.offers_id!!, ""))
    }

    override fun onAddToWishlistButtonClick(productId: String) {
        addToWishlist(productId)
    }

    override fun onDeleteFromWishListButtonClick(productId: String) {
        deleteWishListItem(productId)
    }

    fun addToWishlist(productId: String) {
        LoadingDialog.showLoadingDialog(requireContext(), "")
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
                Log.e("Add wishlist response", response.toString() + "")
                if (response.isSuccessful()) {

                    val status: String = response.body()!!.status.toString()
                    val message: String? = response.body()!!.message

                    Log.e("status", status.toString() + "")
                    Log.e("message", message.toString() + "")

                    if (status == "true") {
                        var position = 0
                        productlist?.forEachIndexed { index, data ->
                            if (data?.offers_id == productId) {
                                position = index
                                productlist[position].wishlist_exist = 1
                                return@forEachIndexed
                            }
                        }

                        binding.recycleBundle.adapter?.notifyItemChanged(position)
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
        LoadingDialog.showLoadingDialog(requireContext(), "")
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
                            if (data?.offers_id == productId) {
                                position = index
                                productlist[position].wishlist_exist = 0
                                return@forEachIndexed
                            }
                        }

                        binding.recycleBundle.adapter?.notifyItemChanged(position)
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