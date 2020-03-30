package com.liamfarrell.android.koganairconchallenge.api

import com.liamfarrell.android.koganairconchallenge.pojos.ProductsPage
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * REST API access points
 */

interface KoganProductApi {

    companion object{
        val BASE_URL = "http://wp8m3he1wt.s3-website-ap-southeast-2.amazonaws.com/"
    }

    @GET("{next}")
    fun getProducts(@Path("next", encoded = true) nextPage: String) : Call<ProductsPage>


    /**
     * Factory class for convenient creation of the Api Service interface
     */
     object Factory {
        fun create(): KoganProductApi {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create<KoganProductApi>(
                KoganProductApi::class.java)
        }
    }
}