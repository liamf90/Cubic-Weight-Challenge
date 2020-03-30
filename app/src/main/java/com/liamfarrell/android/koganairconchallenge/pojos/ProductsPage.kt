package com.liamfarrell.android.koganairconchallenge.pojos

import com.liamfarrell.android.koganairconchallenge.pojos.Product

data class ProductsPage (
    val objects: List<Product>,
    val next: String
)