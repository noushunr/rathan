package com.example.rathaanelectronics.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.LinearLayout
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.Adapter.Cart_list_Adapter
import com.example.rathaanelectronics.Adapter.Oder_history_list_Adapter
import com.example.rathaanelectronics.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Oder_history_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Oder_history_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var Menufilter: MenuItem? = null
    var cart_Fragment = Cart_Fragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @SuppressLint("WrongConstant")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_oder_history_, container, false)
        setHasOptionsMenu(true);

        val recycler_oder_history =  view.findViewById<RecyclerView>(R.id.recycler_oder_history)
        val Oder_history_list_Adapter = Oder_history_list_Adapter(activity)

        recycler_oder_history.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        recycler_oder_history.adapter =Oder_history_list_Adapter

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart -> {
                changeFragemnt(cart_Fragment)
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
         * @return A new instance of fragment Oder_history_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Oder_history_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}