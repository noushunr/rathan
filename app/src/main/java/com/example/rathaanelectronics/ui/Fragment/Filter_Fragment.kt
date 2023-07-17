import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rathaanelectronics.ui.Adapter.Cart_list_Adapter
import com.example.rathaanelectronics.ui.Fragment.SubCategory_Fragment
import com.example.rathaanelectronics.R
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.rathaanelectronics.ui.Activity.MainActivity
import com.example.rathaanelectronics.ui.Fragment.Cart_Fragment
import com.example.rathaanelectronics.Managers.MyPreferenceManager
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Cart_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Filter_Fragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var toolbar: Toolbar? = null
    private var Menufilter: MenuItem? = null
    var cart_Fragment = Cart_Fragment()
    private var manager: MyPreferenceManager? = null
    var min = 0.5f
    var max = 1000f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = MyPreferenceManager(activity)
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

        val view =inflater.inflate(R.layout.fragment_filter, container, false)
        val ivBack = view.findViewById<ImageView>(R.id.iv_back)
        ivBack.setOnClickListener { activity?.onBackPressed() }
        val slider = view.findViewById<RangeSlider>(R.id.slider)
//        slider.setRange(1000f,16000f)
        val close=view.findViewById<AppCompatButton>(R.id.close)
        val apply=view.findViewById<AppCompatButton>(R.id.apply)
        slider.addOnChangeListener { slider, value, fromUser ->
            min = slider.values[0]
            max = slider.values[1]
        }
        apply.setOnClickListener { view ->
            var args = Bundle()
            args.putFloat("min",min)
            args.putFloat("max",max)
            manager?.min = min
            manager?.max = max
            requireActivity().supportFragmentManager
                .setFragmentResult("124", args)
            activity?.onBackPressed()

        }
        close.setOnClickListener { view ->
            activity?.onBackPressed()
        }
        return view
    }


    fun RangeSlider.setRange(from: Float, to: Float) {
        valueFrom = 0F
        valueTo = to
        valueFrom = from
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.cart_menu, menu)
        this.Menufilter = menu.findItem(R.id.cart).setVisible(false)
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
         * @return A new instance of fragment Cart_Fragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Filter_Fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}