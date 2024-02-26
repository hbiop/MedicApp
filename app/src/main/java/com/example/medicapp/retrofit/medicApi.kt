package com.example.medicapp.retrofit

import com.example.medicapp.models.Analys
import retrofit2.Call
import retrofit2.http.GET

interface medicApi {
    @GET("analyses/")
    fun getAnalyses():Call<List<Analys>>
}