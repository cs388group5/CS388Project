package com.example.nightlifeapp.fragments

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.nightlifeapp.Crime
import com.example.nightlifeapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.parse.ParseException
import com.parse.ParseQuery
import java.io.IOException


class MapFragment : Fragment() {

    var allCrimes: MutableList<Crime> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        mapFragment!!.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

            mMap.clear() //clear old markers

            val googlePlex = CameraPosition.builder()
                .target(LatLng(40.7219999, -74.1762462))
                .zoom(10f)
                .bearing(0f)
                .tilt(45f)
                .build()

            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 10000, null)

            createNewMarker(40.7,-74.17,mMap,"Assault", "I was assaulted outside of the WEC")

            /*
            Not working yet
            Log.i(TAG,"Entering loadMapMarkers")
            loadMapMarkers(mMap)
            Log.i(TAG,"Exited loadMapMarkers")
             */
        }

        return rootView
    }

    private fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(this.requireContext()!!, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun createNewMarker(latitude: Double,longitude: Double, mMap: GoogleMap, title: String, description: String){
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude,longitude))
                .title(title)
                .snippet(description)
        )
    }

    //without description
    private fun createNewMarker(latitude: Double,longitude: Double, mMap: GoogleMap, title: String){
        mMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude,longitude))
                .title(title)
        )
    }

    private fun loadMapMarkers(mMap: GoogleMap){

        var location:LatLng?
        queryCrimes()
        Log.i(TAG,"Finished querying crimes")
        for(crime in allCrimes){
            location= crime.getLocation()?.let { getLocationFromAddress(requireContext(), it) }
            if(location!=null){
                Log.i(TAG,"Location: "+location.latitude+" "+location.longitude)
                createNewMarker(location.latitude,location.longitude,mMap,
                    crime.getCrimeType()!!, crime.getDescription()!!
                )
            }
            else{
                Log.i(TAG,"The location was null")
            }
        }

    }

    fun getLocationFromAddress(context: Context, strAddress: String): LatLng? {

        val coder = Geocoder(context)
        val address: List<Address>?
        var p1: LatLng? = null

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5)
            if (address == null) {
                return null
            }

            val location = address[0]
            p1 = LatLng(location.latitude, location.longitude)

        } catch (ex: IOException) {

            ex.printStackTrace()
        }

        return p1
    }

    private fun queryCrimes(){

        //specify which class to query
        val query: ParseQuery<Crime> = ParseQuery.getQuery(Crime::class.java)

        query.addDescendingOrder("createdAt")
        query.setLimit(20)
        try {
            val list = query.find()
            Log.i(TAG, "List: $list")
            allCrimes.addAll(list)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    companion object{
        val TAG="MapFragment"
    }

}// Required empty public constructor

