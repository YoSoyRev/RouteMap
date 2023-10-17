package com.example.routemap


import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("v2/directions/driving-car")
    suspend fun getRoute(@Query("MapTools")key:String,
                 @Query("start")start:String,
                 @Query("end") end:String
    ):Response<RouteResponse>
}