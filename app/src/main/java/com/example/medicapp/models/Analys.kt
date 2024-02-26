package com.example.medicapp.models

data class Analys(
    val id:Int,
    val name:String,
    val cost: Int,
    val days_to_result: Int,
    val description: String,
    val preparation: String,
    val biomaterial: String
)
