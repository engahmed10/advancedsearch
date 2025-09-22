package com.example.advancedcountry.data.remote

import com.example.advancedcountry.data.model.CountryListDto
import retrofit2.Response
import retrofit2.http.GET


interface ApiService {
    /**
     * Api service that includes Retrofit getter method to get  api data.
     **/
    @GET(HEADER_URL)
    suspend fun getCountries(): Response<CountryListDto>
}