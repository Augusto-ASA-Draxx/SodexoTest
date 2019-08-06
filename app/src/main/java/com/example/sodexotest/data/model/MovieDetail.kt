package com.example.sodexotest.data.model

data class MovieDetail (
    val title : String,
    val year : Int,
    val ids : Ids,
    val tagline : String,
    val overview : String,
    val released : String,
    val runtime : Int,
    val country : String,
    val trailer : String,
    val homepage : String,
    val rating : Double,
    val votes : Int,
    val comment_count : Int,
    val updated_at : String,
    val language : String,
    val available_translations : MutableList<String>,
    val genres : MutableList<String>,
    val certification : String
)