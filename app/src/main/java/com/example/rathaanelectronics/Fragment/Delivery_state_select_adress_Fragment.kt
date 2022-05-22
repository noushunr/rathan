package com.example.rathaanelectronics.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.R


import com.kofigyan.stateprogressbar.StateProgressBar






// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Delivery_state_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Delivery_state_select_adress_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    var btn_continue:Button?=null
    var ll_selected:LinearLayout?=null
    var descriptionData = arrayOf("Address", "Delivery", "Payment")

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
         val view=inflater.inflate(R.layout.fragment_delivery_state_address, container, false)

        btn_continue=view.findViewById(R.id.btn_continue);
        ll_selected=view.findViewById(R.id.ll_selected);
        val stateProgressBar = view.findViewById(com.example.rathaanelectronics.R.id.state_progress) as StateProgressBar
        stateProgressBar.enableAnimationToCurrentState(true)
        stateProgressBar.setStateDescriptionData(descriptionData)
//        val typeface:String = activity?.let { ResourcesCompat.getFont(it, R.font.roboto) }
//        stateProgressBar.setStateDescriptionTypeface(typeface);


        ll_selected!!.visibility = View.VISIBLE
        btn_continue?.setOnClickListener{view ->

            changeFragemnt(Delivery_state_select_delivery_Fragment())

        }

        return view
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
         * @return A new instance of fragment Delivery_state_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Delivery_state_select_adress_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}