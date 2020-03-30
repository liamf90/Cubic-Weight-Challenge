package com.liamfarrell.android.koganairconchallenge.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liamfarrell.android.koganairconchallenge.api.KoganProductApi
import com.liamfarrell.android.koganairconchallenge.util.CubicWeightCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The ViewModel for [MainFragment].
 */
class MainViewModel : ViewModel() {
    companion object {
        const val AIR_CONDITIONERS = "Air Conditioners"
        const val INITIAL_PAGE = "/api/products/1"
    }


    private val _productsCount = MutableLiveData<Int>()
    val productsCount : LiveData<Int> = _productsCount

    private val _averageCubicWeight = MutableLiveData<Double>()
    val averageCubicWeight : LiveData<Double> = _averageCubicWeight


    fun start(){
        viewModelScope.launch(Dispatchers.Main) {
            getAverageCubicWeight()
        }
    }

    /**
     * Loops through each page of the api and adds all of the found Air Conditioners to the [CubicWeightCalculator] class
     * [CubicWeightCalculator] determines the average cubic weight of all the air conditioners
     */
    private suspend fun getAverageCubicWeight()  {
        val cubicWeightCalculator = CubicWeightCalculator()
        withContext(Dispatchers.IO) {
            val initialPage = INITIAL_PAGE
            var nextPage: String? = initialPage
            while (nextPage != null) {
                val products = KoganProductApi.Factory.create().getProducts(nextPage)
                val response = products.execute()
                if (response.isSuccessful) {
                    val page = response.body()
                    page?.objects?.forEach {
                        if (it.category == AIR_CONDITIONERS) {
                            cubicWeightCalculator.addToProductList(it)
                        }
                    } ?: throw Error("Page is null")
                    nextPage = page.next
                } else {
                    throw error(response.errorBody().toString())
                }

            }

            _averageCubicWeight.postValue(cubicWeightCalculator.calculateAverageCubicWeight())
            _productsCount.postValue(cubicWeightCalculator.getProductListSize())
        }
    }
}
