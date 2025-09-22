package com.walmart.countrylist.domain.domain

import com.example.advancedcountry.data.model.Currency
import com.example.advancedcountry.data.model.Language

data class CountryUiModel(
    val capital: String?,
    val code: String?,
    val currency: Currency?,
    val flag: String?,
    val language: Language?,
    val name: String?,
    val region: String?
)


