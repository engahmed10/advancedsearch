package com.walmart.countrylist.domain.usecase

import com.example.advancedcountry.domain.Model.GroupedCountry
import com.example.advancedcountry.domain.usecase.CountriesUseCase
import com.example.advancedcountry.domain.repository.CountryRepository
import com.example.advancedcountry.domain.util.Result
import kotlinx.coroutines.flow.flow

class GetCountriesUseCaseImpl(val countryRepository: CountryRepository) : CountriesUseCase {
    /**
     * we create invoke() method to get data from repository.
     * /**
     *      * @param Empty.
     *      * @return The corresponding [FlowCollector<Result<List<CountryUiModel>>].
     *      */
     **/
    override operator fun invoke() = flow {

        emit(Result.Loading)
        val result = countryRepository.getCountries()

        if (result?.isSuccessful == true) {
            result.body()?.let {
               val m = it.sortedBy { it.name }.groupBy { it.region }
                    .flatMap {(g, countries) ->
                        listOf(GroupedCountry.GroupBy("Group: $g")) +
                                countries.map { GroupedCountry.CountryUi(it) }
                    }
                emit(Result.Success(m))
            } ?: emit(Result.Error("No Data found in response"))
        } else {
            result?.message()?.let {
                emit(Result.Error(it))
            }
        }
    }
}