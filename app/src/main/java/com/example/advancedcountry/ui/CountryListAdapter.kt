package com.example.advancedcountry.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advancedcountry.databinding.GroupViewBinding
import com.example.advancedcountry.databinding.RowCountryViewBinding
import com.example.advancedcountry.domain.Model.GroupedCountry

class CountryListAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
     var countries:List<GroupedCountry> = listOf()
    companion object {
        const val GroupViewType = 0
        const val CountryViewType = 1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(countrylist: List<GroupedCountry>) {
        countries = countrylist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val countryBinding =
            RowCountryViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val groupBinding =
            GroupViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        val viewHolder = if (viewType == CountryViewType) {
            CountryViewHolder(countryBinding)
        } else {
            GroupViewHolder(groupBinding)
        }
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        when (countries[position]) {
            is GroupedCountry.CountryUi -> (holder as CountryViewHolder).bind(countries[position])
            is GroupedCountry.GroupBy -> (holder as GroupViewHolder).bind(countries[position])
        }
    }


    override fun getItemViewType(position: Int): Int {

        return when (countries[position]) {
            is GroupedCountry.CountryUi -> CountryViewType
            is GroupedCountry.GroupBy -> GroupViewType
        }
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    class CountryViewHolder(val binding: RowCountryViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(country: GroupedCountry) {

            when (country) {
                is GroupedCountry.CountryUi -> {
                    binding.countryName.text = country.country.name + ", "
                    binding.countryRegion.text = country.country.region
                    binding.countryCode.text = country.country.code
                    binding.countryCapital.text = country.country.capital
                }

                is GroupedCountry.GroupBy -> {}
            }
        }
    }


    class GroupViewHolder(val binding: GroupViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(group: GroupedCountry) {
            if (group is GroupedCountry.GroupBy) {
                binding.groupName.text = group.group
            }
        }
    }
}