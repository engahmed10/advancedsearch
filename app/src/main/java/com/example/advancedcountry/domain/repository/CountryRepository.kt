package com.example.advancedcountry.domain.repository
import com.walmart.countrylist.domain.domain.CountryUiModel
import retrofit2.Response
interface CountryRepository {
    suspend fun getCountries(): Response<List<CountryUiModel>>?
}