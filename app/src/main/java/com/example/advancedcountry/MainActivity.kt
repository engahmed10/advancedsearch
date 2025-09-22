package com.example.advancedcountry

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.advancedcountry.databinding.ActivityMainBinding
import com.example.advancedcountry.data.remote.RetrofitHelper
import com.example.advancedcountry.data.repository.CountryRepositoryImpl
import com.walmart.countrylist.domain.usecase.GetCountriesUseCaseImpl
import com.example.advancedcountry.ui.CountryListAdapter
import com.example.advancedcountry.ui.CountryViewModel
import com.example.advancedcountry.domain.util.Result

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CountryListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val viewModel: CountryViewModel = viewModelInstance()
        val progressBar: ProgressBar = binding.progressBar
        recyclerViewSetup()
        viewModel.getCountries()
        observer(viewModel, progressBar)
        searchListener(viewModel)
    }

    private fun searchListener(viewModel: CountryViewModel) {
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.searchResult(p0!!)
                return false
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }
        })
    }

    private fun recyclerViewSetup() {
        val recycleView = binding.recycleView
        recycleView.layoutManager = LinearLayoutManager(this)
        adapter = CountryListAdapter()
        binding.recycleView.adapter = adapter
    }

    /**
     * observer method to viewmodel data in UI.
     * /**
     *      * @param [CountryViewModel,progressBar] .
     *      * @return Empty.
     *      */
     **/
    @SuppressLint("NotifyDataSetChanged")
    private fun observer(
        viewModel: CountryViewModel,
        progressBar: ProgressBar
    ) {
        viewModel.liveData.observe(this) { countries ->
            when (countries) {
                is Result.Error -> {
                    Log.d("Countries", "Error Loading Data")
                }

                Result.Idle -> {
                    Log.d("Countries", "Not started yet")
                }

                Result.Loading -> {
                    progressBar.visibility = ProgressBar.VISIBLE
                }

                is Result.Success -> {
                    progressBar.visibility = ProgressBar.GONE
                    countries.data.let {
                        adapter.submitList(it)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    /**
     * create viewModelInstance by using [viewmodel owner, factory].
     * /**
     *      * @param empty .
     *      * @return [CountryViewModel] object.
     *      */
     **/
    private fun viewModelInstance(): CountryViewModel {
        val viewModeStoreOwner: ViewModelStoreOwner = this
        val viewModel: CountryViewModel = ViewModelProvider.create(
            viewModeStoreOwner,
            factory = CountryViewModel.Factory,
            extras = MutableCreationExtras().apply {
                set(
                    CountryViewModel.myUseCaseKey, GetCountriesUseCaseImpl(
                        CountryRepositoryImpl(
                            RetrofitHelper.createRetrofitService()
                        )
                    )
                )
            }
        )[CountryViewModel::class]
        return viewModel
    }

    /**
     * we call  onSaveInstanceState to store small amount of data
     * handel configuration changes in the future.
     * /**
     *      * @param outState .
     *      * @return empty.
     *      */
     **/
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    /**
     * we call onRestoreInstanceState to get data from bundle
     * wen configuration changed in the future.
     * /**
     *      * @param [savedInstanceState].
     *      * @return empty.
     *      */
     **/
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }
}