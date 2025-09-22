package com.example.advancedcountry.domain.Model

import com.walmart.countrylist.domain.domain.CountryUiModel

sealed class GroupedCountry {
    data class CountryUi(val country: CountryUiModel): GroupedCountry()
    data class GroupBy(val group: String): GroupedCountry()
}

