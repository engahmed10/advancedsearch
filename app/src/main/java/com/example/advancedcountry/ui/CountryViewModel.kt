package com.example.advancedcountry.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.advancedcountry.domain.util.Result
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.advancedcountry.domain.Model.GroupedCountry
import com.example.advancedcountry.domain.usecase.CountriesUseCase

class CountryViewModel(private val getCountriesUseCase: CountriesUseCase) : ViewModel() {
    companion object {
        val myUseCaseKey = object : CreationExtras.Key<CountriesUseCase> {}
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val myUseCase = this[myUseCaseKey] as CountriesUseCase
                CountryViewModel(myUseCase)
            }
        }
    }

    private var _liveData = MutableLiveData<Result<List<GroupedCountry>>>()
    val liveData: LiveData<Result<List<GroupedCountry>>> = _liveData

    var countries: List<GroupedCountry> = listOf()

    /**
     * getCountries method to collect  Flow<Result<List<CountryUiModel>>> data.
     * /**
     *      * @param Empty.
     *      * @return The corresponding [Result<List<CountryUiModel>>].
     *      */
     **/
    fun getCountries() {
        viewModelScope.launch {
            getCountriesUseCase.invoke().collect {
                if (it is Result.Success) {
                    countries = it.data
                }
                _liveData.value = it
            }
        }
    }

    fun searchResult(query: String) {
        if (query.isBlank()) {
            _liveData.value = Result.Success(countries)
        } else {
            // Filter countries by group OR country details
            val filteredList = countries.filter { groupedCountry ->
                when (groupedCountry) {
                    is GroupedCountry.CountryUi -> {
                        groupedCountry.country.name?.substring(0,query.length)
                            .toString().trim().lowercase() == query.lowercase()
                    }
                    is GroupedCountry.GroupBy -> {
                        groupedCountry.group.isEmpty()
                    }
                }
            }
            _liveData.value = Result.Success(filteredList)
        }
    }

}