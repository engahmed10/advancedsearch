package com.example.advancedcountry.data.repository

import com.example.advancedcountry.data.mapper.CountryMapper
import com.example.advancedcountry.data.remote.ApiService
import com.example.advancedcountry.domain.repository.CountryRepository
import com.walmart.countrylist.domain.domain.CountryUiModel
import retrofit2.Response

class CountryRepositoryImpl(private val apiService: ApiService) : CountryRepository {

    /**
     * we create getCountries method to get List<CountryUiModel> Response from ApiService.
     * /**
     *      * @param Empty.
     *      * @return The corresponding [Response<List<CountryUiModel>>].
     *      */
     **/
    override suspend fun getCountries(): Response<List<CountryUiModel>>? {
        val result = apiService.getCountries()
        return if (result.isSuccessful) {
            result.body()?.let {
                Response.success(CountryMapper.mapToDomainList(it))
            }
        } else {
            result.errorBody()?.let {
                Response.error(400, it)
            }
        }
    }
}