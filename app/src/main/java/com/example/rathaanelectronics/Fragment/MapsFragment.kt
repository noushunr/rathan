package com.example.rathaanelectronics.Fragment

import android.content.Intent
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rathaanelectronics.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import android.os.Handler
import androidx.fragment.app.FragmentTransaction
import com.example.rathaanelectronics.Activity.Sign_in_Activity
import com.example.rathaanelectronics.Activity.Thank_You_Activity


class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_maps, container, false)
        change()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    fun change(){


        val handler = Handler()
        val runnable = Runnable { //Second fragment after 5 seconds appears

            val intent = Intent(activity, Thank_You_Activity::class.java)
            startActivity(intent)

        }

        handler.postDelayed(runnable, 5000)
    }

//    fun changeFragemnt(fragment: Fragment) {
//        val transaction = activity?.supportFragmentManager?.beginTransaction()
//        transaction?.replace(com.example.rathaanelectronics.R.id.frame, fragment)
//        transaction?.addToBackStack(null)
//        transaction?.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
//        transaction?.commit()
//    }
}