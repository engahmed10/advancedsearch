package com.example.advancedcountry.data.mapper

import com.example.advancedcountry.data.model.CountryListDto
import com.example.advancedcountry.data.model.CountryListDtoItem
import com.walmart.countrylist.domain.domain.CountryUiModel


object CountryMapper {
    /**
     * Converts a [CountryListDtoItem] object to a [CountryUiModel] object.
     *
     * @param  [CountryListDtoItem] object to convert.
     * @return The corresponding [CountryUiModel] object.
     */
    fun toDomain(country: CountryListDtoItem): CountryUiModel = CountryUiModel(
        country.capital,
        country.code,
        country.currency,
        country.flag,
        country.language,
        country.name,
        region = country.region
    )
    /**
     * Converts a [CountryListDto]  to a [List<CountryUiModel>].
     *
     * @param [CountryListDto] object to convert.
     * @return The corresponding [List<CountryUiModel>].
     */
    fun mapToDomainList(countryList: CountryListDto): List<CountryUiModel> = countryList.map {
        toDomain(it)
    }
}