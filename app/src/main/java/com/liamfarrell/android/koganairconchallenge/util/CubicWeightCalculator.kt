package com.liamfarrell.android.koganairconchallenge.util

import com.liamfarrell.android.koganairconchallenge.pojos.Product

/**
 * Class that calculates the cubic weight of all the items that are added its product list
 */
class CubicWeightCalculator {
    companion object{
        const val CUBIC_WEIGHT_CONVERSION_FACTOR = 250
    }

    private val productList = mutableListOf<Product>()


    fun addToProductList(product: Product) {
        productList.add(product)
    }

    fun calculateAverageCubicWeight(): Double {
        var totalCubicWeight = 0.00
        productList.forEach {
            val height = it.size.height * 0.01
            val length = it.size.length * 0.01
            val width = it.size.width * 0.01
            val cubicSize =  height * length * width
            val cubicWeight = cubicSize * CUBIC_WEIGHT_CONVERSION_FACTOR
            totalCubicWeight += cubicWeight
        }
        return totalCubicWeight / productList.size
    }

    fun getProductListSize() : Int {
       return productList.size
    }
}