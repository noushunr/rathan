package com.example.rathaanelectronics.ui.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.rathaanelectronics.Utils.CustomPager
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.example.rathaanelectronics.R
import com.google.android.material.tabs.TabLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Contact_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Contact_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var customPager: CustomPager
    private lateinit var tabs : TabLayout
    private lateinit var ivBack : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact_,container,false)
        tabs = view.findViewById<TabLayout>(R.id.tabs2)
        customPager = view.findViewById<View>(R.id.viewpager2) as CustomPager
        ivBack = view.findViewById(R.id.iv_back)
        ivBack.setOnClickListener {
            activity?.onBackPressed()
        }
        val adapter =
            com.example.rathaanelectronics.ui.Adapter.ViewpagerAdapter(childFragmentManager)
        var manager = MyPreferenceManager(requireContext())
        if (manager?.locale.equals("ar")){
            adapter.addFragment(ContactUsFragment.newInstance("",""),getString(R.string.our_address))
            adapter.addFragment(MessageContactFragment.newInstance("",""),getString(R.string.leave_us_message))
            customPager.adapter = adapter
            customPager.currentItem = 1
        }else{
            adapter.addFragment(MessageContactFragment.newInstance("",""),getString(R.string.leave_us_message))
            adapter.addFragment(ContactUsFragment.newInstance("",""),getString(R.string.our_address))
            customPager.adapter = adapter
        }

        tabs.setupWithViewPager(customPager)
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Contact_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Contact_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}