package com.example.routemap

import com.google.gson.annotations.SerializedName

data class RouteResponse(@SerializedName("features")val feature:List<Feature>)
data class Feature(@SerializedName("geometry")val geometry:Geometry)
data class Geometry(@SerializedName("coordinates")val coordinates:List<List<Double>>)
