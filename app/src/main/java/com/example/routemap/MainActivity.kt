package com.example.routemap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var btnCalculate:Button

    private var start:String =""
    private var end:String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCalculate = findViewById(R.id.btnCalculateRoute)
        btnCalculate.setOnClickListener {
            start= ""
            end= " "
            if (::map.isInitialized){
                map.setOnMapClickListener {
                    if (start.isNotEmpty()){
                        start = "${it.longitude},${it.latitude}"
                    }else if (end.isEmpty()){
                        end = "${it.longitude},${it.latitude}"
                        createRoute()
                    }
                }
            }
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
    }
    private fun createRoute(){
        CoroutineScope(Dispatchers.IO).launch{
            val call = getRetrofit().create(ApiService::class.java)
                .getRoute(key = "5b3ce3597851110001cf6248da27533a258c4d7a9d501ff562f1d155", start, end)
            if (call.isSuccessful){
               Log.i("emm","ok")
            }else{
                Log.i("emm","ok")
            }
        }
    }
    private fun drawRoute(routeResponse: RouteResponse?){
        val polyLineOptions = PolylineOptions()
        routeResponse?.feature?.first()?.geometry?.coordinates?.forEach {
             polyLineOptions.add(LatLng(it[0], it[1]))
        }
        runOnUiThread{
            val poly = map.addPolyline(polyLineOptions)
        }
    }

    private fun getRetrofit():Retrofit{
        return  Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}