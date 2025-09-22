package com.example.advancedcountry.domain.usecase

import com.example.advancedcountry.domain.Model.GroupedCountry
import com.example.advancedcountry.domain.util.Result
import kotlinx.coroutines.flow.Flow

interface CountriesUseCase {
    operator fun invoke(): Flow<Result<List<GroupedCountry>>>
}
