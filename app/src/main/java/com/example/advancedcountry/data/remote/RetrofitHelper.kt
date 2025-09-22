package com.example.advancedcountry.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    /**
     * We use Retrofit library to simplify http requests to Make api calls.
     * /**
     *      * Create Retrofit instance and api service interface instance
     *      then we return ApiService instance.
     *      *
     *      * @param Empty.
     *      * @return The corresponding [ApiService].
     *      */
     **/
    fun createRetrofitService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ApiService::class.java)
    }
}